package mvc.co.kr.ucs.bean;

import com.google.gson.Gson;

public class PagingBean {
	private int currentPage = 1; //현재 페이지
	private int totalData; //토탈 데이터 개수
	private int totalPage;
	private int limitRow = 10; //리스트에 가져올 row갯수
	private int limitPage = 10; //페이징할 페이지 개수
	private int startRowNum = 0;	//데이터 가져올때필요	
	private int endRowNum;	//데이터 가져올때필요
	private String search;
	private String searchTarget;
	public void pagingSet() {
		this.startRowNum = (currentPage - 1) * limitRow;
		this.endRowNum = startRowNum + limitRow;
		if(totalData < endRowNum) endRowNum = totalData;
	}
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getSearchType() {
		return searchTarget;
	}
	public void setSearchType(String searchTarget) {
		this.searchTarget = searchTarget;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalData() {
		return totalData;
	}
	public void setTotalData(int totalData) {
		this.totalData = totalData;
		pagingSet();
	}
	public int getLimitRow() {
		return limitRow;
	}
	public void setLimitRow(int limitRow) {
		this.limitRow = limitRow;
	}
	public int getLimitPage() {
		return limitPage;
	}
	public void setLimitPage(int limitPage) {
		this.limitPage = limitPage;
	}
	public int getStartRowNum() {
		return startRowNum;
	}
	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}
	public int getEndRowNum() {
		return endRowNum;
	}
	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}
	public String getSearchTarget() {
		return searchTarget;
	}
	public void setSearchTarget(String searchTarget) {
		this.searchTarget = searchTarget;
	}
	public String getJson() {
		return new Gson().toJson(this);
	}
	@Override
	public String toString() {
		return "PagingBean [currentPage=" + currentPage + ", totalData=" + totalData + ", limitRow=" + limitRow
				+ ", limitPage=" + limitPage + ", startRowNum=" + startRowNum + ", endRowNum=" + endRowNum + ", search="
				+ search + ", searchTarget=" + searchTarget + "]";
	}
	
	
}
