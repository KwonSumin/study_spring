package co.kr.ucs.spring.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.kr.ucs.spring.common.ReflectUtil;



public class DBConnectionPool {
	
	private final int ACTIVE = 1;
	private final int FREE = 0;
	
	private int initConns; //초기에 pool 에 생성할 Connection 개수
	private int maxConns; //pool 에 생성할 최대 connection 개수
	private int currConns; //현재까지 생성된 Connection 개수
	
	private long timeOut = 1000 * 5; // connection 을 얻을때 대기시간 30초
	
	private String url; // DB 계정 URL
	private String id; // DB 계정 ID
	private String pw; // DB 계정 Password
	
	private int[] connStatus; // Connection Pool의 상태
	private Connection[] connPool; //connection pool
	
	private static int count = 0;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionPool.class);
	
	
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public DBConnectionPool(String url, String id, String pw) 
			throws SQLException, ClassNotFoundException {
		super();
		this.url = url;
		this.id = id;
		this.pw = pw;
		
		connPool = new Connection[maxConns];
		connStatus = new int[maxConns];
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		for(currConns = 0; currConns <= initConns-1; currConns++) {
			connPool[currConns] = DriverManager.getConnection(url, id, pw);
		}
	}

	public DBConnectionPool(int initConns, int maxConns, String url, String id,String pw) 
			throws SQLException,ClassNotFoundException {
		super();
		this.initConns = initConns;
		this.maxConns = maxConns;
		this.url = url;
		this.id = id;
		this.pw = pw;
		this.currConns = 0;
		
		connPool = new Connection[maxConns];
		connStatus = new int[maxConns];
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		for(currConns = 0; currConns <= initConns-1; currConns++) {
			connPool[currConns] = DriverManager.getConnection(url, id, pw);
		}
		
		
	}
	
	
	
	private Connection createConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(url, id, pw);
		return conn;
	}
	
	public synchronized Connection getConnection() 
			throws SQLTimeoutException,SQLException {
		long checkTime = new Date().getTime();
		long timeOut = getTimeOut();
		int freeIdx = -1;
		boolean flag = true;
		long countTime = 0L;
		flag = true;
		Connection conn = null;
		
		
		while(conn==null) {
			
			countTime = (new Date().getTime() - checkTime);
			for(int i=0;i<=connStatus.length-1;i++) {
				if(connStatus[i] == FREE) {
					freeIdx = i;
					break;
				}
			}
			boolean hasFreeIdx = freeIdx != -1;
			boolean isTimeOut = countTime >= timeOut ;
			boolean isOverConn = currConns > maxConns;
			if(isOverConn && flag) {
				logger.info("max초과 대기중");
				flag = false;
			}
			if(hasFreeIdx && !isOverConn) {
				if( connPool[freeIdx] == null ) {
					logger.debug(freeIdx +"인덱스 connection 만들기");
					connPool[freeIdx] = createConnection();
				}
				conn = connPool[freeIdx];
				connStatus[freeIdx] = ACTIVE;
				currConns++;
				logger.debug("연결 인덱스 : " + freeIdx);
			}
			if( isTimeOut ) {
				logger.info(String.valueOf(freeIdx));
				logger.info("타임아웃");
				logger.info(String.valueOf(Arrays.asList(connStatus)));
				throw new SQLTimeoutException();
			}
		}
		logger.debug("경과시간 : " + (new Date().getTime() - checkTime ) );
		return conn;
	}
	
	public synchronized void freeConnection(Connection conn) {
		int freeIdx = -1;
		
		for(int i=0;i<=connStatus.length-1;i++) {
			if(connStatus[i] == ACTIVE) {
				freeIdx = i;
			}
		}
		
		connPool[freeIdx] = conn;
		connStatus[freeIdx] = 0;
		currConns--;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}
	
	public int insertQuery(String query) throws SQLException{
		int isSuccess = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			logger.debug(query);
			isSuccess = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		logger.debug("result : " + isSuccess);
		return isSuccess;
	}
	
	
	
	public int insertQuery(String query,Object vo) throws SQLException{
		//미완성
		int isSuccess = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			
			logger.debug(query);
			isSuccess = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} 
		logger.debug("result : " + isSuccess);
		return isSuccess;
	}
	
	

	public int insertQuery(String query,ArrayList values) throws SQLException{
		int isSuccess = -1;
		Connection conn = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			for(int i=0; i<=values.size()-1 ;i++) {
				logger.debug(values.get(i).toString());
				pstmt.setObject(i+1, String.valueOf(values.get(i)));
			}
			logger.debug("values : " + values);
			logger.debug(query);
			isSuccess = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		logger.debug("result : " + isSuccess);
		return isSuccess;
	}
	
	public ArrayList<Object> selectList(String query,ArrayList values) throws SQLException{
		ArrayList list = new ArrayList();
		Connection conn = null;
		HashMap result = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			
			for(int i=0; i<=values.size()-1 ;i++) {
				logger.debug(values.get(i).toString());
				pstmt.setObject(i+1, String.valueOf(values.get(i)));
			}
			logger.debug("values : " + values);
			logger.debug(query);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columCount = rsMeta.getColumnCount();
			while(rs.next()) {
				result = new HashMap();
				for(int i=1; i<= columCount; i++)
					result.put(rsMeta.getColumnName(i), rs.getObject(i));
				list.add(result);
			}
			logger.debug(result.toString());
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	public <T> ArrayList<T> selectList(String query,ArrayList values,T bean) 
			throws SQLException,IllegalAccessException,InstantiationException, 
		InvocationTargetException{
		ArrayList<T> list = new ArrayList();
		Connection conn = null;
		Object obj = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			
			for(int i=0; i<=values.size()-1 ;i++) {
				logger.debug(String.valueOf(values.get(i)));
				pstmt.setObject(i+1, String.valueOf(values.get(i)));
			}
			logger.debug("values : " + values);
			logger.debug(query);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columCount = rsMeta.getColumnCount();
			
			logger.debug("리스트 테스트");
			list = ReflectUtil.resultSetToBean(rs, bean);
			
			
			logger.debug(list.toString());
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		
		return list;
	}
	
	public <T>ArrayList<Object> selectList(String query,Object bean) 
			throws SQLException,IllegalAccessException,InstantiationException, 
		InvocationTargetException{
		ArrayList list = new ArrayList();
		Connection conn = null;
		Object obj = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			logger.debug(query);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columCount = rsMeta.getColumnCount();
			while(rs.next()) {
				obj = (T)bean.getClass().newInstance();
				for(int i=1; i<= columCount; i++) {
					String columName = rsMeta.getColumnName(i).toLowerCase();
					String methodName = "set"+columName.substring(0,1).toUpperCase();
					methodName += columName.substring(1);
					Object argument = rs.getObject(i);
					ReflectUtil.executeMethod(obj, methodName, argument);
				}
				list.add(obj);
			}
			logger.debug(list.toString());
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	public ArrayList selectList(String query) throws SQLException {
		ArrayList list = new ArrayList();
		Connection conn = null;
		HashMap result = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			
			logger.debug(query);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columCount = rsMeta.getColumnCount();
			while(rs.next()) {
				result = new HashMap();
				for(int i=1; i<= columCount; i++)
					result.put(rsMeta.getColumnName(i), rs.getObject(i));
				list.add(result);
			}
			logger.debug(String.valueOf(result));
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	public <T> ArrayList selectList(String query,Class type) throws SQLException {
		ArrayList list = new ArrayList();
		Connection conn = null;
		T result = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			
			logger.debug(query);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columCount = rsMeta.getColumnCount();
			while(rs.next()) {
				result = (T)type.newInstance();
				for(int i=1; i<= columCount; i++){
					String method = "set";
					String name = rsMeta.getColumnName(i);
					String temp = name.substring(0,1);temp = temp.toUpperCase();
					name = temp + name.substring(1).toLowerCase();
					method += name;
					ReflectUtil.executeMethod(result, method, rs.getObject(i));				
				}
				list.add(result);
			}
			logger.debug(String.valueOf(result));
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(InstantiationException e){
			e.printStackTrace();
		}
		
		return list;
	}
	public HashMap selectOne(String query,ArrayList values) throws SQLException {
		Connection conn = null;
		HashMap result = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			
			for(int i=0; i<=values.size()-1 ;i++) {
				pstmt.setObject(i+1, String.valueOf(values.get(i)));
			}
			logger.debug("values : " + values);
			logger.debug(query);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columCount = rsMeta.getColumnCount();
			if(rs.next()) {
				result = new HashMap();
				for(int i=1; i<= columCount; i++)
					result.put(rsMeta.getColumnName(i), rs.getObject(i));
			}
			logger.debug("result : "+result);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	
	
	
	public<T> T selectOne(String query,T bean,ArrayList values,Class type) throws SQLException {
		Connection conn = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			for(int i=0; i<=values.size()-1 ;i++) {
				pstmt.setObject(i+1, String.valueOf(values.get(i)));
			}
			logger.debug(query);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columCount = rsMeta.getColumnCount();
			bean = (T)type.newInstance();
			if(rs.next()) {
				for(int i=1; i<= columCount; i++){
					String method = "set";
					String name = rsMeta.getColumnName(i);
					String temp = name.substring(0,1);temp = temp.toUpperCase();
					name = temp + name.substring(1).toLowerCase();
					method += name;
					ReflectUtil.executeMethod(bean, method, rs.getObject(i));
				}
			}
			logger.debug("result : "+bean);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}catch(IllegalAccessException e) {
			e.printStackTrace();
		}catch(InstantiationException e){
			e.printStackTrace();
		}
		
		return bean;
	}
	
	
	
	public <T> T selectOne(String query,ArrayList values,T result) 
			throws SQLException, InvocationTargetException,IllegalAccessException{
		Connection conn = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			
			for(int i=0; i<=values.size()-1 ;i++) {
				pstmt.setObject(i+1, String.valueOf(values.get(i)));
			}
			logger.debug("values : " + values);
			logger.debug(query);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columCount = rsMeta.getColumnCount();
			if(rs.next()) {
				for(int i=1; i<= columCount; i++) {
					String columName = rsMeta.getColumnName(i).toLowerCase();
					String methodName = "set" + (columName.substring(0, 1).toUpperCase()) + columName.substring(1);
					
					ReflectUtil.executeMethod(result, methodName, rs.getObject(i)); 
			
				}
			}
			logger.debug("result : "+result);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	public Object selectOne(String query) throws SQLException {
		Connection conn = null;
		Object result = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			logger.debug(query);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columCount = rsMeta.getColumnCount();
			if(rs.next()) {
				result = new HashMap();
				for(int i=1; i<= columCount; i++)
					result =  rs.getObject(i);
			}
			logger.debug(String.valueOf(result));
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return result;
	}
	
	public int updateQuery(String query,ArrayList values)  throws SQLException{
		int result = 0;
		
		Connection conn = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			
			for(int i=0; i<=values.size()-1 ;i++) {
				pstmt.setObject(i+1, String.valueOf(values.get(i)));
			}
			logger.debug("values : " + values);
			logger.debug(query);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} 
		logger.debug("result : " + result);
		return result;
	}
	public int updateQuery(String query)  throws SQLException{
		int result = 0;
		
		Connection conn = null;
		try {
			conn = getConnection();
			logger.debug(query);
			pstmt = conn.prepareStatement(query);
			
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		logger.debug("result : " + result);
		return result;
	}
	
}
