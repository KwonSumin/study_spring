/*
*   @author : sumin
*   @version : 1.0.0
*   @since : 2017. 11. 03
*   @param : 
*/

var Paging = function(){
    var util = this;
    this.ajax = function(){};
    this.obj = {
        currentPage : 1, //현재 페이징 숫자 *
        startPage : 1, //페이징 프린트할 시작수 *
        endPage : 10, //페이징 마지막 수 *
        pageSize : 10, //페이징 개수  *
        totalPage : -1, //전체 페이지 개수 *
        totalData : -1, //전체 데이터개수 *
        pageIdx : 0, //현재 페이징 인덱스
        rowSize : 10,
        startRow : 0, //데이터베이스 참조할 row
        endRow : 9, //데이터 베이스 참조 마지막 row
        search : '', //검색어
        searchTarget : '', //검색어 타겟
    }
    this.method = {
		setPaging : function(){
        	this.setRow();
    		util.obj.totalPage = Math.ceil( (util.obj.totalData) / (util.obj.rowSize) );
    		util.obj.endPage = util.obj.startPage+util.obj.pageSize-1;
    		if(util.obj.totalPage < util.obj.endPage) 
    			util.obj.endPage = util.obj.totalPage;//토탈페이지를 넘어가지 않도록 설정
        },
        setRow : function(){
        	util.obj.startRow = (util.obj.startPage-1) * util.obj.rowSize;
        	util.obj.endRow = util.obj.endPage * util.obj.rowSize-1;
        	if(util.obj.totalData-1 < util.obj.endRow) util.obj.endRow = util.obj.totalData-1;
        },
        getMaxPageIdx : function(){
            return Math.ceil(util.obj.totalPage / util.obj.pageSize)-1;
        },
        getEndPage : function(){
            var page = util.obj.startPage + util.obj.pageSize -1;
            if(page > util.obj.totalPage) return util.obj.totalPage;
            return page;
        },
        view : function(){
            view();
        }
    }
    var target;
    
    
    
    var ui = {
        first : div().css({
            border : '1px solid black'
        }).html('<<'),
        back : div().css({
            border : '1px solid black'
        }).html('<'),
        next : div().css({
            border : '1px solid black'
        }).html('>'),
        last : div().css({
            border : '1px solid black'
        }).html('>>'),
        wrapper : div().css({
            border : '1px solid black'
        }),
        pageNum : div().css({
            
        })
    }
    
    //listener
    this.getObj = function(){return util.obj;}
    this.onMove = function(){}
    this.onLink = function(){}
    this.onAction = function(){}
    var ajax = function(){}
    this.setAjax = function(ajaxFun){
        ajax = ajaxFun;
        util.ajax = ajaxFun;
    }
    this.setTarget = function(set){
        target = set;
        
    }
    this.start = function(){
    	view();
    }
    
    function div(){
        var div = $('<div>').css('display','inline-block');
        return div;
    }
    
    function view(){
        target.html('');
        ui.wrapper.html('');
        if(util.obj.totalPage > util.obj.pageSize){
            target.append(ui.first);
            target.append(ui.back);
            
            ui.back.click(function(){
                move(-1);
            });
            ui.next.click(function(){
                move(1);
            });
            ui.first.click(function(){
                move('first');
            });
            ui.last.click(function(){
                move('last');
            });
        }
        
        for(i=util.obj.startPage;i<=util.obj.endPage;i++){
            var num = ui.pageNum.clone().html(i);
            ui.wrapper.append("&nbsp;");
            ui.wrapper.append(num);
            ui.wrapper.append("&nbsp;");
            num.data('page',i);
            num.click(function(){
                util.obj.currentPage = $(this).data('page');
                linke();
            });
            
        }
        target.append(ui.wrapper);
        if(util.obj.totalPage > util.obj.pageSize){
            target.append(ui.next);
            target.append(ui.last);
        }
    }
    function linke(){
        console.log(util.obj.currentPage)
        util.ajax();
        util.onLink(util.obj);
        util.onAction(util.obj);
    }
    function move(move){
        var obj = util.obj;
        var method = util.method;
    	console.log(move);
        
        if(move=='first'){
            
            if(util.obj.currentPage == 1) return false;
            obj.currentPage =1;
            obj.startPage = 1;
            obj.endPage = obj.startPage + obj.pageSize -1;
            obj.pageIdx = 0;
            
            
        } else if(move=='last'){
            
            if(obj.currentPage == obj.totalPage) return false;
            obj.currentPage = obj.totalPage;
            var temp = obj.totalPage % obj.pageSize-1;
            if(temp == -1) {
                obj.startPage = obj.totalPage - obj.pageSize+1;
                obj.endPage = obj.totalPage;
                obj.pageIdx = method.getMaxPageIdx(); 
            } else {
                obj.startPage = obj.totalPage - temp;
                obj.endPage = obj.totalPage;
                obj.pageIdx = method.getMaxPageIdx();
            }
            
            
        } else {
            move = move*1;
            var movePage = obj.currentPage+move;
            var pageIdx = obj.pageIdx + move;
            if(pageIdx > method.getMaxPageIdx()) return false;
            if( movePage < 1  && obj.currentPage==1) return false;else console.log(movePage);
            
            
            obj.pageIdx = pageIdx;
            console.log(obj)
            obj.startPage = obj.pageIdx * obj.pageSize +1;
            obj.endPage = method.getEndPage();
            obj.currentPage = obj.startPage;
            method.setRow();
        }
        
        util.ajax();
        method.view();
        util.onMove(obj);
        util.onAction(obj);
    }
    
    
}