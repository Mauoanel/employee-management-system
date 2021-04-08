package com.xib.assessment.apirerror;

public class BusinessRule {
    private String message;

    public BusinessRule(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BusinessRule{" +
                "message='" + message + '\'' +
                '}';
    }
}
