package mvc.co.kr.ucs.common;

import org.springframework.stereotype.Component;

@Component
public class TestScheduler {
	
	public TestScheduler() {
		System.out.println("스케쥴러 생성");
	}

	//@Scheduled(cron="* 0/10 * * * *") 
	public void printCurrentTime() {
		System.out.println(System.currentTimeMillis());
	}
}
