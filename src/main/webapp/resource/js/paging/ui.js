/*
*   @author : sumin
*   @version : 1.0.3
*   @since : 2017. 11. 22
*/

var PagingUi = function(){
/*
 * [< <] first
 * [ < ] back
 * [ 1 2 3 4 5 6 7 8 9 10] body
 * [ > ] forward
 * [> >] last
 */
    var $target = $('.paging');
    var ui = {
        first : $('<div>').css(getCss()).html('<<'),
        back : $('<div>').css(getCss()).html('<'),
        body : $('<div>').css(getCss()),
        forward : $('<div>').css(getCss()).html('>'),
        last : $('<div>').css(getCss()).html('>>'),
        name : 'paging',
        link : $('<a href="javascript:void(0)">')
            .css('padding','6px 6px')
    }
    var listener = {
        onRenderPage : function(){}
    }
    this.setFirst = function(first) {
        ui.first = first;
    }
    this.setBack = function(back) {
        ui.back = back;
    }
    this.setBody = function(body) {
        ui.body = body;
    }
    this.setForward = function(forward) {
        ui.forward = forward;
    }
    this.setLast = function(last) {
        ui.last = last;
    }
    this.setName = function(name) {
        ui.name = name;
    }
    this.setCss = function(target,css) {
        if(ui[target] != null) {
            ui[target].css(css);
        }
    }
    this.getObj = function(){
        return ui;
    }
    this.moveFun = function(){}
    this.getUi = function(start,end){
        
        $target.append(ui.first);
        $target.append(ui.back);
        $target.append(ui.body);
        $target.append(ui.forward);
        $target.append(ui.last);
        ui.body.html('')
        for(i=start;i<=end;i++){
            
            var $link = ui.link.clone().html(i);
            
            ui.body.append($link);
            listener.onRenderPage($link);
        }
        return $target;
        
    }
    this.setListener = function(name,fun){
    	listener[name] = fun;
    }
    ui.first.data('move','first');
    ui.back.data('move','back');
    ui.last.data('move','last');
    ui.forward.data('move','forward');
    function getCss(){
        return {
            display : 'inline-block',
            border : '1px solid black',
            textAlign : 'center'
        };
    }
}


