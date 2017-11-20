package mvc.co.kr.ucs.template.board.bean;

import mvc.co.kr.ucs.common.bean.PagingBean;

public class BoardBean extends PagingBean {
	private int seq;
	private String title;
	private String contents;
	private String reg_id;
	private String reg_date;
	private String mod_id;
	private String mod_date;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getMod_id() {
		return mod_id;
	}
	public void setMod_id(String mod_id) {
		this.mod_id = mod_id;
	}
	public String getMod_date() {
		return mod_date;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
	@Override
	public String toString() {
		return "BoardBean [seq=" + seq + ", title=" + title + ", contents=" + contents + ", reg_id=" + reg_id
				+ ", reg_date=" + reg_date + ", mod_id=" + mod_id + ", mod_date=" + mod_date + "]";
	}
	
	
}
