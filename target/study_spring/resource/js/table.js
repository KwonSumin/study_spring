/*
*   @author : sumin
*   @version : 1.0.0
*   @since : 2017. 10. 25
*   @param : 필드포멧(fm)
*   fm : {
*       field * : 테이블에 표시할 이름 Array,
*       original * : 변수명칭 Array,
*       style : 적용할 스타일 json,
*              
*   }
*/

$.fn.setTableField = function(fm){
    if(this[0].tagName != 'TABLE') 
        throw new Error('테이블만 적용가능합니다');
    validParam(fm);
    
    var fields = fm.field;
    var names = fm.original;
    var table = this.attr('border','1px');
    var tr = $('<tr>');
    var th = $('<th>');
    var td = $('<td>').css({width : '100px',height : '25px'});
    
    
    
    
    for(j=0; j<=fm.row; j++) {
        var target = table.append(tr.clone());
        for(i=0; i<=fields.length-1; i++) {
            if(j==0) {
                var sub = th.data('field',names[i]).attr('data-field',names[i]).html(fields[i]).clone();
                target.append(sub);
            } else {
                
                var sub = td.data('value',names[i]).attr('data-value',names[i]).clone();
                sub.data('rowNum',j).attr('data-rowNum',j);
                target.append(sub);
            }

        }
    }
    console.log('완료')
     
    //내부 common 함수
    function help(){
        console.log(
'*   @author : sumin' +"\n"+
'*   @version : 1.0.0' +"\n"+
'*   @since : 2017. 10. 25' +"\n"+
'*   @param : 필드포멧(fm)' +"\n"+
'*   fm : {' +"\n"+
'*       field *: 테이블에 표시할 이름 Array,' +"\n"+
'*       original *: 변수명칭 Array,' +"\n"+
'*       style : 적용할 스타일 json' +"\n"+
'*   }'  
        )
    }
    function validParam(fm) {
        try{
            if(typeof fm != 'object')
                throw new Error('오브젝트 파라미터만 가능합니다.');
            if(fm.field == null)
                throw new Error('filed는 필수 항목입니다.');
            if(fm.original == null)
                throw new Error('original은 필수 항목입니다.')
        } catch(e){
            help();
            throw e;
        }
        
        return false;
    }
    
}


/*
*   @author : sumin
*   @version : 1.0.0
*   @since : 2017. 10. 26
*   @param : 필드형태(data) jsonObject
*       setField로 만들어진 양식에 데이터 값과 field이름이
        매칭된 값을 데이터 입력
*/
$.fn.setTableData = function(data){
    console.log(data);
    for(i=0; i<=data.length-1; i++) {
        var keys = Object.keys(data[i]);
        var values = Object.values(data[i]);
        for(j=0; j<=keys.length-1; j++) {

            var target = $('td[data-value="'+keys[j]+'"][data-rowNum="'+(i+1)+'"]');
            
            target.html(values[j]);

        }

    }


}

$.fn.removeTr = function(start,end) {
	
}

