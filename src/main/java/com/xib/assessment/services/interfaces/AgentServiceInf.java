package com.xib.assessment.services.interfaces;

import com.xib.assessment.apirerror.ApiError;
import com.xib.assessment.apirerror.ExistsError;
import com.xib.assessment.apirerror.MandatoryFieldError;
import com.xib.assessment.apirerror.NotFoundError;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.entity.Agent;

import java.util.List;

public interface AgentServiceInf {

    /**
     * Finds All Agents on the database
     * Applies pagination
     * @return a list of agents
     * @throws ApiError if no agents found.
     * @param pageNo : specifies the page number
     * @param pageSize : specifies the number of items(size) on the page
     */
    List<Agent> findAllAgentsWithPaging(Integer pageNo, Integer pageSize) throws ApiError, NotFoundError;

    /**
     * Finds All Agents on the database
     * @return a list of agents
     * @throws ApiError if no agents found.
     */
    List<Agent> findAllAgents() throws ApiError, NotFoundError;

    /**
     * Finds the Agent
     *
     * @param id: agent pk: identification
     * @return :
     * @throws ApiError
     */
    Agent findAgent(long id) throws NotFoundError;


    /**
     * Create a new Agent on the database
     * @param agentDto
     * @return saved agent
     * @throws ApiError: if agent exists or mandatory fields are empty
     */
    Agent saveAgent(AgentDto agentDto) throws NotFoundError, MandatoryFieldError, ExistsError;
}
