package mvc.co.kr.ucs.bean;

public class TestClass {
	String test = "test123132";

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	@Override
	public String toString() {
		return "TestClass [test=" + test + "]";
	}
	
}
