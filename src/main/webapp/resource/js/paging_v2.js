/*
*   @author : sumin
*   @version : 1.0.0
*   @since : 2017. 11. 03
*   @param : 
*/

var Paging = function(){
    var util = this;
    this.obj = {
        currentPage : 1, //현재 페이징 숫자 *
        startPage : 1, //페이징 프린트할 시작수 *
        endPage : 10, //페이징 마지막 수 *
        pageSize : 10, //페이징 개수  *
        totalPage : -1, //전체 페이지 개수 *
        totalData : -1, //전체 데이터개수 *
        pageIdx : 1, //현재 페이징 인덱스
        rowSize : 10,
        startRow : 0, //데이터베이스 참조할 row
        endRow : 9, //데이터 베이스 참조 마지막 row
        search : '', //검색어
        searchTarget : '', //검색어 타겟
        setPaging : function(){
        	this.setRow();
    		this.totalPage = Math.ceil( (this.totalData) / (this.rowSize) );
    		this.endPage = this.startPage+this.pageSize-1;
    		if(this.totalPage < this.endPage) 
    			this.endPage = this.totalPage;//토탈페이지를 넘어가지 않도록 설정
        },
        setRow : function(){
        	this.startRow = (this.startPage-1) * this.rowSize;
        	this.endRow = this.endPage * this.rowSize-1;
        	if(this.totalData-1 < this.endRow) this.endRow = this.totalData-1;
        },
        getMaxPageIdx : function(){
            return Math.ceil(this.totalPage / this.pageSize)-1;
        },
        getEndPage : function(){
            var page = this.startPage + this.pageSize -1;
            if(page > this.totalPage) return this.totalPage;
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
        ajax();
        util.onLink(util.obj);
        util.onAction(util.obj);
    }
    function move(move){
        var obj = util.obj;
    	console.log(move);
        
        if(move=='first'){
            
            if(util.obj.currentPage == 1) return false;
            obj.currentPage =1;
            obj.startPage = 1;
            obj.endPage = obj.startPage + obj.pageSize -1;
            obj.pageIdx = 1;
            
            
        } else if(move=='last'){
            
            if(obj.currentPage == obj.totalPage) return false;
            obj.currentPage = obj.totalPage;
            var temp = obj.totalPage % obj.pageSize-1;
            if(temp == -1) {
                obj.startPage = obj.totalPage - obj.pageSize+1;
                obj.endPage = obj.totalPage;
                obj.pageIdx = obj.getMaxPageIdx(); 
            } else {
                obj.startPage = obj.totalPage - temp;
                obj.endPage = obj.totalPage;
                obj.pageIdx = obj.getMaxPageIdx();
            }
            
            
        } else {
            move = move*1;
            var movePage = obj.currentPage+move;
            var pageIdx = obj.pageIdx + move;
            if(pageIdx > obj.getMaxPageIdx()) return false;
            if( movePage < 1  && obj.currentPage==1) return false;else console.log(movePage);
            
            
            obj.pageIdx = pageIdx;
            console.log(obj)
            obj.startPage = obj.pageIdx * obj.pageSize +1;
            obj.endPage = obj.getEndPage();
            obj.currentPage = obj.startPage;
            obj.setRow();
        }
        
        ajax();
        obj.view();
        util.onMove(obj);
        util.onAction(obj);
    }
    
    
}