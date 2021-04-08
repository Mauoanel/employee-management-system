package com.xib.assessment.apirerror;

/**
 * @author Lawrence Mauoane
 */
public class ApiError extends Exception{

    private String errorMessage;
    private BusinessRule businessRuleMessage;

    public BusinessRule getBusinessRuleMessage() {
        return businessRuleMessage;
    }

    public void setBusinessRuleMessage(BusinessRule businessRuleMessage) {
        this.businessRuleMessage = businessRuleMessage;
    }

    public ApiError(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.businessRuleMessage = new BusinessRule(errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
