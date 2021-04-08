package com.xib.assessment.services;


import com.xib.assessment.apirerror.ApiError;
import com.xib.assessment.assembler.AppAssembler;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.entity.Agent;
import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import com.xib.assessment.repository.AgentRepository;
import com.xib.assessment.repository.TeamRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamService {

    private final AgentRepository agentRepository;
    private final TeamRepository teamRepository;

    public TeamService(AgentRepository agentRepository, TeamRepository teamRepository) {
        this.agentRepository = agentRepository;
        this.teamRepository = teamRepository;
    }


    /**
     * Finds All Teams on the database
     *
     * @return a list of teams
     * @throws ApiError if no team found.
     */
    public List<Team> findAllTeams() throws ApiError {
        List<Team> teams = teamRepository.findAll();

        if (teams.size() == 0)
            throw new ApiError("No Teams found!");

        return teams;
    }

    /**
     * Assign an agent to an existing team.
     * @param teamDto team details
     * @param agentId agent id
     * @return agent and team associated to
     * @throws ApiError
     */
    public Agent assignAgentToTeam(TeamDto teamDto,long agentId) throws ApiError {

        Agent agent;
        Team team;

        try {
            if(!agentRepository.existsById(agentId))
                throw new ApiError("Agent does not exists.");

            if(!teamRepository.existsById(teamDto.getId()))
                throw new ApiError("Team does not exists.");

            agent = agentRepository.getOne(agentId);
            team = teamRepository.findById(teamDto.getId()).get();

            boolean agentManagerManagesOtherTeams = false;

            // this stops an agent from being assigned to a different manager
            if(agent.getManager() != null){
                // Compare managers
                if(team.getManagers() != null) {
                    Set<Manager> teamManagerSet = team.getManagers();

                    agentManagerManagesOtherTeams = teamManagerSet.contains(agent.getManager());

                    if(!agentManagerManagesOtherTeams)
                        throw new ApiError(
                                "An agent can only be assigned to a team that is managed by the same manager she/he reports to.");
                } else {
                    throw new ApiError(
                            "No Managers associated with team. An agent can only be assigned to a team that is managed by the same manager she/he reports to.");
                }
            }

            agent.setTeam(team);

            if(teamDto.isValidateTeamName()){
                if(!teamDto.getName().trim().equalsIgnoreCase(team.getName().trim()))
                    throw new ApiError("Provided Team name does not match the existing team name.");
            }
            agent = agentRepository.save(agent);


        }catch (Exception a){
            a.printStackTrace();
            throw new ApiError(a.getMessage());
        }
        return agent;
    }

    /**
     * Finds a team by Id
     * @param id of the team
     * @return specified team
     * @throws ApiError
     */
    public Team findTeam(@NotNull long id) throws ApiError {
        try {

            return teamRepository.findById(id)
                    .orElseThrow(() -> new ApiError("Team not found for id: " + id));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(e.getMessage());
        }
    }

    /**
     * Create a new Team on the database
     * @param teamDto team to be saved on the db
     * @return saved agent
     * @throws ApiError: if team exists or mandatory fields are empty
     */
    public Team saveTeam(TeamDto teamDto) throws ApiError {

        Team team = AppAssembler.assembleTeam(teamDto);

        try {
            if(teamRepository.existsByNameIgnoreCase(teamDto.getName()))
                throw new ApiError("Team Name exists.");

            team = teamRepository.save(team);

        }catch (Exception a){
            a.printStackTrace();
            throw new ApiError(a.getMessage());
        }
        return team;
    }


    /**
     * Find team with no agents and managers
     * @return saved agent
     * @throws ApiError: if team exists or mandatory fields are empty
     */
    public List<Team> findTeamsWithNoManagersAndAgents() throws ApiError {

        try {
            return teamRepository.findAllByAgentsNullAndManagersNull();
        }catch (Exception a){
            a.printStackTrace();
            throw new ApiError(a.getMessage());
        }
    }
}
