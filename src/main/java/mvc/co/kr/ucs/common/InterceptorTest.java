package mvc.co.kr.ucs.common;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class InterceptorTest extends HandlerInterceptorAdapter{
	
	public InterceptorTest() {
		System.out.println("interceptor 생성");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		System.out.println("pre");
		if(session.getAttribute("id")==null) {
			System.out.println(handler);
		
			Writer out = getWriter(response);
			out.write("<script>");
			out.write("alert('로그인후 사용 가능합니다.');");
			out.write("location.href='/mvc/login/login';");
			out.write("</script>");
			
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("post handler");
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	private Writer getWriter(HttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		return response.getWriter();
	}
}
