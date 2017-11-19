package co.kr.ucs.spring.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mvc.co.kr.ucs.bean.CommonQueryBean;

@Controller
public class Controller_mvc {
	
	
	@Autowired
	private DAO_mvc dao;
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String test(HttpServletRequest request) {
		System.out.println(dao.test());;
		CommonQueryBean param = new CommonQueryBean();
		param.setTableName("board");
		param.addIf("seq", 539);
		param.addSet("title", "modify");
		System.out.println(param);
		System.out.println(dao.update("common.update",param));
		
		
		return "redirect:/";
	}
	
}