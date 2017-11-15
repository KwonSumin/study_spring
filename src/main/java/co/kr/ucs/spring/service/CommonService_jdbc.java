package co.kr.ucs.spring.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import co.kr.ucs.spring.common.ReflectUtil;
import co.kr.ucs.spring.dao.RowMapperImpl;

public class CommonService_jdbc {
	
	
	/*
	 * @Autowired 적용이 안되어서 GenericXmlApplicationContext
	 * 으로 의존성 주입
	 */
	
	private JdbcTemplate jdbcTemplate;
	
	public CommonService_jdbc() {
		super();
		String path = "classpath:config/beans.xml";
		jdbcTemplate = (JdbcTemplate) new GenericXmlApplicationContext(path)
				.getBean("jdbcTemplate");
	}
	
	
	//테스트 완료
	public boolean insertService(String query,Object... args) throws SQLException {
		boolean isSuccess = false;
		return jdbcTemplate.update(query,args)>=1;
	}
	//테스트 완료
	public boolean insertBeanService(String query,Object bean) throws SQLException,IllegalAccessException {
		/*
		 * @Test로 테스트 완료
		 */
		boolean isSuccess = false;
		ArrayList<Object> values = ReflectUtil.getNotNullValues(bean);
		Object[] args = new Object[values.size()];
		args = values.toArray(args);
		return jdbcTemplate.update(query,args)>=1;
	}
	
	
	//테스트 완료
	public List selectList(String query)
	
			throws SQLException,IllegalAccessException,InstantiationException,InvocationTargetException  {
		List list = jdbcTemplate.queryForList(query);
		System.out.println(list.get(0).getClass().getSimpleName());
		return list;
	}
	
	//테스트 완료
	public <T>ArrayList selectList(String query,Class<T> type)
			throws SQLException,IllegalAccessException,InstantiationException,InvocationTargetException  {
		ArrayList<T> list = new ArrayList();
		list = (ArrayList<T>)jdbcTemplate.query(query, new RowMapperImpl(type));
		return list;
	}
	//테스트 완료
	public <T>T selectOneService(String query,Class<T> type) throws SQLException,IllegalAccessException  {
		return (T)jdbcTemplate.queryForObject(query, new RowMapperImpl(type));
	}
	
	//테스트 완료
	public Map selectOneService(String query) throws SQLException,IllegalAccessException  {
		return jdbcTemplate.queryForMap(query);
	}
	
	
	/*
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
	*/
}
