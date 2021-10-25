package com.xib.assessment.apirerror;


import lombok.Data;

@Data
public class ErrorMessage {
    private String message;
    private String rule;
    private String suggestion;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(String message, String rule, String suggestion) {
        this.message = message;
        this.rule = rule;
        this.suggestion = suggestion;
    }
}
