package com.xib.assessment.services;


import com.xib.assessment.apirerror.MandatoryFieldError;
import com.xib.assessment.apirerror.NotFoundError;
import com.xib.assessment.apirerror.ExistsError;
import com.xib.assessment.assembler.AppAssembler;
import com.xib.assessment.entity.Agent;
import com.xib.assessment.entity.Manager;
import com.xib.assessment.repository.AgentRepository;
import com.xib.assessment.apirerror.ApiError;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.repository.ManagerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final ManagerRepository managerRepository;

    public AgentService(AgentRepository agentRepository, ManagerRepository managerRepository) {
        this.agentRepository = agentRepository;
        this.managerRepository = managerRepository;
    }


    /**
     * Finds All Agents on the database
     * Applies pagination
     * @return a list of agents
     * @throws ApiError if no agents found.
     * @param pageNo : specifies the page number
     * @param pageSize : specifies the number of items(size) on the page
     */
    public List<Agent> findAllAgentsWithPaging(Integer pageNo, Integer pageSize) throws ApiError, NotFoundError {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id"));
        Page<Agent> agentsPaged = agentRepository.findAll(paging);

        List<Agent> agents;

        if (agentsPaged.isEmpty()) {
            throw new NotFoundError("No Agents found!");
        } else {
            agents = agentsPaged.getContent();
        }

        return agents;
    }
    /**
     * Finds All Agents on the database
     * @return a list of agents
     * @throws ApiError if no agents found.
     */
    public List<Agent> findAllAgents() throws ApiError, NotFoundError {

        List<Agent> agents = agentRepository.findAll();

        if (agents.isEmpty())
            throw new NotFoundError("No Agents found!");

        return agents;
    }

    /**
     * Finds the Agent
     *
     * @param id: agent pk: identification
     * @return :
     * @throws ApiError
     */
    public Agent findAgent(long id) throws NotFoundError {

        try {
            return agentRepository.findById(id)
                    .orElseThrow(() -> new NotFoundError("Agent not found for this id: " + id));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * Create a new Agent on the database
     * @param agentDto
     * @return saved agent
     * @throws ApiError: if agent exists or mandatory fields are empty
     */
    public Agent saveAgent(AgentDto agentDto) throws NotFoundError, MandatoryFieldError, ExistsError {

        Agent agent = AppAssembler.assembleAgent(agentDto);

        try {
            if(agent.getIdNumber() == null)
                throw new MandatoryFieldError("Agent ID Number is mandatory.");

            if(agentRepository.existsByIdNumber(agent.getIdNumber()))
                throw new ExistsError("Agent exists.");

            if(agentDto.getManagerId() == null)
                throw new MandatoryFieldError("Manager is mandatory.");

            if(!managerRepository.existsById(agentDto.getManagerId()))
                throw new ExistsError("Manager does not exist.");

            Manager manager = null;
            Optional<Manager> managerOptional = managerRepository.findById(agentDto.getManagerId());
            if(managerOptional.isPresent()){
                manager = managerOptional.get();
            } else {
                throw new NotFoundError("Manager not found.");
            }

            agent.setManager(manager);
            agent = agentRepository.save(agent);

        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return agent;
    }
}
