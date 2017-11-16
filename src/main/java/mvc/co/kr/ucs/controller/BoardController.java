package mvc.co.kr.ucs.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import mvc.co.kr.ucs.bean.PagingBean;
import mvc.co.kr.ucs.dao.StudyDAO;

@Controller
public class BoardController {
	
	@Autowired
	private StudyDAO dao;
	
	@RequestMapping(value="/mvc/board/list")
	public ModelAndView boardList(ModelAndView mav) {
		mav.setViewName("/mvcViews/template/board/table.jsp");
		
		HashMap param = new HashMap();
		
		param.put("tableName", "board");
		ArrayList list = (ArrayList)dao.selectList("sql.common",param);
		ArrayList fieldNames = new ArrayList();
		fieldNames.add("No");
		fieldNames.add("제목");
		fieldNames.add("작성자");
		fieldNames.add("등록일");
		

		ArrayList dataNames = new ArrayList();
		dataNames.add("seq");
		dataNames.add("title");
		dataNames.add("reg_id");
		dataNames.add("reg_date");
		HashMap board = new HashMap();
		board.put("fieldNames", fieldNames);
		board.put("dataNames", dataNames);
		board.put("list", list);
		board.put("json_list", new Gson().toJson(list));
		board.put("json_dataNames", new Gson().toJson(dataNames));
		mav.addObject("board", board);
		
		PagingBean paging = new PagingBean();
		paging.setTotalData(list.size());
		mav.addObject("paging", new Gson().toJson(paging));
		return mav;
	}
	
	
}
