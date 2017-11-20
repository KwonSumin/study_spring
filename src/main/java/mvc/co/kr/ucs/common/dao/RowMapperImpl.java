package mvc.co.kr.ucs.common.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import mvc.co.kr.ucs.common.util.ReflectUtil;

public class RowMapperImpl<T> implements RowMapper {
	
	
	private T obj;
	public RowMapperImpl(Class<T> target) {
		try {
			this.obj = target.newInstance();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		try {
			ResultSetMetaData meta = rs.getMetaData();
			ArrayList<T> list = new ArrayList();
			int columCount = meta.getColumnCount();
			for(int i=1;i<=columCount;i++) {
				String columName = meta.getColumnName(i).toLowerCase();
				Object arg = rs.getObject(i);
				String method = "set" + ( columName.substring(0, 1) ).toUpperCase() + columName.substring(1);
				ReflectUtil.executeMethod(obj, method, arg);
			}
			return obj;
		}catch(Exception e) {
			e.printStackTrace();
			throw new SQLException("mapRow 에러");
		}
	}
	
}
