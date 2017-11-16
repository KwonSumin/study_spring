<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
<%@include file="/mvcViews/template/common/header.jsp" %>
<script src="<%=request.getContextPath() %>/resource/js/table.js"></script>
<script src="<%=request.getContextPath() %>/resource/js/paging_v2.js"></script>
</head>
<body>
	<table>
		<tr>
		<c:forEach items="${board.fieldNames }" var="field">
			<th>${field }</th>
		</c:forEach>
		</tr>
		<c:forEach items="${board.list }" end="9">
		<tr class="data">
			<c:forEach items="${board.dataNames} "  var="dataName">
			<td  data-field="${dataName.replace('[','').replace(']','').replace(' ','') }"></td>
			</c:forEach>	
		</tr>
		</c:forEach>
	</table>
	<div id="paging"></div>
<script>
	//(function(){
		var list = JSON.parse('${board.json_list}');
		var dataNames = JSON.parse('${board.json_dataNames}');
		var row = 0;
		for(var t of $('tr.data')) {
			for(i=0;i<=dataNames.length-1;i++){
				var target = $(t).find('[data-field="'+dataNames[i]+'"]');
				target.html(list[row][dataNames[i].toUpperCase()]);
			}
			row++;
		}
		
	//})();
	
	//(function(){
		var paging = new Paging();
		var obj = JSON.parse('${paging}');
		paging.obj.totalData = obj.totalData;
		paging.setTarget($('#paging'));
		paging.obj.setPaging();
		paging.onAction = function(data){
			console.log(data);
		}
		paging.start();
		console.log(paging.obj);
	//})();
</script>
</body>
</html>