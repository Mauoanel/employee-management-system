package com.xib.assessment.services;


import com.xib.assessment.apirerror.ApiError;
import com.xib.assessment.assembler.AppAssembler;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.entity.Agent;
import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import com.xib.assessment.repository.ManagerRepository;
import com.xib.assessment.repository.TeamRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final TeamRepository teamRepository;

    public ManagerService(ManagerRepository managerRepository, TeamRepository teamRepository) {
        this.managerRepository = managerRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Create a new Manager on the database
     * @param managerDto
     * @return saved manager
     * @throws ApiError: if team exists or mandatory fields are empty
     */
    public Manager saveManager(ManagerDto managerDto) throws ApiError {

        Manager manager = AppAssembler.assembleManager(managerDto);

        try {
            if(managerRepository.existsByIdNumberIgnoreCase(managerDto.getIdNumber()))
                throw new ApiError("Manager Id Number exists.");

            manager = managerRepository.save(manager);

        }catch (Exception a){
            a.printStackTrace();
            throw new ApiError(a.getMessage());
        }
        return manager;
    }

    /**
     * Assigns a manager to a team.
     * @param managerIdNumber
     * @param teamId
     * @return
     * @throws ApiError
     */
    public Team assignManagerToTeam(String managerIdNumber, long teamId) throws ApiError{

        Manager manager;
        Team team;

        try {
            if(!managerRepository.existsByIdNumberIgnoreCase(managerIdNumber))
                throw new ApiError("Manager does not exists.");

            if(!teamRepository.existsById(teamId))
                throw new ApiError("Team does not exists.");

            team = teamRepository.getOne(teamId);

            if(team.getManagers() != null){
                for (Manager man :team.getManagers()) {
                    if(man.getIdNumber().equalsIgnoreCase(managerIdNumber))
                        throw new ApiError("Manager already exists. Please use remove or update manager function.");
                }
            }

            manager = managerRepository.findByIdNumberIgnoreCase(managerIdNumber);

            Set<Manager> managerSet = team.getManagers();
            managerSet.add(manager);

            team.setManagers(managerSet);
            team = teamRepository.save(team);

        }catch (Exception a){
            a.printStackTrace();
            throw new ApiError(a.getMessage());
        }
        return team;
    }


    /**
     * Unassigns a manager from a team
     * @param managerIdNumber
     * @param teamId
     * @return
     * @throws ApiError
     */
    public Team unassignManagerToTeam(String managerIdNumber, long teamId) throws ApiError{

        Manager manager;
        Team team;
        Manager foundManagerDo= null;
        boolean managerFound = false;

        try {
            if(!managerRepository.existsByIdNumberIgnoreCase(managerIdNumber))
                throw new ApiError("Manager does not exists.");

            if(!teamRepository.existsById(teamId))
                throw new ApiError("Team does not exists.");

            team = teamRepository.getOne(teamId);
            manager = managerRepository.findByIdNumberIgnoreCase(managerIdNumber);

            if(team.getManagers() == null || team.getManagers().size() ==0)
                throw new ApiError("Team does not have manager. Please assign manager.");


            if(team.getManagers() != null){
                for (Manager man :team.getManagers()) {
                    if(man.getIdNumber().equalsIgnoreCase(managerIdNumber)) {
                        managerFound = true;
                        foundManagerDo = man;
                    }
                }
            }

            if(!managerFound){
                throw new ApiError("Provided Manager ID does not match with assigned Manager IDs. " +
                        "Note: To remove a current manager from the Team, note that the ID Number you provide has to match with" +
                        "one of the saved Manager ID Numbers.");

            } else {
                // Manager Unassigned from team
                Set<Manager> managerSet = team.getManagers();
                if(managerSet.remove(foundManagerDo)){
                    team.setManagers(managerSet);
                    team = teamRepository.save(team);
                } else {
                    throw new ApiError("Manager could not be removed from team.");
                }
            }
        }catch (Exception a){
            a.printStackTrace();
            throw new ApiError(a.getMessage());
        }
        return team;
    }

    /**
     * Finds a manager in the database
     * @param managerIdNumber: Id Document Number
     * @return manager
     * @throws ApiError
     */
    public Manager findManager(@NotNull String managerIdNumber) throws ApiError{
        try {
            Manager manager = managerRepository.findByIdNumberIgnoreCase(managerIdNumber);

            if(manager == null)
                   throw new ApiError("Manager not found for this id number: " + managerIdNumber);

            return manager;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiError(e.getMessage());
        }
    }
}
