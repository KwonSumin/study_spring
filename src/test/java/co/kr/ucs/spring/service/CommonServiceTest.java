package co.kr.ucs.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.kr.ucs.spring.bean.BoardBean;


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
