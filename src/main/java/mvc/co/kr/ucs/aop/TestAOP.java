package mvc.co.kr.ucs.aop;

import java.util.ArrayList;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TestAOP {
	
	private boolean hasNeedLog = true;
	
	
	
	public boolean isHasNeedLog() {
		return hasNeedLog;
	}

	public void setHasNeedLog(boolean hasNeedLog) {
		this.hasNeedLog = hasNeedLog;
	}

	public TestAOP() {
		System.out.println("aop생성");
	}
	
	@Pointcut("execution(public * mvc.co.kr.ucs..*(..))")
	public void all() {
		
	}
	
	@Before("execution(public * mvc.co.kr.ucs.dao..*(..))")
	public void before(JoinPoint jp) {
		MethodInfo info = new MethodInfo();
		
		if(hasNeedLog) {
			String methodName = jp.getSignature().getName();
			String className = jp.getSignature().getDeclaringTypeName();
			info.setMethodName(jp.getSignature().getName());
			info.setClassName(jp.getSignature().getDeclaringTypeName());
			System.out.printf("[%s.%s()]\n",className,methodName);
			System.out.println(info.toString());
		}

	
	}
	
	@Around("all()")
	public Object around(ProceedingJoinPoint jp) throws Exception{
		System.out.println("around");
		
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
			throw new Exception("around error!");
		}
		
	}
	
	
}


class MethodInfo {
	String className;
	String methodName;
	String simpleClassName;
	String[] argTypes;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
		this.simpleClassName = getSimpleClassName();
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getSimpleClassName() {
		return this.className.substring(this.className.lastIndexOf(".")+1);
	}
	@Override
	public String toString() {
		return "MethodInfo [className=" + className + ", methodName=" + methodName + ", simpleClassName="
				+ simpleClassName + "]";
	}
	
	
	
	
}