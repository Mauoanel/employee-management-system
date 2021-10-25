package com.xib.assessment.services;


import com.xib.assessment.apirerror.ApiError;
import com.xib.assessment.apirerror.ExistsError;
import com.xib.assessment.apirerror.NotFoundError;
import com.xib.assessment.apirerror.ServiceSubExistError;
import com.xib.assessment.assembler.AppAssembler;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.entity.Manager;
import com.xib.assessment.entity.Team;
import com.xib.assessment.repository.ManagerRepository;
import com.xib.assessment.repository.TeamRepository;
import com.xib.assessment.services.interfaces.ManagerServiceInf;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
public class ManagerService implements ManagerServiceInf {

    private final ManagerRepository managerRepository;
    private final TeamRepository teamRepository;

    public ManagerService(ManagerRepository managerRepository, TeamRepository teamRepository) {
        this.managerRepository = managerRepository;
        this.teamRepository = teamRepository;
    }

    @Override
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

    @Override
    public Team assignManagerToTeam(String managerIdNumber, long teamId) throws ApiError, NotFoundError {

        Manager manager;
        Team team;

        try {
            if(!managerRepository.existsByIdNumberIgnoreCase(managerIdNumber))
                throw new NotFoundError("Manager does not exists.");

            if(!teamRepository.existsById(teamId))
                throw new NotFoundError("Team does not exists.");

            team = teamRepository.getOne(teamId);

            if(team.getManagers() != null){
                for (Manager man :team.getManagers()) {
                    if(man.getIdNumber().equalsIgnoreCase(managerIdNumber))
                        throw new ExistsError("Manager already exists. Please use remove or update manager function.");
                }
            }

            manager = managerRepository.findByIdNumberIgnoreCase(managerIdNumber);

            Set<Manager> managerSet = team.getManagers();
            managerSet.add(manager);

            team.setManagers(managerSet);
            team = teamRepository.save(team);

        }catch (Exception a){
            a.printStackTrace();
            throw a;
        }
        return team;
    }


    @Override
    public Team unassignManagerToTeam(String managerIdNumber, long teamId) throws ApiError, ServiceSubExistError, NotFoundError {

        Manager manager;
        Team team;
        Manager foundManagerDo= null;
        boolean managerFound = false;

        try {
            if(!managerRepository.existsByIdNumberIgnoreCase(managerIdNumber))
                throw new NotFoundError("Manager does not exists.");

            if(!teamRepository.existsById(teamId))
                throw new NotFoundError("Team does not exists.");

            team = teamRepository.getOne(teamId);
            manager = managerRepository.findByIdNumberIgnoreCase(managerIdNumber);

            if(team.getManagers() == null || team.getManagers().size() ==0)
                throw new NotFoundError("Team does not have manager. Please assign manager.");

            if(team.getManagers() != null){
                for (Manager man :team.getManagers()) {
                    if(man.getIdNumber().equalsIgnoreCase(managerIdNumber)) {
                        managerFound = true;
                        foundManagerDo = man;
                    }
                }
            }

            if(!managerFound){
                throw new NotFoundError("Provided Manager ID does not match with assigned Manager IDs. ",
                        "Confirm ID numbers",
                        "Note: To remove a current manager from the Team, note that the ID Number you provide has to match with" +
                        "one of the saved Manager ID Numbers.");

            } else {
                // Manager Unassigned from team
                Set<Manager> managerSet = team.getManagers();
                if(managerSet.remove(foundManagerDo)){
                    team.setManagers(managerSet);
                    team = teamRepository.save(team);
                } else {
                    throw new ServiceSubExistError("Manager could not be removed from team.");
                }
            }
        }catch (Exception a){
            a.printStackTrace();
            throw a;
        }
        return team;
    }

    @Override
    public Manager findManager(@NotNull String managerIdNumber) throws ApiError, NotFoundError {
        try {
            Manager manager = managerRepository.findByIdNumberIgnoreCase(managerIdNumber);

            if(manager == null)
                   throw new NotFoundError("Manager not found for this id number: " + managerIdNumber);

            return manager;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
