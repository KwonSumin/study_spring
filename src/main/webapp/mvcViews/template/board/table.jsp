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
		<c:forEach items="${template.getInfo() }" var="info">
			<th>${info.fieldName }</th>
		</c:forEach>
		</tr>
		
		<c:forEach items="${template.getList() }" varStatus="status" end="9">
		<tr class="data">
			<c:forEach items="${template.getInfo()}"  var="info">
			<td data-rowNum="${status.index }"  
			data-value="${info.dataName.replace('[','').replace(']','').replace(' ','') }"></td>
			</c:forEach>	
		</tr>
		</c:forEach>
		
	</table>
	<div id="paging"></div>
<script>
	//(function(){
		var list = JSON.parse('${template.getJson("list")}');
		var info = JSON.parse('${template.getJson("info")}');
		
		view();
		
	//})();
	function view(){
		var row = 0;
		console.log(list)
		console.log(info)
		for(var t of $('tr.data')) {
			for(i=0;i<=info.length-1;i++){
				//console.log(list[row][info[i].dataName.toUpperCase()]);
				var target = $(t).find('[data-value="'+info[i].dataName.toLowerCase()+'"]');
				console.log(target)
				target.html(list[row][info[i].dataName.toUpperCase()]);
			}
			row++;
		}
		//$('table').setTableData(list);
		
	}
	
	function init(data){
		console.log(data)
		console.log('init')
		list = data.list
		info = data.info
		view();
		
	}
	
	//(function(){
		console.log('${test.getJson("info")}')
		var paging = new Paging();
		var obj = JSON.parse('${template.getJson("paging")}');
		paging.obj.totalData = obj.totalData;
		paging.setTarget($('#paging'));
		paging.method.setPaging();
		paging.onAction = function(data){
			console.log(data);
		}
		paging.setAjax(function(){
			console.log(paging.getObj())
			$.ajax({
		        url:"/mvc/template/board/list.ajax",
		        type:'POST',
		        data: paging.getObj(),
		        success:function(data){
					data = JSON.parse(data);
					console.log(data)
					init(data);
		        },
		        error:function(data){
		        	console.log(data)
		        }
		    })
		});
		paging.start();
		console.log(paging.obj);
	//})();
</script>
</body>
</html>