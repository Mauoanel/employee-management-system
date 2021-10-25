package com.xib.assessment.controller;


import com.xib.assessment.apirerror.ApiError;
import com.xib.assessment.apirerror.NotFoundError;
import com.xib.assessment.apirerror.ServiceSubExistError;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.services.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("manager/")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("{managerIdNumber}")
    public ResponseEntity findManager(@PathVariable("managerIdNumber") String managerIdNumber) throws NotFoundError {
        return new ResponseEntity<>(managerService.findManager(managerIdNumber), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity createManager(@RequestBody ManagerDto managerDto) {
        return new ResponseEntity<>(managerService.saveManager(managerDto), HttpStatus.OK);
    }

    @PutMapping("assign/{managerIdNumber}/team/{teamId}")
    public ResponseEntity assignManagerToTeam(@PathVariable("managerIdNumber") String managerIdNumber, @PathVariable("teamId") long teamId) throws NotFoundError {
        return new ResponseEntity<>(managerService.assignManagerToTeam(managerIdNumber, teamId), HttpStatus.OK);
    }

    @PutMapping("unassign/{managerIdNumber}/team/{teamId}")
    public ResponseEntity unassignManagerToTeam(@PathVariable("managerIdNumber") String managerIdNumber, @PathVariable("teamId") long teamId) throws ServiceSubExistError, NotFoundError {
            return new ResponseEntity<>(managerService.unassignManagerToTeam(managerIdNumber, teamId), HttpStatus.OK);
    }
}
