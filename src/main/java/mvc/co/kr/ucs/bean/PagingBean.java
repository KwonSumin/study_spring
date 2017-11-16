package mvc.co.kr.ucs.bean;

public class PagingBean {
	private int startRowNum;
	private int endRowNum;
	private int total; //필수
	private int currentPage; //필수
	private int limit; //rowLimit 필수
	private int pageCount; //pagetotal
	private String searchTarget;
	private String search;
	private int startPage=1;
	private int endPage;
	
	
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
	public int getPageCount() {
		this.pageCount = (int)Math.ceil( ((double)total) / ((double)limit) );
		return pageCount;
	}
	public String getSearchTarget() {
		return searchTarget;
	}

	public void setSearchTarget(String searchTarget) {
		this.searchTarget = searchTarget;
	}

	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	public int getStartRowNum() {
		setPaging();
		return startRowNum;
	}


	public int getEndRowNum() {
		setPaging();
		return endRowNum;
	}

	
	public void setPaging() {
		this.startRowNum = currentPage * limit-limit;
		this.endRowNum = this.startRowNum + limit;
		this.pageCount = getPageCount();
		this.endPage = this.startPage+this.limit-1;
	}
	@Override
	public String toString() {
		return "PagingBean [startRowNum=" + startRowNum + ", endRowNum=" + endRowNum + ", total=" + total
				+ ", currentPage=" + currentPage + ", limit=" + limit + ", pageCount=" + pageCount + ", searchTarget="
				+ searchTarget + ", search=" + search + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
	
}
