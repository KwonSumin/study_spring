package mvc.co.kr.ucs.template.board.controller;

import java.io.Writer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import mvc.co.kr.ucs.common.bean.BoardTemplateBean;
import mvc.co.kr.ucs.common.bean.PagingBean;
import mvc.co.kr.ucs.template.board.service.AbstractBoardService;

@Controller
public class BoardController {
	@Resource
	private AbstractBoardService svc;
	
	@RequestMapping(value="/mvc/template/board/list")
	public ModelAndView list(ModelAndView mav,PagingBean paging) {
		mav.setViewName("template/board/table");
		BoardTemplateBean template = new BoardTemplateBean();
		template.addInfo("No", "seq");
		template.addInfo("제목", "title");
		template.addInfo("작성자", "reg_id");
		template.addInfo("등록일", "reg_date");
		template.setPaging(paging);
		svc.getList(template);
		mav.addObject("template", template);
		System.out.println("테스트");
		System.out.println(template);
		return mav;
	}
	
	
	@RequestMapping(value="/mvc/template/board/list.ajax")
	public void list_ajax(ModelAndView mav,PagingBean paging,HttpServletResponse response)
		throws Exception{
		System.out.println("파라미터 체크");
		System.out.println(paging);
		mav.setViewName("template/board/table");
		BoardTemplateBean template = new BoardTemplateBean();
		template.addInfo("No", "SEQ");
		template.addInfo("제목", "TITLE");
		template.addInfo("작성자", "REG_ID");
		template.addInfo("등록일", "REG_DATE");
		template.setPaging(paging);
		svc.getList(template);
		mav.addObject("template", template);
		System.out.println("ajax");
		System.out.println(template);
		response.setCharacterEncoding("UTF-8");
		Writer out = response.getWriter();
		String jsonStr = new Gson().toJson(template);
		out.write(jsonStr);
		out.flush();
	}
}
