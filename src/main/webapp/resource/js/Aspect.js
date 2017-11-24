/*
*   @author : sumin
*   @version : 1.0.0
*   @since : 2017. 11. 24
*   @discription :  
*       java AOP 
*   @needLib : jqueryk
*   한글테스트
*/


var Aspect = function(){
    var aop = this;
    var target = window;
    this.setTarget = function(param){target = param}
    this.before = function(targetFun,aopFun){
        
        before(targetFun,aopFun);
    }
    this.afterReturn = function(targetFun,aopFun){
        afterReturn(targetFun,aopFun);
    }
    this.pointcut = function(pattern,name,aopFun){
        for(var method of findFunction(pattern))
            aop[name](method,aopFun);
    }
    
    function findFunction(pattern){
        var pt = new RegExp('^'+pattern+'$');
        var keys = Object.keys(target);
        var result = [];
        for(i=0;i<=keys.length-1;i++) {
            if(pt.test(keys[i])) {
            	if(typeof target[keys[i]] =='function')
                result.push(target[keys[i]]);
            }
        }
        console.log(result);
        return result;
    }
    function afterReturn(run,returnFun){
        /*  
         *  
         */
        var methodName = run.aopName||run.name;
        target[methodName] = function setReturn(){
            var ret = run(...arguments);
            var method = target[methodName];
            returnFun(ret,method);
        };
        target[methodName].aopName = methodName;
    }
    
    function before(run,beforeFun){
        /*  
         *  
         */
        var methodName = run.aopName||run.name;
        var name = 'name test';
        target[methodName] = function setBefore(){
            var method = target[methodName];
            beforeFun(method,...arguments);
            return run(...arguments);
        };
        target[methodName].aopName = methodName;
    }
}


var Aspect_logger = function(){
    
    
    /*
     *  @discription :  
     *  [현재시간] 실행횟수. 메소드이름() args : 아규먼트
     */
    
    var isNeedArgs = true;
    var isNeedTrace = false;
    this.defaultBefore = function(method,...args){
        
        var info = writeInfo(method,...args)
        
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