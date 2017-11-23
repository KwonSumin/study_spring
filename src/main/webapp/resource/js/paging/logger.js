/*
*   @author : sumin
*   @version : 1.0.0
*   @since : 2017. 10. 25
*/


// logLevel interface;
const LOG4_INFO = {
        info : 0,
        debug : 1,
        warn : 2,
        error : 3,
        none : 4
    }


var Log4 = function(levelParam){
    //default level info;

    

    this.logLevel = LOG4_INFO.info;    
    this.onLog = function(){};
    
    
    this.setLogLevel = function(level){
        this.logLevel = level;
    }
    this.getLogLevel = function(level){
        return this.logLevel;
    }
    this.info = function (str){
        
        if(LOG4_INFO.info >= this.logLevel) {
            //getStack();
            console.log(str);
            this.onLog();
        }
        
    }
    this.debug = function (str){
        if(LOG4_INFO.debug >= this.logLevel) {
            //getStack();
            console.warn(str);
            this.onLog();
        }
    }
    
    this.warn = function (str){
        if(LOG4_INFO.warn >= this.logLevel) {
            //getStack();
            console.warn(str);
            this.onLog();
        }
    }
    
    this.error = function (str){
        if(LOG4_INFO.error >= this.logLevel) {
            //getStack();
            console.error(str);
            this.onLog();
        }
    }
    
    
    function getStack(){
        var stack = new Error().stack;
        stack = stack.split('\n');
        console.log(stack[2]);
    }
    if(levelParam != null) this.setLogLevel (levelParam);
    
}

