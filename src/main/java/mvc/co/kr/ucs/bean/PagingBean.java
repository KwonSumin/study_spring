package mvc.co.kr.ucs.bean;

/* 기본값 사용법
 * 기본값 1~10페이징 넘버 설정
 * 데이터 베이스 총 리스트 값 개수 totalData에 적용 후 setpaging
 */
public class PagingBean extends CommonQueryBean {
	private int currentPage = 1;
	private int startPage = 1;
	private int endPage = 10;
	private int pageSize = 10;
	private int rowSize = 10;
	private int totalPage;
	private int totalData;
	private int pageIdx = 1;
	private int startRow; //db에서값 받아올때 사용 페이징 처리에서 무관
	private int endRow; //db에서값 받아올때 사용 페이징 처리에서 무관
	private String search;
	private String searchTarget;
	private String seq = "seq";
	public int getRowSize() {
		return rowSize;
	}
	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public void setPaging() {
		this.startRow = currentPage * rowSize-rowSize;
		this.endRow = this.startRow + rowSize;
		this.totalPage = (int)Math.ceil( ((double)totalData) / ((double)rowSize) );
		this.endPage = this.startPage+this.pageSize-1;
		if(totalPage < endPage) 
			endPage = totalPage;//토탈페이지를 넘어가지 않도록 설정
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalData() {
		return totalData;
	}
	public void setTotalData(int totalData) {
		this.totalData = totalData;
	}
	public int getPageIdx() {
		return pageIdx;
	}
	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getSearchTarget() {
		return searchTarget;
	}
	public void setSearchTarget(String searchTarget) {
		this.searchTarget = searchTarget;
	}
	@Override
	public String toString() {
		return "PagingBean [currentPage=" + currentPage + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", pageSize=" + pageSize + ", totalPage=" + totalPage + ", totalData=" + totalData + ", pageIdx="
				+ pageIdx + ", startRow=" + startRow + ", endRow=" + endRow + ", search=" + search + ", searchTarget="
				+ searchTarget + "]";
	}
	
}
