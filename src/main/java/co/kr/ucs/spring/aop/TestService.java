package co.kr.ucs.spring.aop;

import org.springframework.stereotype.Component;

@Component
public class TestService {
	public void print(String str) {
		System.out.println("테스트 서비스");
		System.out.println(str);
	}
}
