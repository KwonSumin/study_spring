package co.kr.ucs.spring.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mvc.co.kr.ucs.bean.BoardBean;
import mvc.co.kr.ucs.service.BoardService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
		"file:src/main/webapp/WEB-INF/spring/**/*-context.xml",
})
public class MvcTester {
	
	@Resource
	private BoardService svc;
	
	@Test
	public void test() {
		BoardBean bean = new BoardBean();
		bean.setTitle("제목123");
		bean.setContents("content123");
		bean.setReg_id("tester");
		try {
			System.out.println(svc.insertBoard(bean));;
		}catch(Exception e) {}
	}
}
