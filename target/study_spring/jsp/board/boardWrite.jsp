<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	form.addWrapper{
		width : 650px;
		position : relative;
		top : calc(50% - 300px);
		left : calc(50% - 320px);
	}
	div.foot{
		width : 200px;
		margin : 0 auto;
		text-align: center;
	}
	form.addWrapper table{
		width : 100%;
	}
	form.addWrapper table td{
		width : 100%;
	}
	form.addWrapper tr:nth-child(2) td{
		height : 350px;	
	}
	form.addWrapper table th{min-width : 100px;}
	form.addWrapper table td *{
		width : 100%;
		height : 100%;
	}
	table tr:nth-child(2) th{vertical-align: top;}
	table th{text-align: left;padding-left:5px;}
</style>
</head>
<body>
	<div class="container">
		<form class="addWrapper" method="POST" action="${pageContext.request.contextPath}/board/write.do">
			<h4>게시판 입력</h4>
			<input type="hidden" name="reg_id" value="${sessionScope.id }" />
			<table border="1px">
				<tr>
					<th>제목 </th>	<td><input id="title" name="title" type="text" name="title" /> </td>
				</tr>
				
				<tr>
					<th>내용 </th>	<td><textarea id="contents" name="contents"></textarea> </td>
				</tr>
			</table>
			<div class="foot" >
				<button type="submit" class="btn">저장</button>		<button type="button" onclick="location.href='${pageContext.request.contextPath}/board/list'  " class="btn">목록</button>
			</div>
		</form>
	</div>
</body>
</html>