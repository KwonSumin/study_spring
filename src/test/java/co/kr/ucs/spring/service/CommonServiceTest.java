package co.kr.ucs.spring.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.kr.ucs.spring.common.ReflectUtil;
import co.kr.ucs.spring.dao.DBConnectionPool;
import co.kr.ucs.spring.dao.DBConnectionPoolManager;
import co.kr.ucs.spring.dao.RowMapperImpl;
import co.kr.ucs.spring.spring.bean.BoardBean;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
	"classpath:config/beans.xml"
})
public class CommonServiceTest {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private DBConnectionPoolManager dbManager;
	public CommonServiceTest() {
		super();
		String url = "jdbc:oracle:thin:@220.76.203.39:1521:UCS";
		String id = "UCS_STUDY";
		String pw = "qazxsw";
		int initConns = 3;
		int maxConns = 20;
		
		dbManager = DBConnectionPoolManager.getInstance();
		try{
			dbManager.setDBPool("commonPool", url, id, pw, initConns, maxConns);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test() throws Exception{
		System.out.println("테스트");
		System.out.println(jdbcTemplate.queryForObject("select * from board where seq = 100", new RowMapperImpl(BoardBean.class)));
		System.out.println("테스트 완료");
	}
	
	public boolean insertService(String query,Object... args) throws SQLException {
		boolean isSuccess = false;
		ArrayList<Object> values = new ArrayList();
		DBConnectionPool dao = dbManager.getDBPool("commonPool");
		
		
		for(Object target : args) {
			values.add(target);
		}
		if(args != null) {
			isSuccess = (dao.insertQuery(query, values)>=1);
		} else {
			isSuccess = (dao.insertQuery(query)>=1);
		}
		return isSuccess;
	}
	
	public boolean insertBeanService(String query,Object bean) throws SQLException,IllegalAccessException {
		//미확인
		boolean isSuccess = false;
		DBConnectionPool dao = dbManager.getDBPool("commonPool");
		ArrayList<Object> values = ReflectUtil.getNotNullValues(bean);
		if(dao.insertQuery(query, values)>=1) isSuccess = true;
		return isSuccess;
	}
	
	
	
	public ArrayList listService(String query,Object... args) throws SQLException  {
		ArrayList list = new ArrayList();
		ArrayList<Object> values = new ArrayList();
		DBConnectionPool dao = dbManager.getDBPool("commonPool");
		for(Object target : args) {
			values.add(target);
		}
		if(args!=null) {
			list = dao.selectList(query,values);
		} else {
			list = dao.selectList(query);
		}
		
		return list;
	}
	
	public ArrayList selectList(String query,Object bean)
			throws SQLException,IllegalAccessException,InstantiationException,InvocationTargetException  {
		ArrayList<Object> list = new ArrayList();
		DBConnectionPool dao = dbManager.getDBPool("commonPool");
		
		ArrayList values = ReflectUtil.getNotNullValues(bean);
		
		list = dao.selectList(query, values, bean);
		
		return list;
	}
	
	public ArrayList selectList(String query)
			throws SQLException,IllegalAccessException,InstantiationException,InvocationTargetException  {
		ArrayList<Object> list = new ArrayList();
		DBConnectionPool dao = dbManager.getDBPool("commonPool");
		
		
		list = dao.selectList(query);
		
		return list;
	}
	public ArrayList selectList(String query,Class type)
			throws SQLException,IllegalAccessException,InstantiationException,InvocationTargetException  {
		ArrayList<Object> list = new ArrayList();
		DBConnectionPool dao = dbManager.getDBPool("commonPool");
		
		
		list = dao.selectList(query,type);
		
		return list;
	}
	public Object selectOneService(String query,Object bean) throws SQLException,IllegalAccessException  {
		HashMap<String, Object> result = new HashMap();
		ArrayList<Object> values = ReflectUtil.getNotNullValues(bean);
		DBConnectionPool dao = dbManager.getDBPool("commonPool");
		
		return (bean = dao.selectOne(query, values));
	}
	public <T> T selectOneService(String query,T bean,Class type) throws SQLException,IllegalAccessException  {
		HashMap<String, Object> result = new HashMap();
		ArrayList<Object> values = ReflectUtil.getNotNullValues(bean);
		DBConnectionPool dao = dbManager.getDBPool("commonPool");
		
		return (bean = dao.selectOne(query, bean,values,type));
	}
	public Object selectOneService(String query) throws SQLException,IllegalAccessException  {
		HashMap<String, Object> result = new HashMap();
		DBConnectionPool dao = dbManager.getDBPool("commonPool");
		
		return dao.selectOne(query);
	}
	public int updateService(String query,Object... args) throws SQLException{
		int result = -1;
		ArrayList<Object> values = new ArrayList();
		DBConnectionPool dao = dbManager.getDBPool("commonPool");
		
		for(Object target : args) {
			values.add(target);
		}
		if(args!=null) {
			result = dao.updateQuery(query,values);
		} else {
			result = dao.updateQuery(query);
		}
		
		return result;
	}
	
}
