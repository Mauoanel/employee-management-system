package com.xib.assessment.services.interfaces;

import com.xib.assessment.apirerror.ApiError;
import com.xib.assessment.apirerror.NotFoundError;
import com.xib.assessment.apirerror.ServiceSubExistError;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;

import javax.validation.constraints.NotNull;

public interface ManagerServiceInf {
    /**
     * Create a new Manager on the database
     * @param managerDto
     * @return saved manager
     * @throws ApiError: if team exists or mandatory fields are empty
     */
    Manager saveManager(ManagerDto managerDto) throws ApiError;


    /**
     * Assigns a manager to a team.
     * @param managerIdNumber
     * @param teamId
     * @return
     * @throws ApiError
     */
    Team assignManagerToTeam(String managerIdNumber, long teamId) throws ApiError, NotFoundError;

    /**
     * Unassigns a manager from a team
     * @param managerIdNumber
     * @param teamId
     * @return
     * @throws ApiError
     */
    Team unassignManagerToTeam(String managerIdNumber, long teamId) throws ApiError, ServiceSubExistError, NotFoundError;

    /**
     * Finds a manager in the database
     * @param managerIdNumber: Id Document Number
     * @return manager
     * @throws ApiError
     */
    Manager findManager(@NotNull String managerIdNumber) throws ApiError, NotFoundError;
}
