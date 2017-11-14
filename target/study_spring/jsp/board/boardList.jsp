<%@page import="co.kr.ucs.spring.bean.BoardBean"%>
<%@page import="co.kr.ucs.spring.bean.PagingBean"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%> 
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	div.listWrapper{
		width: 450px;
   		margin: 0 auto;
	}
	div.paging{
		display: inline-block;
	}
	div.foot{
  	  margin: 0 auto;
  	  width: 500px;
  	  margin-top : 10px;
	}
	div.head button.btn{
		position: relative;
	    top: -9px;
	}
	div.pageNum{cursor:pointer;}
	div.curPage{background-color:#ddd;}
	div.foot button{background-color : white;border:1px solid black;}
	div.fieldWrapper{display: inline-block;}
	select[name=target]{width : 100%; height : 100%	;}
	table th{width : 80px;}
	table th:nth-child(2), table td:nth-child(2){
		width : 350px;
	}
	table td{height : 20px;}
	table td:nth-child(1){text-align: center;}
	.pageNo:not(.active){cursor: pointer;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<%=request.getContextPath() %>/resource/js/table.js"></script>
<script src="<%=request.getContextPath() %>/resource/js/paging.js"></script>
</head>
<body>
  
<%
PagingBean paging = (PagingBean)request.getAttribute("paging");
ArrayList<BoardBean> list = (ArrayList<BoardBean>)request.getAttribute("list");
if(paging.getSearchTarget()==null) {
	paging.setSearchTarget("");
	paging.setSearch("");	
}
%> 
 
	<div class="container" >
		<div class="listWrapper">
			<h4>게시판 목록</h4>
			<div class="head">
				<form method="get" action="${pageContext.request.contextPath}/board/list">
					<div class="fieldWrapper">
							<div class="field">
								<select name="searchTarget">
									<option value="title">제목 </option>
									<option value="REG_ID">작성자 </option>
								</select>
							</div>
							<div class="field"><input type="text" name="search"/></div>
					</div>
					<input type="submit" class="btn" value="검색">
					<button type="button" onclick="location.href='${pageContext.request.contextPath}/board/write' ">글쓰기</button>
				</form>
			</div>
			<table border="1px">
				<tr>
					<th>글번호</th>	<th>제목</th>		<th>작성자</th>	<th>작성일</th>
				</tr>
				
				<!-- TODO : table list  -->
				
				<c:forEach items="${list }" var="item">
					<tr>
						<td>	${item.getSeq() } 	</td>
						<td>	<a href="${pageContext.request.contextPath}/board/read?seq=${item.getSeq()}">${item.getTitle() }</a>	</td>
						<td>	${item.getReg_id() }	</td>
						<td>	${item.getReg_date() }	</td>
					</tr>
				</c:forEach>
			</table>
			<div class="foot">
				<div class="paging">
					<c:if test="${paging.getPageCount() > paging.getLimit() }">
					<button onclick="movePage('first','${ paging.getSearchTarget() }','${ paging.getSearch()}');" >&lt;&lt;	</button>	<button onclick="movePage(-1,'${ paging.getSearchTarget() }','${ paging.getSearch()}');">&lt;	</button>
					</c:if>	
					<div	class="paging">
						<% for(int i=paging.getStartPage(),j=1; i<= (paging.getEndPage()) && i<=(double)paging.getPageCount(); i++ ){ %>
								<%if( paging.getCurrentPage() == i ){ %>
										<span class="pageNo active" id="pageNo<%=i%>"> <%=i %> </span>
								<%}else if(i>(paging.getCurrentPage()+9)){%>
										<span class="pageNo" id="pageNo<%=i%>" onclick="linkPage(<%=i%>,'<%=paging.getSearchTarget() %>','<%=paging.getSearch()%>');" style="display : none; "> <%=i %> </span>
								<%} else { %>
										<span class="pageNo" id="pageNo<%=i%>" onclick="linkPage(<%=i%>,'<%=paging.getSearchTarget() %>','<%=paging.getSearch()%>');"> <%=i %> </span>
								<%} %>
						<%} %>
					</div>
					<c:if test="${ paging.getTotal() > paging.getLimit()}" >
					<button onclick="movePage(1,'${paging.getSearchTarget()  }','${ paging.getSearch()}');" >&gt;	</button>	<button onclick="movePage('last','${ paging.getSearchTarget() }','${ paging.getSearch()}');">&gt;&gt;	</button>
					</c:if>
					</div>
			</div>
		</div>
	</div>
<script>
var _paging = JSON.parse('${json_paging}');
var _list = JSON.parse('${json_list}');

console.log(_paging);
console.log(_list);
$(document).ready(function(){
	
	
	function setPaging(){
		
	}
	function setBoardList(){
		
	}
	
	
});
function linkPage(pageNo){
	if(_paging.searchTarget==null || _paging.searchTarget =='')_paging.searchTarget='title';
	var info = '?startPage='+_paging.startPage+'&currentPage='+setEmpty(pageNo)+'&searchTarget='+_paging.searchTarget+'&search='+setEmpty(_paging.search);
	location.href="${pageContext.request.contextPath}/board/list"+info;
}
function movePage(move){
	if(_paging.searchTarget==null || _paging.searchTarget =='')_paging.searchTarget='title';
	var info = '&searchTarget='+_paging.searchTarget+'&search='+setEmpty(_paging.search);
	if(move == 'first') {
		location.href="${pageContext.request.contextPath}/board/list?currentPage="+1+info;
	} else if (move == 'last') {
		var startPage = _paging.pageCount-_paging.pageCount % 10;
		if(_paging.pageCount % 10 == 0) startPage = _paging.pageCount-9;
		location.href="${pageContext.request.contextPath}/board/list?startPage="+startPage+"&currentPage="+(parseInt(_paging.pageCount))+info;
	} else {
		
		if(move == -1 && _paging.startPage <2) {
			return;
		} else if(move ==1 && _paging.endPage == (_paging.pageCount) ) {
			return;
		}
		move = parseInt(move) * 10 + parseInt(_paging.startPage);
		console.log(move);
		if(move < 1) {
			location.href="${pageContext.request.contextPath}/board/list?startPage="+(_paging.currentPage+10)+"&currentPage="+ (_paging.currentPage+10) +info;
			return;
		} else if(move >= (_paging.pageCount-1) ) {
			location.href="${pageContext.request.contextPath}/board/list?startPage="+(_paging.currentPage+10)+"&currentPage="+ (_paging.currentPage+10) +info;
			return;
		}
		location.href="${pageContext.request.contextPath}/board/list?startPage="+move+"&currentPage="+ move+info;
	}
}
function setEmpty(em) {
	if(em == null)
		return '';
	return em;
}

function settingScript(){
	_paging.rowCount = _paging.limit;
	_paging.curPage = _paging.currentPage;
	_paging.totalPage = Math.ceil(_paging.total / _paging.limit);
	_paging.pageCount = 10;
	console.log(_paging);
	$('table').html('');
	$('table').setTableField({
		field : [
			'글번호','제목','작성자','작성일'
		],
		original : [
			'seq','title','reg_id','reg_date'
		],
		row : 10
		
	});
	
	$('table').setTableData(_list);
	
	$('div.paging').html('');
	_paging.onChangeObj = function(){
		
		_paging.limit = _paging.rowCount;
		_paging.currentPage = _paging.curPage;
		
		featData();
	}
	$('div.paging').paging(_paging);
	
}
var featData = function() {
	
	//임시방편 중복으로 요청시 오동작으로 인해
	if(!this.isUse) {
		this.isUse = true;
	} else {
		console.log("사용중")
		return;
	}
	var method = this;
	_paging.onChangeObj = null; //리스너 무한루프로
	$.ajax({
	    url: "<%=request.getContextPath()%>/board/listAjax",
	    type:'GET',
	    data: _paging,
	    success:function(data){
	    	data = JSON.parse(data);
	    	_paging = data.paging;
	    	_list = data.list;
	    	
	    	console.log(_list)
	    	settingScript();
	    	method.isUse = false;
	    },
	    error:function(data){
	    	console.log(data)
	    	method.isUse = false;
	    }
	});
}
</script>	


</body>
</html>