package co.kr.ucs.spring.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.kr.ucs.spring.bean.BoardBean;

@Repository
public class DAO_mvc {
	
	@Autowired
	private SqlSession sql;
	
	@Test
	public Object test() {
		HashMap<String,Object> map= new HashMap();
		ArrayList ifStatement = new ArrayList();
		HashMap field = new HashMap();
		field.put("field", "TITLE");
		field.put("value", "title");
		ifStatement.add(field);
		field = new HashMap();
		field.put("field", "REG_ID");
		field.put("value", "ksm");
		ifStatement.add(field);
		
		map.put("ifStatement", ifStatement);
		map.put("tableName", "board");
		BoardBean bean = new BoardBean();
		bean.setSeq(102);
		return sql.selectList("sql.common",map);
	}
	public Object SelectOne(String statement,Object parameter) {
		return sql.selectOne(statement,parameter);
	}
	public Object SelectOne(String statement) {
		return sql.selectOne(statement);
	}
	
	public List<Object> selectList(String statement,Object parameter) {
		return sql.selectList(statement,parameter);
	}
	public List<Object> selectList(String statement) {
		return sql.selectList(statement);
	}
	public int update(String statement,Object parameter) {
		return sql.update(statement, parameter);
	}
	public int update(String statement) {
		return sql.update(statement);
	}
	public int delete(String statement,Object parameter) {
		return sql.delete(statement,parameter);
	}
	public int delete(String statement) {
		return sql.delete(statement);
	}
	public int insert(String statement,Object parameter) {
		return sql.insert(statement, parameter);
	}
	public int insert(String statement) {
		return sql.insert(statement);
	}
}
