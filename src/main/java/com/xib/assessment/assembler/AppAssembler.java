package com.xib.assessment.assembler;

import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.entity.Agent;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.entity.*;

public class AppAssembler {

    public static Agent assembleAgent(AgentDto agentDto) {

        Agent agent = new Agent();
        agent.setFirstName(agentDto.getFirstName());
        agent.setLastName(agentDto.getLastName());
        agent.setIdNumber(agentDto.getIdNumber());

        return agent;
    }

    public static Team assembleTeam(TeamDto teamDto) {

        Team team = new Team();
        team.setName(teamDto.getName());
        return team;
    }

    public static Manager assembleManager(ManagerDto managerDto) {

        Manager manager = new Manager();
        manager.setFirstName(managerDto.getFirstName());
        manager.setLastName(managerDto.getLastName());
        manager.setIdNumber(managerDto.getIdNumber());
        return manager;
    }
}
