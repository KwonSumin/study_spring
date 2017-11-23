/*
*   @author : sumin
*   @version : 1.0.3
*   @since : 2017. 11. 22
*/


var PagingList = function(listParam){
    var info = {
        limitRow : 10,
        title : [
            "No","제목","작성자","등록일"
        ],
        field : [
            'seq','title','reg_id','reg_date'
        ],
        name : 'board',
        list : [
            {seq:4,title:'제목3241',reg_id:'ksm',reg_date:'2017-11-22'},
            {seq:3,title:'제234목1',reg_id:'test',reg_date:'2017-11-21'},
            {seq:2,title:'제목3324',reg_id:'asdf',reg_date:'2017-11-20'},
            {seq:1,title:'제목2341',reg_id:'ksm',reg_date:'2017-11-20'}
        ]
    }
    var listener = {
        onRender : function(){}
    }
    
    if(typeof listParam =='Object') info.list = listParam;
    
    this.setOnRender = function(param){listener.onRender = param}
    this.setTarget = function(target){
        info.target = target;
    }
    this.setLimitRow = function(limitRow){
        info.limitRow = limitRow;
    }
    this.setLimitRow = function(title){
        info.title = title;
    }
    this.setLimitRow = function(field){
        info.field = field;
    }
    this.setLimitRow = function(name){
        info.name = name;
    }
    this.setList = function(list){
        info.list = list;
        console.log('setlist')
        console.log(info.list);
    }
    this.setListener = function(name,fun){
        listener[name] = fun;
    }
    this.getList = function(){
        var $table = $('<table>').clone();
        var $tr = $('<tr>').clone();
        for(var t of info.title){
        	var $th = $('<th>').clone().html(t);
        	$tr.append($th);
        	$table.append($tr);
        }
        for(var obj of info.list) {
            var $tr = $('<tr>').clone();
            
            for(var fd of info.field){
                var data = obj[fd];
                data = listener.onRender(data,fd);
                var $td = $('<td>').clone().html(data);
                $tr.append($td);
            }
            $table.append($tr);
        }
        console.debug(info.list)
        return $table.html();
    }
   
    
    function start(){
    
    }
    
}