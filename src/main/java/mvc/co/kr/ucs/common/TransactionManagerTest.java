package mvc.co.kr.ucs.common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class TransactionManagerTest extends DataSourceTransactionManager{
	
	public TransactionManagerTest(DataSource dataSource) {
		super(dataSource);
		System.out.println(dataSource.getClass().getName());
		System.out.println("트랜잭션 메니저 생성");
	}

	@Override
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		return super.getDataSource();
	}

	@Override
	public void setEnforceReadOnly(boolean enforceReadOnly) {
		// TODO Auto-generated method stub
		super.setEnforceReadOnly(enforceReadOnly);
	}

	@Override
	public boolean isEnforceReadOnly() {
		// TODO Auto-generated method stub
		return super.isEnforceReadOnly();
	}

	@Override
	public void afterPropertiesSet() {
		// TODO Auto-generated method stub
		super.afterPropertiesSet();
	}

	@Override
	public Object getResourceFactory() {
		// TODO Auto-generated method stub
		return super.getResourceFactory();
	}

	@Override
	protected Object doGetTransaction() {
		// TODO Auto-generated method stub
		return super.doGetTransaction();
	}

	@Override
	protected boolean isExistingTransaction(Object transaction) {
		// TODO Auto-generated method stub
		return super.isExistingTransaction(transaction);
	}

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		// TODO Auto-generated method stub
		super.doBegin(transaction, definition);
	}

	@Override
	protected Object doSuspend(Object transaction) {
		// TODO Auto-generated method stub
		return super.doSuspend(transaction);
	}

	@Override
	protected void doResume(Object transaction, Object suspendedResources) {
		// TODO Auto-generated method stub
		super.doResume(transaction, suspendedResources);
	}

	@Override
	protected void doCommit(DefaultTransactionStatus status) {
		System.out.println("커밋 됩니다.");
		super.doCommit(status);
	}

	@Override
	protected void doRollback(DefaultTransactionStatus status) {
		System.out.println("롤백 됩니다.");
		super.doRollback(status);
	}

	@Override
	protected void doSetRollbackOnly(DefaultTransactionStatus status) {
		// TODO Auto-generated method stub
		super.doSetRollbackOnly(status);
	}

	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		// TODO Auto-generated method stub
		super.doCleanupAfterCompletion(transaction);
	}

	@Override
	protected void prepareTransactionalConnection(Connection con, TransactionDefinition definition)
			throws SQLException {
		// TODO Auto-generated method stub
		super.prepareTransactionalConnection(con, definition);
	}
	
}
