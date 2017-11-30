package mvc.co.kr.ucs.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class StudyDAO {
	
	@Autowired
	private SqlSession sql;
	
	public Object SelectOne(String statement,Object parameter) throws SQLException {
		return sql.selectOne(statement,parameter);
	}
	public Object SelectOne(String statement) throws SQLException{
		return sql.selectOne(statement);
	}
	
	public List<?> selectList(String statement,Object parameter) throws SQLException{
		return sql.selectList(statement,parameter);
	}
	public List<?> selectList(String statement) throws SQLException{
		return sql.selectList(statement);
	}
	public int update(String statement,Object parameter) throws SQLException{
		return sql.update(statement, parameter);
	}
	public int update(String statement) throws SQLException{
		return sql.update(statement);
	}
	public int delete(String statement,Object parameter) throws SQLException{
		return sql.delete(statement,parameter);
	}
	public int delete(String statement) throws SQLException{
		return sql.delete(statement);
	}
	public int insert(String statement,Object parameter) throws SQLException{
		return sql.insert(statement, parameter);
	}
	public int insert(String statement) throws SQLException{
		return sql.insert(statement);
	}
}