package mvc.co.kr.ucs.common.bean;

import java.util.ArrayList;
/*
 * @author : sumin
 * @version : 1.0.0
 * @since : 2017. 11. 16
 * @description : mybatis 공통쿼리문 객체
 * 추가 기능 페이징 등 관련 기능 사용시 해당객체 상속받아서 추가 확장.
 */
public class CommonQueryBean {
	private String tableName; //사용할 테이블명
	private String primaryKey;
	private String seqName;
	private ArrayList<Statement> setStatement; //insert,update문 실행시 set 객체
	private ArrayList<Statement> ifStatement; //where절 사용시 객체
	
	/*
	 * @param : 필드명, 값
	 * update시 사용할 set 필드명 = 값에 사용할 객체
	 * @run : 처음 추가시 ArrayList생성 후 필드명,값 입력 후 리스트에 추가.
	 */
	public void addSet(String field,Object value) {
		if(setStatement==null) setStatement = new ArrayList();
		setStatement.add(new Statement(field,value));
	}
	/*
	 * @param : 필드명, 값
	 * where절 사용시 사용할 필드명 = 값에 사용할 객체
	 * @run : 처음 추가시 ArrayList생성 후 필드명,값 입력 후 리스트에 추가.
	 */
	public void addIf(String field,Object value) {
		if(ifStatement==null) ifStatement = new ArrayList();
		ifStatement.add(new Statement(field,value));
	}
	public void addIf(String field,Object value,String option) {
		if(ifStatement==null) ifStatement = new ArrayList();
		ifStatement.add(new Statement(field,value,option));
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public ArrayList<Statement> getSetStatement() {
		return setStatement;
	}
	public void setSetStatement(ArrayList<Statement> setStatement) {
		this.setStatement = setStatement;
	}
	public ArrayList<Statement> getIfStatement() {
		return ifStatement;
	}
	public void setIfStatement(ArrayList<Statement> ifStatement) {
		this.ifStatement = ifStatement;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getSeqName() {
		return seqName;
	}
	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}
	@Override
	public String toString() {
		return "CommonQueryBean [tableName=" + tableName + ", setStatement=" + setStatement + ", ifStatement="
				+ ifStatement + "]";
	}
	
	
}
class Statement{
	private String field;
	private Object value;
	private String option;
	public Statement(String field, Object value) {
		super();
		this.field = field;
		this.value = value;
	}
	public Statement(String field, Object value, String option) {
		super();
		this.field = field;
		this.value = value;
		this.option = option;
	}

	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Statement [field=" + field + ", value=" + value + ", option=" + option + "]";
	}
}
