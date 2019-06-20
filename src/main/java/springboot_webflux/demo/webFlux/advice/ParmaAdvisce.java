package springboot_webflux.demo.webFlux.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import springboot_webflux.demo.webFlux.exception.StudetException;

//当前类为通知 其连接点为处理其方法
//验证失败了返回失败信息和400
@ControllerAdvice
public class ParmaAdvisce {
    @ExceptionHandler
    public ResponseEntity<String>  validateHandle(WebExchangeBindException ex){
        return new ResponseEntity<String>(exToStr(ex),HttpStatus.BAD_REQUEST);
    }

    private String exToStr(WebExchangeBindException ex) {
       return  ex.getFieldErrors().stream().map(error-> error.getField()+ ":"+error.getDefaultMessage()).reduce("",(s1,s2)->s1+"\n"+s2);
    }
    @ExceptionHandler
    public ResponseEntity<String>  validateHandle(StudetException ex){

        return new ResponseEntity<String>(ex.getMessage()+ex.getErrorFile()+ex.getErrorMessage(),HttpStatus.BAD_REQUEST);
    }
}
