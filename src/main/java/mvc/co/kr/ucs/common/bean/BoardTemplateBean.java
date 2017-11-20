package mvc.co.kr.ucs.common.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class BoardTemplateBean {
	private TestClass test = new TestClass();
	private List list;
	private PagingBean paging;
	private ArrayList<Map> info;
	public void addInfo(String fieldName,String dataName) {
		if(info == null) info = new ArrayList();
		HashMap<String,String> map = new HashMap();
		map.put("fieldName", fieldName);
		map.put("dataName", dataName);
		info.add(map);
	}
	public TestClass getTest() {
		return test;
	}
	public void setTest(TestClass test) {
		this.test = test;
	}
	/*
	 * @param : json으로 가져올 필드명
	 * @return : 파라미터에 필드명에 대한 jsonStr
	 * @Description : 페이지가 완전 로드가 된 후에는
	 * 스크립트단에서 접근이 불가능 하므로 보안상 문제가 없을것 같음.
	 */
	public String getJson(String name) {
		Field field;
		try {
			field = this.getClass().getDeclaredField(name);
			return new Gson().toJson(field.get(this));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Map> getInfo() {
		return info;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
		paging.setPaging();
	}
	public PagingBean getPaging() {
		return paging;
	}
	public void setPaging(PagingBean paging) {
		this.paging = paging;
	}
	
	@Override
	public String toString() {
		return "BoardTemplateBean [list=" + list + ", paging=" + paging + ", info=" + info + "]";
	}
	
}

