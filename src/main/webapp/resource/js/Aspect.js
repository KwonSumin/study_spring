/*
*   @author : sumin
*   @version : 1.0.0
*   @since : 2017. 11. 24
*   @discription :  
*       자바에서 aop랑 비슷한형태의 객체.
*  
*   
*/


var Aspect = function(){
    var aop = this;
    var target = window;
    this.setTarget = function(param){target = param}
    
    /* var aop = new Aspect();
     * var logger = new Aspect_logger();
     * aop.before(print,function(method,...args){
            //기본값으로 설정한 로거객체 메소드실행
           logger.defaultBefore(method,...args);
           기본값 원본 메소드실행..if문으로 파라미터 검사 후 실행 등 가능.
           return 값을 넘겨주어야 afterReturn 으로 리턴값 확인 가능
           return method.method(...args) 
        });
     */
    this.before = function(targetFun,aopFun){
        before(targetFun,aopFun);
    }
    /* var aop = new Aspect(); 
     * aop.afterReturn(print,function(ret){
            console.log('return : ' + ret);
        })
    
     */
    this.afterReturn = function(targetFun,aopFun){
        afterReturn(targetFun,aopFun);
    }
    this.pointcut = function(pattern,name,aopFun){
        
        
        for(var method of findFunction(pattern)) {
            aop[name](method,aopFun);
        }
          
    }
    
    function findFunction(pattern){
        var pt = new RegExp('^'+pattern+'$');
        var keys = Object.keys(target);
        var result = [];
        for(i=0;i<=keys.length-1;i++) {
            if(pt.test(keys[i])) {
                target[keys[i]].aopName = keys[i];
                if(typeof target[keys[i]] == 'function')
                result.push(target[keys[i]]); 
            }
        }
        console.log('find : ',result)
        return result;
    }
    function afterReturn(run,returnFun){
        /*  
         *  
         */
        setAopName(run);
        var methodName = run.aopName||run.name;
        target[methodName] = function(){
            
            var ret = run(...arguments);
            var method = target[methodName];
            
            returnFun(ret,method);
            return ret;
        };
        target[methodName].aopName = methodName;
    }
    
    function before(run,beforeFun){
        /*  
         *  
         */
        setAopName(run);
        var methodName = run.aopName||run.name;
        
        
        target[methodName] = function(){
            
            var method = target[methodName];
            method.method = run;
            beforeFun.prototype.meethod = method;
            beforeFun.call(target,method,...arguments);
            return run.call(target,...arguments);
        };
        target[methodName].aopName = methodName;
        
    }
    
    function setAopName(method){
        var keys = Object.keys(target);
        for(var key of keys) {
            if( target[key] == method ){
                method.aopName = key;
            }
        }
    }
}


var Aspect_logger = function(){
    
    
    /*
     *  @discription :  
     *  [현재시간] 실행횟수. 메소드이름() args : 아규먼트
     */
    
    var isNeedArgs = true;
    var isNeedTrace = false;
    
    this.setIsNeedArgs = function(param){isNeedArgs = param}
    this.setIsNeedTrace = function(param){isNeedTrace = param}
    this.defaultBefore = function(method,...args){
        
        var info = writeInfo(method,...args);
        
        var idx=0;
        var log = getTime()+method.info.executeNum+'. '+method.aopName +'()';
        console.log(log);
        var text ='';
        if(isNeedArgs)
            console.log('args : ',info.args)
        if(isNeedTrace)
            console.log('trace : ',info.trace)
        
        
    }
    
    function writeInfo(method,...args){
        if(method.info == null) {
            method.info={};
            method.info.executeNum = 0;
            method.info.history = [];
        } 
        var info = method.info;
        var obj = {
            
            runTime : getTime(),
            args : [...args],
            trace : new Error().stack,
                    
        }
        info.history.push(obj)
        method.info.executeNum++;
        return obj;

    }
    function getTime(){
        var date = new Date();
        var hour = date.getHours();
        var min = date.getMinutes();
        var sec = date.getSeconds();
        return '['+hour+'시 '+min+'분 '+sec+'초] ';
    }
}