package com.xib.assessment.services.interfaces;

import com.xib.assessment.apirerror.ApiError;
import com.xib.assessment.apirerror.ExistsError;
import com.xib.assessment.apirerror.NotFoundError;
import com.xib.assessment.apirerror.ServiceSubExistError;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.entity.Agent;
import com.xib.assessment.entity.Team;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface TeamServiceInf {

    /**
     * Finds All Teams on the database
     *
     * @return a list of teams
     * @throws ApiError if no team found.
     */
    List<Team> findAllTeams() throws ApiError, NotFoundError;

    /**
     * Assign an agent to an existing team.
     * @param teamDto team details
     * @param agentId agent id
     * @return agent and team associated to
     * @throws ApiError
     */
    Agent assignAgentToTeam(TeamDto teamDto, long agentId) throws ApiError, ServiceSubExistError, NotFoundError;

    /**
     * Finds a team by Id
     * @param id of the team
     * @return specified team
     * @throws ApiError
     */
    Team findTeam(@NotNull long id) throws NotFoundError;

    /**
     * Create a new Team on the database
     * @param teamDto team to be saved on the db
     * @return saved agent
     * @throws ApiError: if team exists or mandatory fields are empty
     */
    Team saveTeam(TeamDto teamDto) throws ExistsError;

    /**
     * Find team with no agents and managers
     * @return saved agent
     * @throws ApiError: if team exists or mandatory fields are empty
     */
    List<Team> findTeamsWithNoManagersAndAgents() throws ApiError;
}
