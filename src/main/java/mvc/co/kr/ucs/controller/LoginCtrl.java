package mvc.co.kr.ucs.controller;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mvc.co.kr.ucs.bean.SignBean;
import mvc.co.kr.ucs.service.SignService;

@Controller
public class LoginCtrl {
	
	@Resource
	private SignService svc; 
	
	@RequestMapping(value="/mvc/login/login")
	public ModelAndView login
		(ModelAndView mav,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		mav.setViewName("/login/login");
		return mav;
	}
	
	@RequestMapping(value="/mvc/login/login.do")
	public String doLogin
		(SignBean bean,ModelAndView mav,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		boolean hasLogin = false;
		//TODO : login logic
		hasLogin = svc.login(bean);
		if(hasLogin) {
			request.getSession().setAttribute("id", bean.getUser_id());
			return "redirect:/mvc/board/list";
		} else {
			Writer out = getWriter(response);
			System.out.println("back");
			out.write("<script>");
			out.write("alert('로그인실패\\n패스워드를 확인해주세요.');");
			out.write("window.history.back()");
			out.write("</script>");
			out.flush();
			return null;
		}
	}
	
	@RequestMapping(value="/mvc/login/join")
	public ModelAndView join(ModelAndView mav,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		mav.setViewName("/login/signUp");
		return mav;
	}

	@RequestMapping(value="/mvc/login/signUp",method=RequestMethod.GET)
	public String signup() {
		return "/login/signUp";
	}
	
	@RequestMapping(value="/mvc/login/join.do",method=RequestMethod.POST)
	public String doJoin(SignBean bean,ModelAndView mav,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		mav.setViewName("/login/signUp");
		if( svc.signUp(bean) ) {
			
			
			return "redirect:/mvc/board/login";
		} else {
			Writer out = getWriter(response);
			System.out.println("back");
			out.write("<script>");
			out.write("window.history.back()");
			out.write("</script>");
			out.flush();
			return null;
		}
	}
	
	@RequestMapping(value="/mvc/login/logout.do")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/mvc/login/login";
	}
	
	@RequestMapping(value="/mvc/login/signUp.do")
	public void doSignUp(SignBean bean,HttpServletResponse response) 
		throws Exception{
		Writer out = getWriter(response);
		try {
			
			if(svc.signUp(bean)) {
				out.write("<script>");
				out.write("alert('회원가입 성공하였습니다.');");
				out.write("location.href='/mvc/login/login';");
				out.write("</script>");
			} else {
				
				out.write("<script>");
				out.write("alert('회원가입 실패하였습니다.');");
				out.write("history.back();");
				out.write("</script>");
			}
		}catch(DuplicateKeyException e) {
			out.write("<script>");
			out.write("alert('중복된 아이디입니다.');");
			out.write("history.back();");
			out.write("</script>");
		} 
	}
	
	
	private Writer getWriter(HttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		return response.getWriter();
	}
}
