package co.kr.ucs.spring.mybatis;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.kr.ucs.spring.bean.BoardBean;
import co.kr.ucs.spring.common.ReflectUtil;

public class PersonalMybatis {
	
	public static void main(String[] args) throws Exception{
		BoardBean bean = new BoardBean();
		bean.setSeq(100);
		bean.setReg_id("user id");
		bean.setContents("content");
		String query = "insert into board(${seq},${reg_id},${contents})";
		//System.out.println(setStatement(query, bean));
		
		Object[] arr = sortValues(query, bean);
		System.out.println(Arrays.asList(arr));
	}
	
	public static String setStatement(String query,Object bean) throws Exception {
		Pattern p = Pattern.compile("\\$\\{[^}]*\\}");
		String[] values = {};
		
		Matcher m = p.matcher(query);
		
		while(m.find()){
			String method = "get";
			String temp = m.group().replace("${", "");
			temp = temp.replace("}", "").toLowerCase();
			method += temp.substring(0, 1).toUpperCase() + 
					temp.substring(1);
			System.out.println(method);
			Object value = ReflectUtil.executeMethod(bean, method);
			if(!value.getClass().getName().equals("java.lang.Integer"))
				value = "'"+value.toString()+"'";
			query = query.replaceFirst(p.pattern(), value.toString());
		}
		return query;
	}
	
	
	//${}파싱하여 객체 필드명 순서대로 value값 정리
	public static Object[] sortValues(final String query,Object bean) throws Exception {
		Object[] result;
		Pattern p = Pattern.compile("\\$\\{[^}]*\\}");
		String[] values = {};
		Matcher m = p.matcher(query);
		int i=0;
		result = new Object[query.split(p.pattern()).length-1];
		while(m.find()){
			String method = "get";
			String temp = m.group().replace("${", "");
			temp = temp.replace("}", "").toLowerCase();
			method += temp.substring(0, 1).toUpperCase() + 
					temp.substring(1);
			System.out.println(method);
			Object value = ReflectUtil.executeMethod(bean, method);
			System.out.println(value);
			result[i++] = value.toString();
		}
		
		System.out.println(query);
		for(Object t : result) System.out.println(t);
		return result;
	}
	
	
}
