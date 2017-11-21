package mvc.co.kr.ucs.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import mvc.co.kr.ucs.bean.BoardBean;
import mvc.co.kr.ucs.bean.PagingBean;
import mvc.co.kr.ucs.service.BoardService;

@Controller
public class MvcBoardCtrl {
	
	@Resource
	private BoardService svc;
	
	
	@RequestMapping(value="/mvc/board/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView mav,BoardBean bean,PagingBean paging) {
		mav.setViewName("/board/boardList");
		paging.setTotalData(svc.getTotal(bean));
		bean.setTotalData(paging.getTotalData());
		ArrayList list = (ArrayList)svc.getList(bean);
		mav.addObject("list", list);
		mav.addObject("json_list", new Gson().toJson(list));
		mav.addObject("json_paging", new Gson().toJson(paging));
		mav.addObject("paging", paging);
		return mav;
	}
	
	@RequestMapping(value="/mvc/board/list.ajax",method=RequestMethod.POST)
	public void jsonList(ModelAndView mav,BoardBean bean,PagingBean paging,HttpServletResponse response)
		throws Exception{
		paging.setTotalData(svc.getTotal(bean));
		bean.setTotalData(paging.getTotalData());
		response.setCharacterEncoding("utf-8");
		Writer out = response.getWriter();
		List list = svc.getList(bean);
		HashMap map = new HashMap();
		map.put("list", list);
		map.put("paging",paging);
		out.write(new Gson().toJson(map));
		out.flush();
		
	}
	
	@RequestMapping(value="/mvc/board/read",method=RequestMethod.GET)
	public ModelAndView readBoard(ModelAndView mav,BoardBean bean) {
		mav.setViewName("/board/boardRead");
		BoardBean board = new BoardBean();
		board = svc.getBoard(bean);
		board.setCurrentPage(bean.getCurrentPage());
		board.setSearch(bean.getSearch());
		board.setSearchTarget(bean.getSearchTarget());
		mav.addObject("bean", board);
		return mav;
	}
	
	@RequestMapping(value="/mvc/board/write")
	public ModelAndView write(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		return new ModelAndView("/board/boardWrite");
	}
	
	@RequestMapping(value="/mvc/board/insert.do",method=RequestMethod.POST)
	public void insert(ModelAndView mav,BoardBean bean,
			HttpServletRequest request,HttpServletResponse response) 
		throws Exception{
		Writer out = getWriter(response);
		out.write("<script>");
		if(1>=svc.insertBoard(bean)) {
			out.write("alert('성공하였습니다.');");
			out.write("location.href='/mvc/board/list\'");
		} else {
			out.write("alert('실패하였습니다.');");
		}
		out.write("</script>");
		out.flush();
	}
	
	private Writer getWriter(HttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		return response.getWriter();
	}
}
