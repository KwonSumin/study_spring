package mvc.co.kr.ucs.aop;

import java.util.ArrayList;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class BoardAOP {
	
	private boolean hasNeedLog = true;
	
	@Pointcut("execution(public * mvc.co.kr.ucs.service.BoardServiceImpl.*(..))")
	public void all() {}
	
	
	@Before("all()")
	public void before(JoinPoint jp) {
		System.out.println(jp.getSignature().getName()+" Before");
	}
	
	@Around("all()")
	public Object around(ProceedingJoinPoint jp) {
		try {
			Object[] args = jp.getArgs();
			ArrayList<Class> types = new ArrayList<Class>();
			int i=1;
			if(hasNeedLog) {
				for(Object arg : args) {
					System.out.printf("%d. 아규먼트 : %s , %s\n",i++,arg.toString(),arg.getClass().getTypeName());
					types.add(arg.getClass());
				}
			}
			return jp.proceed();
		}catch(Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@AfterReturning(pointcut="all()",returning="ret")
	public void returning(JoinPoint jp,Object ret) {
		System.out.printf("returning : %s\n",ret.toString());
	}
	
}
