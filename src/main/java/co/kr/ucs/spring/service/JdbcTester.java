package co.kr.ucs.spring.service;

import java.sql.SQLException;

import co.kr.ucs.spring.bean.BoardBean;

public class JdbcTester {
	
	private CommonService_jdbc util = new CommonService_jdbc();
	
	//@Test
	public void insertService() throws SQLException{
		String query = "insert into BOARD(SEQ,TITLE,CONTENTS,REG_ID) "
				+ "values( (select max(seq)+1 from board) ,?,?,?)";
		Object[] args = {
			"title2","content2","test2"
		};
		System.out.println(util.insertService(query,args));
	}
	
	//@Test
	public void insertBeanService() throws Exception{
		String query = "insert into BOARD(SEQ,TITLE,CONTENTS,REG_ID) "
				+ "values( (select max(seq)+1 from board) ,?,?,?)";
		BoardBean bean = new BoardBean();
		bean.setReg_date("reg_id");
		bean.setTitle("title");
		bean.setContents("content");
		System.out.println(util.insertBeanService(query,bean));
	}
	
	//@Test
	public void selectList() throws Exception{
		String query = "select * from board";
		
		System.out.println(util.selectList(query, BoardBean.class));
	}
	//@Test
	public void selectList_query() throws Exception{
		String query = "select * from board";
		
		System.out.println(util.selectList(query));
	}
	//@Test
	public void selectOneService() throws Exception {
		String query = "select * from board where seq=100";
		System.out.println(util.selectOneService(query,BoardBean.class));
	}
	
	//@Test
	public void selectOneService_query() throws Exception{
		System.out.println(util.selectOneService("select count(*) from board where seq=100"));
	}
	/*
	 * @Test insert, update 쿼리 가능 mybatis 형식쿼리문장..#은 미구현
	 * preparestaetment $만 구현
	 */
	public void updateService() throws Exception{
		String query = "insert into board(SEQ,title,reg_id,contents)"
				+ " values( (select max(seq) from board)+1,${title},${reg_id},${contents})";
		BoardBean bean = new BoardBean();
		bean.setTitle("title");
		bean.setReg_id("regid");
		bean.setContents("content");
		
		System.out.println(util.updateService(query, bean));
	}
	
}
