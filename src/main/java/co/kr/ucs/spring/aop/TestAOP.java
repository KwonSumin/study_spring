package co.kr.ucs.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TestAOP {
	
	
	
	public TestAOP() {
		System.out.println("aop생성");
	}
	
	@Pointcut("execution(public * co.kr.ucs..*(..))")
	public void all() {
		
	}
	
	@Before("execution(public * co.kr.ucs..*(..))")
	public void before(JoinPoint jp) {
		String methodName = jp.getSignature().getName();
		String className = jp.getSignature().getDeclaringTypeName();
		System.out.printf("[%s.%s()]\n",className,methodName);
	}
	
	@Around("all()")
	public Object around(ProceedingJoinPoint jp) throws Exception{
		System.out.println("around");
		
		try {
			Object[] args = jp.getArgs();
			int i=1;
			for(Object arg : args)System.out.printf("%d. 아규먼트 : %s\n",i++,arg.toString());
			
			return jp.proceed();
		}catch(Throwable e) {
			e.printStackTrace();
			throw new Exception("around error!");
		}
		
	}
	
	
}
