package co.kr.ucs.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



public class DBConnectionPoolManager {
	
	
	private static String DEFAULT_POOLNAME = "DEFAULT";
	private static Map<String, DBConnectionPool> dbPool = new HashMap();
	private static DBConnectionPoolManager instance = new DBConnectionPoolManager();
	
	public static DBConnectionPoolManager getInstance() {
		return instance;
	}
	
	public void setDBPool(String url,String id,String pw) 
			throws ClassNotFoundException,SQLException{
		dbPool.put(DEFAULT_POOLNAME, new DBConnectionPool(url, id, pw));
	}
	
	public void setDBPool(String poolName, String url,String id,String pw) 
			throws ClassNotFoundException,SQLException{
		dbPool.put(poolName, new DBConnectionPool(url, id, pw));
	}
	
	public void setDBPool(String poolName,String url,String id,
		String pw,int initConns,int maxConns) throws SQLException {
		try {
			DBConnectionPool pool = new DBConnectionPool(initConns, maxConns, url, id,pw);
			dbPool.put(poolName, pool);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public DBConnectionPool getDBPool() {
		return dbPool.get(DEFAULT_POOLNAME);
	}
	
	public DBConnectionPool getDBPool(String poolName) {
		return dbPool.get(poolName);
	}
}
