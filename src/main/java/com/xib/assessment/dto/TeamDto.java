package com.xib.assessment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TeamDto {

    private long id;
    private String name;
    private boolean validateTeamName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValidateTeamName() {
        return validateTeamName;
    }

    public void setValidateTeamName(boolean validateTeamName) {
        this.validateTeamName = validateTeamName;
    }

    @Override
    public String toString() {
        return "TeamDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", validateTeamName=" + validateTeamName +
                '}';
    }
}
