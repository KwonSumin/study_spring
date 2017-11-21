<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	body.login form.loginWrapper{
		position : relative;
		top : calc(50% - 100px);
		left : calc(50% - 120px);
		display : inline-block;
		text-align: center;
	}
	body.login div.field{
		display : inline-block;
	    min-width: 90px;
	    border : 1px solid #ddd;
	    margin-left : -5px;
	    height : 30px;
	}
	body.login form.loginWrapper button{
		margin-top : 10px;
		display: inline-block;
		background-color : white;
		border : 1px solid black;
		padding : 5px 15px 5px 15px;
	}
	body.login div.container{
	  width: 100%;
  	  height: calc(100vh - 30px);
	}
</style>
<script src="../../resource/js/default.js"></script>
</head>
<body class="login">
	<div class="container">
		
		<c:if test="${ sessionScope.id == null}">
		
			<form id="loginForm" class="loginWrapper" method="post" action="<%=request.getContextPath() %>/sign/signin">
				<input type="hidden" name="process" value="signin"/>
				<h4>로그인</h4>
				<div>
					<div class="field">아이디*</div>	<div class="field">	<input id="login_id" type="text" name="user_id" />	</div>
				</div>
				
				<div>
					<div class="field">비밀번호</div>	<div class="field">	<input id="login_pw" type="password" name="user_pw" />	</div>
				</div>
				<button onclick="return loginValidate();" >로그인</button> <button type="button" onclick="location.href='<%=request.getContextPath()%>/jsp/login/signUp.jsp'; " >회원가입</button>
			</form>
		
		</c:if>
		
		<c:if test="${ sessionScope.id != null}">
			<p>${sessionScope.id }님 환영 합니다.</p>
			<a href="signProcess.jsp?process=logout">로그아웃</a>
		</c:if>
		
		
	</div>
	<div id="console"></div>
	<script>
		
		(function(){
			var message ='${data.message}';
			if(message != ''){
				alert(message);
				location.href= '${data.view}';
			}
		})();
		function loginValidate(){
			var form = document.getElementById('loginForm');
			var id = document.getElementById('login_id');
			var pw = document.getElementById('login_pw');
			
			var ID_MIN_LENGTH = 2;
			var ID_MAX_LENGTH = 20;
			var PW_MIN_LENGTH = 3;
			var PW_MAX_LENGTH = 20;
			
			if( checkLength(id.value,ID_MIN_LENGTH,ID_MAX_LENGTH,'아이디는') == false ){
				return false;
			}
			if( checkLength(pw.value,PW_MIN_LENGTH,PW_MAX_LENGTH,'패스워드는') == false ){
				return false;
			}
			//loginValidate 로직은 종료 입니다.
			return true;
			
			
			//내부 메소드
			function checkLength(value, min, max,error){
				var length = value.length;
				console.log(length);
				console.log(value)
				if(length == 0) {
					alert(error + ' 필수 항목 입니다.');
					return false;
				}
				if(length < min) {
					alert(error + ' '+min+'자이상 입력가능 합니다.');
					return false;
				}
				if(length > max) {
					alert(error + ' '+max+'자이하 입력가능 합니다.');
					return false;
				}
				return true;
			}
			
		}
		
		
		ajax({
			action : "<%=request.getContextPath()%>/TestCtrl/ajax",
			method : 'POST',
			data : "test=data",
			sucess : function(data){
				alert("성공");
				console.log(JSON.parse(data));
			}
		});
	</script>
	
</body>
</html>