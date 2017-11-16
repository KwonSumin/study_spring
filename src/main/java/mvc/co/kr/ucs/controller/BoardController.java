package mvc.co.kr.ucs.controller;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mvc.co.kr.ucs.bean.BoardBean;
import mvc.co.kr.ucs.bean.BoardTemplateBean;
import mvc.co.kr.ucs.bean.PagingBean;
import mvc.co.kr.ucs.dao.StudyDAO;

@Controller
public class BoardController {
	
	@Autowired
	private StudyDAO dao;
	
	@RequestMapping(value="/mvc/board/list")
	public ModelAndView boardList(ModelAndView mav) {
		mav.setViewName("/mvcViews/template/board/table.jsp");
		
		BoardBean param = new BoardBean();
		
		param.setTableName("board");
		ArrayList list = (ArrayList)dao.selectList("common.select",param);
		
		BoardTemplateBean template = new BoardTemplateBean();
		template.addInfo("No","seq");
		template.addInfo("제목","title");
		template.addInfo("작성자","reg_id");
		template.addInfo("등록일","reg_date");
		
		PagingBean paging = new PagingBean();
		paging.setTotalData(list.size());
		BoardTemplateBean bean = new BoardTemplateBean();
		bean.addInfo("test", "test");
		mav.addObject("test", bean);
		
		template.setList(list);
		template.setPaging(paging);
		mav.addObject("template", template);
		
		System.out.println(template);
		return mav;
	}
	@Test
	public void test() {
		BoardTemplateBean bean = new BoardTemplateBean();
		bean.addInfo("test", "test");
		
		System.out.println(bean.getJson("info"));
		
	}
	
}
