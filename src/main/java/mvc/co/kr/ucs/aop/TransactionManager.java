package mvc.co.kr.ucs.aop;

import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class TransactionManager{
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public String getStr(String str) throws Exception{
		if(str == null) throw new Exception();
		return str;
	}
	
	@Test
	public void test() throws Exception{
		System.out.println(getStr(null));
	}
}

