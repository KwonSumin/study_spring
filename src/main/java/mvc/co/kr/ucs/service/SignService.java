package mvc.co.kr.ucs.service;

import java.sql.SQLException;

import mvc.co.kr.ucs.bean.SignBean;

public interface SignService {
	public boolean login(SignBean bean)throws SQLException;
	public boolean signUp(SignBean bean)throws SQLException;
}
