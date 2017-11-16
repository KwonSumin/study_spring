package mvc.co.kr.ucs.bean;

import java.util.ArrayList;

public class CommonQueryBean {
	private String tableName;
	private ArrayList<Statement> setStatement;
	private ArrayList<Statement> ifStatement;
	
	public void addSet(String field,Object value) {
		if(setStatement==null) setStatement = new ArrayList();
		setStatement.add(new Statement(field,value));
	}
	public void addIf(String field,Object value) {
		if(ifStatement==null) ifStatement = new ArrayList();
		ifStatement.add(new Statement(field,value));
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
	@Override
	public String toString() {
		return "CommonQueryBean [tableName=" + tableName + ", setStatement=" + setStatement + ", ifStatement="
				+ ifStatement + "]";
	}
	
	
}
class Statement{
	private String field;
	private Object value;
	
	public Statement(String field, Object value) {
		super();
		this.field = field;
		this.value = value;
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
		return "Statement [field=" + field + ", value=" + value + "]";
	}
}
