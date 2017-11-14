package co.kr.ucs.spring.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import co.kr.ucs.spring.bean.BoardBean;
import co.kr.ucs.spring.bean.PagingBean;
import co.kr.ucs.spring.common.ReflectUtil;
import co.kr.ucs.spring.service.CommonService;

/**
 * Servlet implementation class SignController
 */
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(BoardController.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardController() {
        super();
    }

    
    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		
		super.service(request, response);
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		//uri root + 기본 uri sign을 제외한 뒷부분만 자름
		String root = request.getContextPath();
		uri = uri.replaceAll(root + "/board/", "");
		CommonService svc = new CommonService();
		String query;
		String totalQuery;	
		Gson gson = new Gson();
		if(uri.equals("list") || uri.equals("listAjax")) {
			try{
				PagingBean bean = ReflectUtil.requestToSetBean(request, new PagingBean());
				try{
					if(bean.getCurrentPage()==0)bean.setCurrentPage(1);
				}catch(NumberFormatException e){
					bean.setCurrentPage(1);
				}
				totalQuery = "select count(*) as total  from board";
				
				bean.setLimit(10);
				bean.setTotal((Integer)ReflectUtil.castType(svc.selectOneService(totalQuery), Integer.TYPE));
				bean.setPaging();
				query = "select * from "
						+ "( select A.*,ROWNUM AS RNUM, count(*) over() AS TOTCNT FROM"
						+ " ( select * from board "
						+ "order by seq desc ) A ) where RNUM > "+bean.getStartRowNum()+" AND RNUM <="+bean.getEndRowNum()
						+ " order by seq desc";
				if(bean.getSearch()!=null&&!bean.getSearch().equals("") && bean.getSearch()!=null) {
					totalQuery += " where "+bean.getSearchTarget()+" like '%"+bean.getSearch()+"%'";
					query = "select * from "
							+ "( select A.*,ROWNUM AS RNUM, count(*) over() AS TOTCNT FROM"
							+ " ( select * from board where "+bean.getSearchTarget()+" like '%"+bean.getSearch()+"%'"
							+ "order by seq desc )  A ) where RNUM > "+bean.getStartRowNum()+" AND RNUM <="+bean.getEndRowNum()
							+ " order by seq desc";
				}
				
				bean.setTotal((Integer)ReflectUtil.castType(svc.selectOneService(totalQuery), Integer.TYPE));
				
				bean.setPaging();
				logger.info(bean.toString());
				ArrayList list = svc.selectList(query,new BoardBean().getClass());
				logger.info(list.toString());
				
				if(uri.equals("listAjax")) {
					logger.info("ajax");
					OutputStream out = response.getOutputStream();
					HashMap map = new HashMap();
					map.put("paging", bean);
					map.put("list", list);
					out.write(gson.toJson(map).getBytes());
					out.close();out.flush();
					return;
				}
				
			
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/board/boardList.jsp");
				request.setAttribute("list", list);
				request.setAttribute("paging", bean);
				request.setAttribute("json_list", gson.toJson(list));
				request.setAttribute("json_paging", gson.toJson(bean));
				dispatcher.forward(request, response);
			} catch(SQLException e) {
				e.printStackTrace();
			} catch(IllegalAccessException e) {
				//reflect 예외처리
				e.printStackTrace();
			} catch(InstantiationException e) {
				//reflect invoke 예외처리
				e.printStackTrace();
			} catch(InvocationTargetException e) {
				//reflect invoke 예외처리
				e.printStackTrace();
			}
		} else if(uri.equals("read")) {
			query = "select * from board where seq =?";
			
			try{
				BoardBean bean = ReflectUtil.requestToSetBean(request, new BoardBean());
				bean = svc.selectOneService(query, bean,bean.getClass());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/board/boardRead.jsp");
				request.setAttribute("bean", bean);
				logger.info(bean.toString());
				dispatcher.forward(request, response);
			}catch(SQLException e){
				e.printStackTrace();
			}catch(IllegalAccessException e){
				e.printStackTrace();
			}
		} else if(uri.equals("write")) {
			response.sendRedirect(root+"/jsp/board/boardWrite.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		CommonService svc = new CommonService();
		String root = request.getContextPath();
		//uri root + 기본 uri sign을 제외한 뒷부분만 자름
		uri = uri.replaceAll(root + "/board/", "");
		String query;
		String message;
		String view;
		
		if(uri.equals("write.do")) {
			query = "insert into BOARD(SEQ,REG_ID,TITLE,CONTENTS) "
					+ "values( (select max(seq)+1 from board) ,?,?,?)";
			try {
				BoardBean bean = ReflectUtil.requestToSetBean(request, new BoardBean());
				if(svc.insertBeanService(query, bean)) {
					message = "게시글 등록 성공";
					view = root+"/board/list";
					alertForward(response, message, view);
				} else {
					message = "게시글 등록 실패하였습니다.";
					alertHistoryBack(response, message);
				}
			}catch(IllegalAccessException e) {
				e.printStackTrace();
			}catch(SQLException e) {
				e.printStackTrace();
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
