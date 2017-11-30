package mvc.co.kr.ucs.service;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import mvc.co.kr.ucs.bean.SignBean;
import mvc.co.kr.ucs.dao.StudyDAO;

@Service
public class SignServiceImpl implements SignService{
	
	@Resource
	private StudyDAO dao;
	
	public boolean login(SignBean bean) throws SQLException{
		return 1<= (Integer)dao.SelectOne("sign.login",bean);
	}

	public boolean signUp(SignBean bean) throws SQLException{
		return 1<= (Integer)dao.insert("sign.join",bean);
	}
	
}
