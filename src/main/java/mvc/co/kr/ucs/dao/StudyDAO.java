package mvc.co.kr.ucs.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class StudyDAO {
	
	@Autowired
	private SqlSession sql;
	
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