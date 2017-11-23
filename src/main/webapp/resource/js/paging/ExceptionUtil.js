/*
*   @author : sumin
*   @version : 1.0.3
*   @since : 2017. 11. 22
*/

var ExceptionUtil = function(){
    
    this.isObject = function(arg){
        for(var t of arg){
            if((typeof t) != 'object')
                throw new Error("오브젝트 형태만 가능합니다.");
        }
    }
    this.isNotNull = function(arg){
        for(var t of arg){
            if((typeof t) != 'undefiend')
                throw new Error("널값은 불가능 합니다.");
        }
    }
    this.isNotEmpty = function(arg){
       
        for(var t of arg){
            if((typeof t) != 'undefiend' && t == '') {
                throw new Error("값이 비어있습니다.");
            } else if(typeof t == 'object'){
                if(Object.keys(t).length == 0)
                    throw new Error("값이 비어있습니다.");
            }
        }
    }
    this.hasParameter = function(arg){
        if(arg.length ==0)
            throw new Error("파라미터 값을 확인해주세요.");
    }
}


