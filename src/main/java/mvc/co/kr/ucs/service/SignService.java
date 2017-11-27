package mvc.co.kr.ucs.service;

import mvc.co.kr.ucs.bean.SignBean;

public interface SignService {
	public boolean login(SignBean bean);
	public boolean signUp(SignBean bean);
}
