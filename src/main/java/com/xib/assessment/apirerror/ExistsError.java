package com.xib.assessment.apirerror;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExistsError extends RuntimeException{

    private String message;
    private ErrorMessage errorMessage;
    private String errorType = this.getClass().getSimpleName();

    public ExistsError(String message) {
        super(message);
        this.message = message;
        this.errorMessage = new ErrorMessage(message);
    }
}
