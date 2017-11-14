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
	body.join table th{
		text-align: left;
	}
	body.join form.joinWrapper {
		position : relative;
		top : calc(50% - 135px);
		left : calc(50% - 135px);
	}
	body.join tfoot td{
		text-align: center;
	}
	form.joinWrapper{width : 400px;}
	form.joinWrapper div.footer button{background-color:white;border:1px solid black;padding:5px 15px 5px 15px;}
	form.joinWrapper div.footer{text-align: center;margin-top : 10px;}
	table td{width : 210px; padding-left: 10px;}
	table th{font-weight: normal;padding-left: 10px;}
</style>
<title>Insert title here</title>
<script src="../../resource/js/default.js"></script>
</head>
	<body class="join">
		<div class="container">
			<form class="joinWrapper" method="post" action="<%=request.getContextPath() %>/sign/signup">
				<h3>회원가입</h3>
				<input type="hidden" name="process" value="siginup" />
				<table border="1px">
					<tr>
						<th>아이디*	</th><td>	<input id="CM_ID" type="text" name="user_id"/>	</td>
					</tr>
					
					<tr>
						<th>비밀번호	</th><td>	<input id="CM_PW" type="password" name="user_pw"/>	</td>
					</tr>
					
					<tr>
						<th>비밀번호 확인</th><td>	<input id="CM_check" type="password" name="check"/>	</td>
					</tr>
					
					<tr>
						<th>이름		</th><td>	<input id="CM_NM" type="text" name="user_nm"/>	</td>
					</tr>
					
					<tr>
						<th>이메일		</th><td>	<input id="CM_email" type="text" name="email"/>	</td>
					</tr>
				</table>
				<div class="footer"><button onclick="return signUpValidate();">가입</button>		<button type="button" onclick="location.href='/jsp/login/login.jsp' ">취소</button></div>
			</form>
		</div>
		
		<script>
			
			function signUpValidate(){
				var id = document.getElementById('CM_ID');
				var password = document.getElementById('CM_PW');
				var check_pw = document.getElementById('CM_check');
				var name = document.getElementById('CM_NM');
				var email =document.getElementById('CM_email');
				
				if( checkLength(id, 2, 20, '아이디는') == false )
					return false;
				
				
				if( checkLength(password, 5, 20, '패스워드는') == false )
					return false;
				
				if( checkPassword() == false ){
					return false;
				}
				
				
				if( checkLength(name, 2, 20, '이름은') == false )
					return false;
				
				if( checkLength(email, 2, 20, '메일은') == false )
					return false;
				
				if( isEmail(email.value) == false ) {
					return false;
				}
				
				function isEmail(email) {
					var pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
					if(! pattern.test(email) ) {
						alert(email);
						alert("잘못된 이메일 형식 입니다.");
						return false;
					}
					return true;
				}
				
				
				function checkPassword() {
					if( ! (password.value == check_pw.value) ) {
						alert('비밀번호가 일치하지 않습니다.');
						check_pw.focus();
						return false;
					}
					return true;
				}
				
				function checkLength(target, min, max,error){
					console.log(target.value);
					var length = target.value.length;
					console.log(length);
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
					
				return true;
			}
			
		</script>
		
	</body>
</html>