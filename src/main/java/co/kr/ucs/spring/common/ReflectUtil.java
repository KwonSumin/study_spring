package co.kr.ucs.spring.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class ReflectUtil {
	public static void main(String[] args) {
		/*int num = 1;
		Object obj = new Object();
		obj.getClass().cast(num);
		obj = num;
		System.out.println(obj.getClass().getSimpleName());*/
	}
	
	public static Object executeMethod(Object obj,String method,Object...args)
	throws IllegalAccessException,InvocationTargetException{
		//1차 테스트 완료
		Method[] methods = obj.getClass().getDeclaredMethods();
		for(Method target : methods) {
			target.setAccessible(true);
			String name = target.getName();
			if(name.equals(method)) {
				Class[] types = target.getParameterTypes();
					
				
				for(int i=0;i<=types.length-1;i++) {
					
					args[i] = castType(args[i],types[i]);
					
				}
				
				
				return target.invoke(obj, args);
			}
		}
		return null;
	}
	
	
	public static <T> T requestToSetBean(HttpServletRequest request,T obj) throws IllegalAccessException {
		//1차 테스트 완료
		Enumeration<String> names = request.getParameterNames();
		Field[] Fields = obj.getClass().getDeclaredFields();
		while(names.hasMoreElements()) {
			String requestParam = names.nextElement();
			
			for(Field field : Fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				System.out.println(fieldName);
				if(requestParam.equals(fieldName)) {
					Object value = request.getParameter(requestParam);
					value = ReflectUtil.castType(value, field.getType());
					field.set(obj, value);
				
				}
			}
		}
		return obj;
	}
	
	public static<T> ArrayList<T> resultSetToBean(ResultSet rs,T obj) throws SQLException,IllegalAccessException,InvocationTargetException,InstantiationException {
		//1차테스트 완료
		ResultSetMetaData meta = rs.getMetaData();
		ArrayList<T> list = new ArrayList();
		int columCount = meta.getColumnCount();
		while(rs.next()) {
			obj = (T)obj.getClass().newInstance();
			for(int i=1;i<=columCount;i++) {
				String columName = meta.getColumnName(i).toLowerCase();
				Object arg = rs.getObject(i);
				String method = "set" + ( columName.substring(0, 1) ).toUpperCase() + columName.substring(1);
				executeMethod(obj, method, arg);
			}
			list.add(obj);
		}
		
		return list;
	}
	
	public static Object castType(Object target,Class type) throws ClassCastException {//1차 테스트 완료
		if(target != null) {
			String castName = type.getSimpleName();
			String simpleName = target.getClass().getSimpleName();
			
			//TODO : 추가로 필요한 형변환 추가..
			try {
				if(castName.equals("int") && simpleName.equals("BigDecimal")) {
					target = ( (BigDecimal)target ).intValueExact();
				}else if(castName.equals("int") && simpleName.equals("String")){
					target = Integer.parseInt(String.valueOf(target));
				}else if(castName.equals("Integer") && simpleName.equals("String")) {
					
					target = Integer.parseInt(String.valueOf(target));
				}else if(castName.equals("String")){
					target = String.valueOf(target);
				}else {
					type.cast(target);
				}
			}catch(ClassCastException e) {
				throw new ClassCastException("ReflectUtil 형변환 추가해주세요.");
			}
			
			
		}
		return target;
	}
	
	public static ArrayList<Field> getNotNullFields(Object obj) throws IllegalAccessException{
		//1차 테스트 완료
		ArrayList<Field> list = new ArrayList();
		Field[] fileds = obj.getClass().getDeclaredFields();
		for(Field target : fileds) {
			target.setAccessible(true);
			boolean isEmpty = target.get(obj) == null;
			if(!isEmpty) {
				list.add(target);
			}
		}
		return list;
	}
	
	public static ArrayList<Object> getNotNullValues(Object obj) throws IllegalAccessException{
		//1차 테스트 완료
		ArrayList<Object> list = new ArrayList();
		Field[] fileds = obj.getClass().getDeclaredFields();
		for(Field target : fileds) {
			target.setAccessible(true);
			boolean isEmpty = target.get(obj) == null;
			if(!isEmpty) {
				list.add(target.get(obj));
			}
		}
		return list;
	}
}
