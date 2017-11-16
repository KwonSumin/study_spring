<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath() %>/resource/js/table.js"></script>
</head>
<body>
	<table>
		<tr>
		<c:forEach items="${board.fieldNames }" var="field">
			<th>${field }</th>
		</c:forEach>
		</tr>
		<c:forEach items="${board.list }" end="9">
		<tr>
			<c:forEach items="${board.dataNames} "  var="dataName">
			<td  data-field="${dataName }">더미값</td>
			</c:forEach>	
		</tr>
		</c:forEach>
	</table>
</body>
</html>