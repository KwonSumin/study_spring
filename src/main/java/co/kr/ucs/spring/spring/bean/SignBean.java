package co.kr.ucs.spring.spring.bean;

public class SignBean {
	private String user_id;
	private String user_pw;
	private String user_nm;
	private String email;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "SignBean [user_id=" + user_id + ", user_pw=" + user_pw + ", user_nm=" + user_nm + ", email=" + email
				+ "]";
	}
}
