package com.xib.assessment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AgentDto {

    private String firstName;
    private String lastName;
    private String idNumber;
    private Long managerId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "AgentDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", managerId='" + managerId + '\'' +
                '}';
    }
}
