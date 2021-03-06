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




.spin {
  position: absolute;
  top: calc(50% - 35px);
  left: calc(50% - 35px);
  right: 0;
  bottom: 0;
  vertical-align: middle;
  height: 70px;
  width: 70px;
  border-radius: 50%;
  border:dashed 5px blue;
  -webkit-animation-name: spin;
  -webkit-animation-duration: 2.5s;
  -webkit-animation-iteration-count: infinite;
  -webkit-animation-timing-function: linear;
}

@-webkit-keyframes spin {
  from   {  -webkit-transform: rotate(0deg); }
  to   {  -webkit-transform: rotate(360deg); }
}


.overlay{
	margin : 0;padding:0;
	background-color: #ddd;
	opacity : 0.5;
	height : 100%;
	width : 100%;
	position : fixed;
	top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display:none;
}

.currentPage {
	background-color : green;
}

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
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/table.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/paging.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/Aspect.js"></script>
</head>
<body>


	<div class="overlay"></div>
	<div class="container">
	
		<div class="listWrapper">
			<h4>게시판 목록</h4>
			<div class="head">
				<%-- <form method="get"
					action="${pageContext.request.contextPath}/board/list"> --%>
					<div class="fieldWrapper">
						<div class="field">
							<select name="searchTarget" style="margin-bottom:2px;height : 25px;">
								<option value="title">제목</option>
								<option value="REG_ID">작성자</option>
							</select>
						</div>
						<div class="field">
							<input type="text" name="search" />
						</div>
					</div>
					<input type="submit" class="btn" value="검색" style="margin-bottom:2px;">
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
					<div class="pagingBody">
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<script>
	
	var _rootPath = '${pageContext.request.contextPath}';
	var Paging = function(list){
		//설정값
		var util = this;
		var target = $('div.pagingBody');
		var _pagingBody;
		var searchBtn = $('input[type="submit"]');
		var table = $('table');
		
		//설정값
		var url = {
			ajax : _rootPath + "/mvc/board/list.ajax",
			basic : _rootPath + "/mvc/board/list"
		};
		var ui = {
			first : "",
			back : "",
			forwward : "",
			last : "",
			pagingBody : "",
			defaultCss : {
				small : {
					width : '30px', height : '30px',
					border : '1px solid black', display : 'inline-block',
					textAlign : 'center'
				},
				pagingBody : {
					width : '300px', height : '30px', textAlign : 'center',
					border : '1px solid black', display : 'inline-block'
				},
				number : {
					width : '30px', display : 'inline-block',
					height : '100%'
				}
			}
		}
		this.list = list;
		this.obj = {
			currentPage : 1,
			startPage : 1,
			endPage : 10,
			totalPage : 100,
			searchTarget : '',
			search : '',
			
			limitPage : 10,
			limitRow : 10,
			startRowNum : 0,
			endRowNum : 0
		}
		var fields = ["seq","title","reg_id","reg_date"];
		this.setObj = function(param){
			this.obj = param;
			var obj = this.obj;
			this.obj.pageIdx = Math.floor( (obj.currentPage-1) / obj.limitPage );
			this.obj.totalPage = Math.ceil(obj.totalData / obj.limitRow);
			this.obj.startPage = obj.pageIdx * obj.limitPage + 1;
			this.obj.endPage = obj.startPage + obj.limitPage - 1;
			if(obj.endPage > obj.totalPage) obj.endPage = obj.totalPage;
			if(obj.totalData < obj.endRowNum) obj.endRowNum = obj.totalData-1;
			return obj;
		}
		this.start = function(){start()};
		
		
		
		//private functions
		function fetchData(url,sucFun){
			var loading = $('<div class="spin">');
			console.log('check : ',util.obj)
			$('body').append(loading);
			$('.overlay').show();
			$.ajax({
		        url:url,
		        type:'POST',
		        data: util.obj,
		        success:function(data){
		        	console.log(data);
		        	if(typeof data != 'object')
		        		data = JSON.parse(data);
		        	sucFun(data);
		        	$('.overlay').hide();
		        	loading.remove();
		        },
		        error:function(data){
		        	console.log(data);
		        	$('.overlay').hide();
		        	loading.remove();
		        }
		    });
		}
		
		function clickListener_search(){
			
			searchBtn.click(function(){
				search();
			});
			
			function search(){
				util.obj.currentPage = 1;
				var search = $('input[name="search"]').val();
				var searchTarget = $('select[name="searchTarget"]').val();
				console.log(searchTarget + ' : ' + search);
				util.obj.search = search;
				util.obj.searchTarget = searchTarget;
				fetchData(url.ajax,function(data){
					util.setObj(data.paging);
					util.list = data.list;
					start();
				});
			}
		}
		function renderUi(){
			//util.setObj(util.obj);
			target.html('');
			var moveDiv = getDiv(ui.defaultCss.small);
			var first = moveDiv.clone().html('<<');
			var back = moveDiv.clone().html('<');
			var pagingBody = getDiv(ui.defaultCss.pagingBody);
			var forward = moveDiv.clone().html('>');
			var last = moveDiv.clone().html('>>');
			clickListener_btn();
			
			
			//if(obj.totalPage > obj.pagingSize){
				target.append(first);
				target.append(back);
			//}
			target.append(pagingBody);
			//if(obj.totalPage > obj.endPage) {
				target.append(forward);
				target.append(last);
			//}
			_pagingBody = pagingBody;
			renderNumber();
			function clickListener_btn(){
				first.click(function(){
					movePage('first');
				});
				back.click(function(){
					movePage('back');
				});
				forward.click(function(){
					movePage('forward');
				});
				last.click(function(){
					movePage('last');
				});
				
			}
			function renderNumber(){
				pagingBody.html('');
				
				for(i=util.obj.startPage;i<=util.obj.endPage;i++){
					var num = getDiv(ui.defaultCss.number).html(i);
					if(i==util.obj.currentPage){
						num.attr('class','currentPage');
					}else{
						num.attr('class','pageNum');
						clickListener_link();
					}
					pagingBody.append(num);
				}
				function clickListener_link(){
					num.click(function(){
						util.obj.currentPage = $(this).html();
						fetchData(url.ajax,function(data){
							util.setObj(data.paging);
							util.list = data.list;
							start();
						});
					});
				}
			}
			
			
		}
		
		function movePage(move){
			var obj = util.obj;
			
			console.log(obj);
			var isNotFirst = obj.currentPage > 1;
			var isNotLast = obj.currentPage < getTotalPage();
			if(move == 'first') {
				if(!isNotFirst) return false;				
				obj.currentPage = 1;
				
			} else if(move == 'back') {
				if(!isNotFirst) return false;
				obj.currentPage -= obj.limitPage;
				if(obj.currentPage < 1)
					obj.currentPage = 1;
				
			} else if(move == 'forward') {
				if(!isNotLast) return false;
				obj.currentPage += obj.limitPage;
				if(obj.currentPage > getTotalPage())
					obj.currentPage = getTotalPage();
			} else if(move == 'last') {
				if(!isNotLast) return false;	
				obj.currentPage = getTotalPage();
			}
			obj.startRowNum = (obj.currentPage - 1 )* obj.limitRow;
			obj.endRowNum = obj.startRowNum + obj.limitRow-1;
			
			fetchData(url.ajax,function(data){
				util.setObj(data.paging);
				util.list = data.list;
				start();
			});
			return;
			
			function getTotalPage(){
				return Math.ceil(obj.totalData / obj.limitRow);
			}
		}
		
		function renderList(){
			console.log('list');
			table.find('tr[data-value="row"]').remove();
			for(i=1;i<=util.list.length;i++) {
				var searchQuery = '&search='+isEmpty(util.obj.search)+'&searchTarget='
					+isEmpty(util.obj.searchTarget) + '&currentPage='+util.obj.currentPage;
				var readURL = _rootPath+"/mvc/board/read?seq="+util.list[i-1]['seq']+searchQuery;
				
				var $tr = $('<tr data-value="row">').clone();
				for(var field of fields) {
					var data = util.list[i-1][field];
					var $td = $('<td data-value="'+field+'">');
					if(field=='title') {
						data = '<a href="'+readURL+'">'+data+'</a>';
					} else if(field=='reg_date') {
						data = setDateformat(data);
					}
					
					$td.html(data);
					$tr.append($td);	
				}
				table.append($tr);
			}
			function isEmpty(data){
				if(data==null) return '';
				return data;
			}
			function setDateformat(date) {
				var sub = (new Date().getTime() - 
						new Date(date).getTime() ) / 1000 /60 / 60;
				if(sub <= 1){
					return Math.floor(sub * 60) + '분 전';
				} else if(sub <=24) {
					return Math.floor(sub) + '시간 전';
				} else if(sub<=(24 * 30)){
					return Math.floor( sub / 24 ) + '일 전';
				} else if( Math.floor( sub / 24 / 365 ) < 1  ){
					return Math.floor( sub / 24 / 30 ) + '개월 전';
				} else {
					return Math.floor( sub / 24 / 365 ) + '년 전';
				}
				
			}
		}
		
		function getDiv(css){
			return $('<div>').css(css);
		}
		
		function start(){
			
			renderUi();
			renderList();
			
		}
		
		(function(){
			 
			clickListener_search();
		})();
	}
	
	var _paging = new Paging();
	$(document).ready(function(){
		
		var aop = new Aspect();
		var logger = new Aspect_logger();
		var loading = $('<div class="spin">');
		var query = '?currentPage=${currentPage}&search=${search}&searchTarget=${searchTarget}'
		$('body').append(loading);
		$('.overlay').show();
		console.log(_paging.obj);
		$.ajax({
	        url: _rootPath + "/mvc/board/list.ajax"+query,
	        type:'POST',
	        success:function(data){
	        	console.log(data.paging)
	        	_paging.setObj(data.paging);
	        	_paging.list = data.list;
	        	setAop();
				_paging.start();
				$('.overlay').hide();
	        	loading.remove();
	        },
	        error:function(data){
	        	console.log(data);
	        	$('.overlay').hide();
	        	loading.remove();
	        }
	    });
		
		function setAop(){
			aop.setTarget(_paging);
			aop.pointcut('.*','before',function(method,...args){
				logger.defaultBefore(method,...args);
			});
			aop.pointcut('.*','afterReturn',function(ret,...args){
				console.log('리턴값 : ', ret);
			});
		}
	});
	
	
	
</script>


</body>
</html>