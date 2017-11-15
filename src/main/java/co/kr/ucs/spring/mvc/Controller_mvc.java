package co.kr.ucs.spring.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.kr.ucs.spring.aop.TestService;

@Controller
public class Controller_mvc {
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private DAO_mvc dao;
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String test(HttpServletRequest request) {
		System.out.println(dao.test());;
		testService.print("test service 테스트중");
		return "redirect:/";
	}
	
}
