/*
*   @author : sumin
*   @version : 1.0.0
*   @since : 2017. 11. 03
*   @param : action : ajax로 링크 처리 등 리스너 처리.
*   @description : ui(페이징 버튼과 넘버링)객체, list(리스트화면 랜더링)객체,
*       link 페이지 이동 관리 객체를 통합하여 관리하는 페이징 객체.
*       필수 설정사항
*       1. obj변수명과 info 변수명 매칭 시켜주면 됩니다.
*       2. ajax사용시 list객체를 생성하여 setter해주시면 됩니다.
*/


var PagingUtil = function(action,pagingObj){
    
    var info = {
        currentPage : 'currentPage',
        totalData : 'totalData',
        totalPage : 'totalPage',
        limitPage : 'limitPage',
        startPage : 'startPage',
        endPage : 'endPage'
    }
    var target = {
        table : $('table'),
        paging : $('.paging')
    }
    var obj = pagingObj;
    var ui = new PagingUi();
    var link = new PagingLink(info,obj);
    var list = new PagingList(list);
    var checkError = new ExceptionUtil();
    /*
    checkError.hasParameter(arguments);
    checkError.isObject(arguments);
    checkError.isNotEmpty(arguments);
    */
    //페이징 객체(obj)과 변수명 매칭
    
    
    
    this.setObj = function(param){obj = param}
    this.setUi = function(param){ui = param}
    this.setLink = function(param){link = param}
    this.setList = function(param){list = param}
    this.setInfo = function(name,setName){
        info[name] = setName;
    }
    //ajax로 list와 페이징 객체 리스트 전환
    this.setMoveAction = function(param){
        moveAction = param;   
    }
    
    
    //페이징 객체 시
    this.start = function(){
    	
		start();
    }
    
    function start(){
        render();
    	clickListener_pagingBtn();
        
    }
    function render(){
        var dataList = list.getList();
        ui.getUi(obj[info.startPage],obj[info.endPage]);
        target.table.html(dataList);
        
    }
    function clickListener_pagingBtn(){
        var btns = ui.getObj();
        var actions = link.linkAction;
        
        btns.first.unbind('click');
        btns.back.unbind('click');
        btns.forward.unbind('click');
        btns.last.unbind('click');
        
        btns.first.click(actions.move);
        btns.back.click(actions.move);
        btns.forward.click(actions.move);
        btns.last.click(actions.move);
        
    }
    //test
   
}