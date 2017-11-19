package co.kr.ucs.spring.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.context.ContextLoaderListener;

import co.kr.ucs.spring.common.ReflectUtil;
import co.kr.ucs.spring.dao.DBConnectionPool;
import co.kr.ucs.spring.dao.DBConnectionPoolManager;
import mvc.co.kr.ucs.bean.BoardBean;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
	/*"classpath:/config/beans.xml"*/
	"file:src/main/webapp/WEB-INF/spring/**/*-context.xml",
		
})
public class CommonServiceTest {
	
	@Autowired
	private CommonService svc;
			
	@Test
	public void test() throws Exception{
		
		
		BoardBean bean = new BoardBean();
		bean.setSeq(102);
		System.out.println(svc.selectOneService("select * from board where seq = ?", bean));
	}
	
}
