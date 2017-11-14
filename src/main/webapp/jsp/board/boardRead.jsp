<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	table td,table th{padding-left : 5px;}
	table tr:nth-child(2) td{width : 500px;}
	table th{text-align: left;padding-left:5px;height : 35px;}
	table tr:nth-child(3) td{height : 300px;vertical-align: top;}
	table th:nth-child(1){width :120px;}
	div.foot{text-align: center;margin-top: 15px;}
	form.detailWrapper{width : 620px;position :relative;top : calc(50% - 300px); left : calc(50% - 300px);}
</style>
</head>
<body>

	<div class="container">
		<form class="detailWrapper">
			<h4>게시판 상세</h4>
			<table border="1px">
				<tr>
					<th>작성자 </th>	<td>${bean.getReg_id() } </td>	<th>작성일 </th> <td>${bean.getReg_date() } </td>
				</tr>
				<tr>
					<th>제목 </th> 	<td colspan="3">${bean.getTitle() } </td>
				</tr>
				<tr>
					<th>내용 </th>	<td colspan="3"> ${bean.getContents() }</td>
				</tr>			
			</table>
			<div class="foot">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/board/list' ">취소</button>
			</div>
		</form>
	</div>
</body>
</html>