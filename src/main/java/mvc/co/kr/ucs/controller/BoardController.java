package mvc.co.kr.ucs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		String[] fieldNames = {
			"No","제목","작성자","등록일"	
		};
		String[] dataNames = {
				"seq","title","reg_id","reg_date"
		};
		HashMap board = new HashMap();
		board.put("fieldNames", Arrays.asList(fieldNames));
		board.put("dataNames", Arrays.asList(dataNames));
		board.put("list", list);
		mav.addObject("board", board);
		return mav;
	}
	
	
}
