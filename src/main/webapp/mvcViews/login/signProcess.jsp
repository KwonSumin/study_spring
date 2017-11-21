<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%!
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String url = "jdbc:oracle:thin:@220.76.203.39:1521:UCS";
	private String user = "UCS_STUDY";
	private String pw = "qazxsw";
	private RequestDispatcher dispatcher;
	
	private final String JOIN_QUERY = "insert into CM_USER(USER_ID,USER_PW,USER_NM,EMAIL) values(?,?,?,?)";
	private final String OVERLAP_CHECK = "select count(*) as count from CM_USER where USER_ID = ?";
	private final String LOGIN_QUERY = "select count(*) as count from CM_USER where USER_ID = ? AND USER_PW = ?";
		
	private void command(String cmd,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		int overlap = -1;
		String view = null;
		HashMap result = new HashMap();
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);
			System.out.println("Connection success");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		switch(cmd) {
		case "siginup":
			try{
				pstmt = conn.prepareStatement(OVERLAP_CHECK);
				pstmt.setString(1,request.getParameter("id"));
				rs = pstmt.executeQuery();
				while(rs.next()){
					overlap = rs.getInt("count");
				}
				if(overlap == 1) {
					//overlap id
						System.out.println("중복된 아이디");
					result.put("message","중복된 아이디 입니다.");
					//view = request.getHeader("referer");
					//view = "./signUp.jsp";
					
					%>
					<script>
						alert("중복된 아이디 입니다.");
						window.history.back();
					</script>
					<%!
					return;
				} else {
					//don't overlap id
					pstmt = null;
					pstmt = conn.prepareStatement(JOIN_QUERY);
					
					
					pstmt.setString(1, request.getParameter("id"));
					pstmt.setString(2, request.getParameter("password"));
					pstmt.setString(3, request.getParameter("name"));
					pstmt.setString(4, request.getParameter("email"));
					
					if(pstmt.executeUpdate() >= 1) {
						//success
						System.out.println("회원가입 성공");
						result.put("message","회원가입에 성공하였습니다.");
						view = "./login.jsp";
					} else {
						//fail
						System.out.println("회원가입 실패");
						result.put("message","회원가입에 실패하였습니다.");
						//view = request.getHeader("referer");
						view = "./signUp.jsp";
					}
				}
				
				
			}catch(SQLException e) {
				e.printStackTrace();
				
				throw e;
			}
			break;
			
		case "signin":
			boolean hasSamePassword = false;
			try{
				pstmt = conn.prepareStatement(LOGIN_QUERY);
				pstmt.setString(1, request.getParameter("id"));
				pstmt.setString(2, request.getParameter("password"));
				rs = pstmt.executeQuery();
				while(rs.next()){
					if(rs.getInt("count") == 1) {
						hasSamePassword = true;
					}
				}
				if(hasSamePassword) {
					System.out.println("로그인 성공하였습니다.");
					session.setAttribute("id", request.getParameter("id"));
					result.put("message","로그인 성공하였습니다.");
					response.sendRedirect(request.getContextPath()+"/jsp/board/boardList.jsp");
					return;
				} else {
					view = "./login.jsp";
					System.out.println("로그인 실패하였습니다.");
					result.put("message","로그인 실패하였습니다.");					
				}
			}catch(SQLException e){
				e.printStackTrace();
				throw e;
			}
			break;
		case "logout" :
			session.invalidate();
			view = "./login.jsp";
			result.put("message","로그아웃 되었습니다.");
			break;
		}
		
		
		try{
			result.put("view",view);
			close(conn,pstmt,rs);
			System.out.println("view : " + view);
			dispatcher = request.getRequestDispatcher(view);
			request.setAttribute("data", result);
			dispatcher.forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void close(Connection conn,PreparedStatement pstmt,ResultSet rs) {
		try{
			if(conn != null)
				conn.close();
			if(pstmt != null)
				pstmt.close();
			if(rs != null)
				rs.close();
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}
%>

<%
	try{
		request.setCharacterEncoding("UTF-8");
		System.out.println("process : "+request.getParameter("process"));
		command(request.getParameter("process"),request,response,session);
	} catch (SQLException e) {
		%>
			errorAlert();
		<%
	} catch(Exception e) {
		%>
		<script>
			alert('통신 실패');
			window.history.back();
		</script>
		<%
	}
	
%>

<script>
	function errorAlert() {
		alert('오류가 발생하였습니다.\n관리자에게 문의해주세요.');
		window.history.back();
	}
	
</script>