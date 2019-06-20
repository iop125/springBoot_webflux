package springboot_webflux.demo.webFlux.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudetException extends  RuntimeException{
    private String errorMessage;
    private String errorFile;

    public StudetException(String message, String errorMessage, String errorFile) {
        super(message);
        this.errorMessage = errorMessage;
        this.errorFile = errorFile;
    }
}
