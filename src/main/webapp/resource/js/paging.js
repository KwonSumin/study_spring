/*
*   @author : sumin
*   @version : 1.0.0
*   @since : 2017. 10. 26
*   @param : data
*       keys : total,curPage,pageCount,rowCount,startPage,endPage
*	@description : ajax / formaction 선택해서 사용을 위하여 분리해서 만들었으나
*	 ajax로 사용시 약간의 문제점 있음. 객체 생성형태로 다시 만들예정
*	객체 변수이름이 다를경우도 생각필요 함.
*/

$.fn.paging = function(data){
	
    validParam(data);
    this.onChangeObj = data.onChangeObj == null ? function(){} : data.onChangeObj ;
    var totalPage = Math.ceil(data.total / data.rowCount);
    data.totalPage = totalPage;
    var obj = this;
    //예외상황 처리
    if(data.startPage == null)
        data.startPage = 1;
    if(data.endPage == null)
        data.endPage = data.pageCount;
    if(data.endPage > totalPage)
        data.endPage = totalPage;
    var div = $('<div>').css({
        display : 'inline-block',
        border : '1px solid black',
        marginLeft : '5px',
    });
    
    
    
    
    //data 메소드 추가
    data.move = function(m){
        data.curPage = data.startPage + (m*data.pageCount);
        data.startPage = data.startPage + (m*data.pageCount);
        data.endPage = data.startPage + data.pageCount-1;
    }
    data.first = function(){
        data.curPage = 1;
        data.startPage = 1;
        data.endPage = data.pageCount;
        
    }
    data.last = function(){
        data.curPage = totalPage;
        data.startPage = getStartNum();
        data.endPage = totalPage;
        
        
        function getStartNum(){
            var temp = data.totalPage / data.pageCount;
            temp = ( temp - parseInt(temp) ).toFixed(1) * 10;
            temp = (totalPage - temp );
            if(!data.totalPage % data.pageCount != 0){
            	temp -= data.pageCount-1;
            } else {
            	temp += 1;
            }
            
            return Math.floor(temp);
        }
    }
    
    var first = div.clone().html('<<');
    var back = div.clone().html('<');
    var pages = div.clone();
    var forward = div.clone().html('>');
    var last = div.clone().html('>>');
    
    
    if(totalPage > data.pageCount) {
        this.append(first);
        this.append(back);
    }
    
    
    
    this.append(pages);
    
    if(totalPage > data.pageCount) {
        this.append(forward);
        this.append(last);
    }
    
    first.on('click',function(){
        if(data.curPage == 1) return false;
        data.first();
        //paging();;
        console.log(data)
        obj.onChangeObj(data);
    })
    
    last.on('click',function(){
        if(data.curPage == data.totalPage) return false;
        data.last();
        //paging();;
        obj.onChangeObj(data);
    })
    
    back.on('click',function(){
        if(data.startPage == 1) return false;
        console.log('back');
        data.move(-1);
        //paging();;
        obj.onChangeObj(data);
    })
    forward.on('click',function(){
        if(data.endPage == data.totalPage) return false;
        console.log('forward');
        data.move(1);
        //paging();;
        obj.onChangeObj(data);
    })
    
    
    
    console.log(totalPage)
    this.doPaging = function() {
        //페이징 처리
        
        pages.html('');
        for(i=data.startPage; i<=data.endPage; i++) {

            if(num >= totalPage ) break;

            var num = i;
            var pageNum = $('<div>').clone().html("&nbsp;"+num+"&nbsp;")
                .css({display:'inline-block'
                     })
                .attr({
                    'data-page' : num,
                    class : 'pageNum'
                }).data('page',num);
            pages.append(pageNum);

            if(data.curPage == num)
                pageNum.attr('class','curPage');
            else{
                pageNum.removeClass('curPage');
                pageNum.on('click',function(){
	                if($(this).hasClass('curPage')) return false;
	                var page = $(this).data(page);
	                data.curPage = page.page;
	                pages.find('.curPage').attr('class','pageNum');
	                pages.find('[data-page="'+page.page+'"]').attr('class','curPage');
	                obj.onChangeObj(data);
	                return false;
	            })
            }

        }
    }
    
    this.doPaging();
    
    return this;
    
    //내부 함수들
    function help(){
        
    }
    
    function validParam(data) {
        
        var accept = ['object']
        var indispensable = [];
        
        try{
            if(accept.indexOf(typeof data) == -1 )
                throw new Error('잘못된 파라미터 입니다.');
            if(!hasIncludeItem(data,indispensable))
                throw new Error('필수항목 누락되었습니다.');
        } catch(e){
            help();
            throw e;
        }
        return false;
    }
    function hasIncludeItem(data,item){
        
        
        var keys = Object.keys(data);
        for(i=0; i<=item.length-1; i++) {
            var isFindItem = keys.indexOf(item[i]) != -1;
            if(!isFindItem) {
                return false;
            }
        }
        return true;
    }
}