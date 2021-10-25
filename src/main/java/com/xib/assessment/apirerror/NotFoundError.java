package com.xib.assessment.apirerror;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundError extends Exception{
    private String message;
    private ErrorMessage errorMessage;
    private String errorType = this.getClass().getSimpleName();
    private HttpStatus status;

    public NotFoundError(String message) {
        super(message);
        this.message = message;
        this.errorMessage = new ErrorMessage(message);
    }

    public NotFoundError(String message, String rule, String suggestion) {
        super(message);
        this.message = message;
        this.errorMessage = new ErrorMessage(message, rule, suggestion);
    }
}
