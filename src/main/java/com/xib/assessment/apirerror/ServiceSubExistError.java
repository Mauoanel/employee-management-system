package com.xib.assessment.apirerror;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceSubExistError extends Exception{
    private String message;
    private ErrorMessage errorMessage;
    private String errorType = this.getClass().getSimpleName();
    private HttpStatus status;

    public ServiceSubExistError(String message) {
        super(message);
        this.message = message;
        this.errorMessage = new ErrorMessage(message);
    }
}
