/**
 * 
 */

/*
(function(){
			var message ='${data.message}';
			if(message != ''){
				alert(message);
				location.href= '${data.view}';
			}
		})();*/

//console 없을경우 ex) IE 하위버전
 if(window.console == undefined) console = {
		 log : function(){},
		 error : function(){}
 } 
 
 
function ajax( obj ) {
	try {
		
		var type = typeof obj;
		var httpRequest;
		var method;
		var action = obj.action;
		var data = obj.data;
		
		
		if(obj.method == null) method = 'GET'; else method = obj.method;
		
		
		//파라미터 검사
		if(type != 'object') throw new Error("잘못된 파라미터");
		
		//리퀘스트 생성
		if (window.XMLHttpRequest) { // 모질라, 사파리등 그외 브라우저, ...
		    httpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE 8 이상
		    httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		if(!httpRequest) {
			throw new Error("http 객체 생성 실패");
		}
		httpRequest.open(method,action,true);
		//httpRequest.overrideMineType('text/xml');
		httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		httpRequest.onreadystatechange = function(){
			if(httpRequest.readyState == 4 && httpRequest.status == 200) {
				console.log(httpRequest);
				var param;
				if(httpRequest.response) param = httpRequest.response;
				if(httpRequest.responseXML && param == null) param = httpRequest.responseXML;
				obj.sucess(param);
			}
		}
		console.log(action);
		console.log(data);
		httpRequest.send(data);
		
		
	} catch (e) {
		console.error(e);
	}
}