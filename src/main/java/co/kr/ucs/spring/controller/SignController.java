package co.kr.ucs.spring.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.kr.ucs.spring.common.ReflectUtil;
import co.kr.ucs.spring.service.CommonService;
import co.kr.ucs.spring.service.CommonServiceTest;
import co.kr.ucs.spring.spring.bean.SignBean;

/**
 * Servlet implementation class SignController
 */
@WebServlet("/sign/*")
public class SignController extends HttpServlet {
	private static final long serialVersionUID = 2L;
    
	@Autowired
	private CommonService commonService;
	
	private static Logger logger = LoggerFactory.getLogger(SignController.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignController() {
        super();
    }

    
    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		if(commonService !=null)System.out.println("서비스가능"); else System.out.println("널값");
		
		super.service(request, response);
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		//uri root + 기본 uri sign을 제외한 뒷부분만 자름
		String root = request.getContextPath();
		uri = uri.replaceAll(root + "/sign/", "");
		if(uri.equals("login")) {
			response.sendRedirect(root+"/jsp/login/login.jsp");
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		CommonServiceTest svc = new CommonServiceTest();
		String root = request.getContextPath();
		//uri root + 기본 uri sign을 제외한 뒷부분만 자름
		uri = uri.replaceAll(root + "/sign/", "");
		String query;
		String message;
		String view;
		
		
		if(uri.equals("signup")) {
			//가입
			query = "insert into CM_USER"
					+ "(USER_ID,USER_PW,USER_NM,EMAIL) values(?,?,?,?)";
			try{
				//request영역에 저장된 데이터 bean으로 대입.
				SignBean bean = ReflectUtil.requestToSetBean(request, new SignBean());
				if(svc.insertBeanService(query, bean)){
					message = "회원가입에 성공하였습니다.";
					view = root+"/sign/login";
					alertForward(response, message, view);
				} else {
					message = "회원가입에 실패하였습니다.\\n관리자에게 문의주세요 error-1.";
					alertHistoryBack(response, message);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				message = "회원가입에 실패하였습니다.\\n관리자에게 문의주세요 error-02.";
				alertHistoryBack(response, message);
			} catch (SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
				message = "회원가입에 실패하였습니다.\\n중복된 아이디는 사용할 수 없습니다.";
				alertHistoryBack(response, message);
			} catch (SQLException e) {
				e.printStackTrace();
				message = "회원가입에 실패하였습니다.\\n관리자에게 문의주세요 error-03.";
				alertHistoryBack(response, message);
			} 
		} else if(uri.equals("signin")){
			//로그인
			query = "select user_id from cm_user where user_id = ? AND"
					+ " user_pw = ? ";
			try{
				SignBean bean = ReflectUtil.requestToSetBean(request, new SignBean());
				bean = (SignBean)svc.selectOneService(query, bean, bean.getClass());
				if(bean.getUser_id() != null) {
					message = "로그인 성공";
					view = root+"/board/list";
					alertForward(response, message, view);
				} else {
					message = "로그인에 실패하였습니다.\\n패스워드 / 아이디를 확인해주세요.";
					alertHistoryBack(response, message);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				message = "로그인에 실패하였습니다.\\n관리자에게 문의주세요 error-02.";
				alertHistoryBack(response, message);
			} catch (SQLException e) {
				e.printStackTrace();
				message = "로그인에 실패하였습니다.\\n관리자에게 문의주세요 error-03.";
				alertHistoryBack(response, message);
			} 
		}
	}

	private void alertForward(HttpServletResponse response,String message,String view) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		
		logger.debug("view :" + view);
		out.println("<script charset=\"UTF-8\">");
		out.print("	alert('"+message+"');");
		out.println("location.href='"+view+"'");
		out.println("</script>");
		out.flush();
	}
	
	private void alertHistoryBack(HttpServletResponse response,String message) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		
		logger.debug("history back : " + message);
		out.println("<script charset=\"UTF-8\">");
		out.print("	alert('"+message+"');");
		out.println("window.history.back();");
		out.println("</script>");
		out.flush();
	}

}
