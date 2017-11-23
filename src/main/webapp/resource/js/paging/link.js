 /*
*   @author : sumin
*   @version : 1.0.3
*   @since : 2017. 11. 22
*/


var PagingLink = function(info,setObj){
    
    /* @param
    var info = {
        currentPage : 'currentPage',
        totalData : 'totalData',
        totalPage : 'totalPage',
        limitPage : 'limitPage',
        startPage : 'startPage',
        endPage : 'endPage'
    }
    */
	
	if(info==null)
		info = {
		        currentPage : 'currentPage',
		        totalData : 'totalData',
		        totalPage : 'totalPage',
		        limitPage : 'limitPage',
		        startPage : 'startPage',
		        endPage : 'endPage'
		    }
    var obj = setObj;
	var  listener = {
		onMove : function(){}
	}
    this.setCurrentPage = function(param){
        info.currentPage = param;
    }
    this.setTotalData = function(param){
        info.totalData = param;
    }
    this.setTotalPage = function(param){
        info.totalPage = param;
    }
    this.setLimitPage = function(param){
        info.limitPage = param;
    }
    this.setObj = function(param){obj = param}
    
    this.linkAction = {
        move : move
    }
    this.setListener = function(name,fun){
    	listener[name] = fun;
    }
    function move(){
        var move = $(this).data('move');
        if(move == 'first') {
            if(obj[info.currentPage] <= 1) return false;
            obj[info.currentPage] = 1;
            obj[info.startPage] = 1;
            obj[info.endPage] = obj[info.startPage] + obj[info.limitPage] -1;
            
        } else if(move == 'back') {
            if(obj[info.currentPage] <= 1) return false;
            obj[info.currentPage] -= obj[info.limitPage];
            if(obj[info.currentPage] <= 1)obj[info.currentPage]=1;
            obj[info.startPage] = 
                Math.floor(obj[info.currentPage] / obj[info.limitPage]) *
                obj[info.limitPage] + 1
            obj[info.endPage] = obj[info.startPage] + obj[info.limitPage];
            
        } else if(move == 'forward') {
            if(obj[info.currentPage] >= obj[info.totalPage]) return false;
            obj[info.currentPage] += obj[info.limitPage];
            if(obj[info.currentPage] >= obj[info.totalPage])
                obj[info.currentPage] = obj[info.totalPage];
            
             obj[info.startPage] = 
                Math.floor(obj[info.currentPage] / obj[info.limitPage]) *
                obj[info.limitPage] + 1
             obj[info.endPage] = obj[info.startPage] + obj[info.limitPage] -1;
            if(obj[info.endPage] > obj[info.totalPage])
                obj[info.endPage] = obj[info.totalPage];
        } else if(move == 'last') {
            if(obj[info.currentPage] >= obj[info.totalPage]) return false;
            obj[info.currentPage] = obj[info.totalPage];
            
            
             obj[info.startPage] = 
                Math.floor(obj[info.currentPage] / obj[info.limitPage]) *
                obj[info.limitPage] + 1
             obj[info.endPage] = obj[info.totalPage];
        
        }
        listener.onMove();
        console.info(move)
        console.log(obj);
    }
}