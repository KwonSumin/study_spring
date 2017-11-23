<%@page import="mvc.co.kr.ucs.bean.BoardBean"%>
<%@page import="mvc.co.kr.ucs.bean.PagingBean"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
div.listWrapper {
	width: 450px;
	margin: 0 auto;
}

div.paging {
	display: inline-block;
}

div.foot {
	margin: 0 auto;
	width: 500px;
	margin-top: 10px;
}

div.head button.btn {
	position: relative;
	top: -9px;
}

div.pageNum {
	cursor: pointer;
}

div.curPage {
	background-color: #ddd;
}

div.foot button {
	background-color: white;
	border: 1px solid black;
}

div.fieldWrapper {
	display: inline-block;
}

select[name=target] {
	width: 100%;
	height: 100%;
}

table th {
	width: 80px;
}

table th:nth-child(2), table td:nth-child(2) {
	width: 350px;
}

table td {
	height: 20px;
}

table td:nth-child(1) {
	text-align: center;
}

.pageNo:not (.active ){
	cursor: pointer;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/table.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/paging.js"></script>

<script src="<%=request.getContextPath()%>/resource/js/paging/ExceptionUtil.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/paging/link.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/paging/list.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/paging/paging.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/paging/ui.js"></script>


</head>
<body>

	<%
		PagingBean paging = (PagingBean) request.getAttribute("paging");
		ArrayList<BoardBean> list = (ArrayList<BoardBean>) request.getAttribute("list");
		if (paging.getSearchTarget() == null) {
			paging.setSearchTarget("");
			paging.setSearch("");
		}
	%>

	<div class="container">
		<div class="listWrapper">
			<h4>게시판 목록</h4>
			<div class="head">
				<%-- <form method="get"
					action="${pageContext.request.contextPath}/board/list"> --%>
					<div class="fieldWrapper">
						<div class="field">
							<select name="searchTarget">
								<option value="title">제목</option>
								<option value="REG_ID">작성자</option>
							</select>
						</div>
						<div class="field">
							<input type="text" name="search" />
						</div>
					</div>
					<input type="submit" class="btn" value="검색">
					<button type="button"
						onclick="location.href='${pageContext.request.contextPath}/mvc/board/write' ">글쓰기</button>
				<!-- </form> -->
			</div>
			<table border="1px" id="table">
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
				<!-- TODO : table list  -->
				
			</table>
			<div class="foot">
				<div class="paging">
				</div>
			</div>
		</div>
	</div>
<script>
	var _rootPath = '${pageContext.request.contextPath}';
	var _list = JSON.parse('${json_list}');
	var _paging = JSON.parse('${json_paging}');
	var paging = new PagingUtil();
	var listUtil = new PagingList();
	var link = new PagingLink();
	var ui = new PagingUi();
	listUtil.setList(_list);
	link.setObj(_paging);
	link.setListener('onMove',function(){
		console.log($(this).data('move'));
		fetchData()
	});
	ui.setListener('onRenderPage',function(link){
		link.click(function(){
			_paging.currentPage = parseInt(link.html());
			console.log($(this).html())
			console.log(_paging)
			fetchData()
		});
	})
	listUtil.setListener('onRender',function(data,fd){
		var result = data;
		if(fd=='title')
			result = $('<a href="#">').html(data);
		return result;
	})
	paging.setUi(ui);
	paging.setObj(_paging);
	paging.setLink(link);
	paging.setList(listUtil);
	_paging.startPage = 1;
	_paging.endPage = 10;
	_paging.totalPage = Math.ceil(_paging.totalData / _paging.limitPage);
	paging.start();
	
	function fetchData(){
		$.ajax({
	        url:_rootPath+"/mvc/board/list.ajax",
	        type:'POST',
	        data: _paging,
	        success:function(data){
	        	data = JSON.parse(data);
	        	_paging = data.paging;
	        	_list = data.list;
	        	init();
	        	paging.start();
	        },
	        error:function(data){
	        	console.log(data)
	        }
	    });
	}
	function init(){
		listUtil.setList(_list);
    	link.setObj(_paging);
	}
	
	
	
	
</script>


</body>
</html>