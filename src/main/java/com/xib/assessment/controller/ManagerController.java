package com.xib.assessment.controller;


import com.xib.assessment.apirerror.NotFoundError;
import com.xib.assessment.apirerror.ServiceSubExistError;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.services.interfaces.ManagerServiceInf;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("manager/")
public class ManagerController {

    private final ManagerServiceInf managerServiceInf;

    public ManagerController(ManagerServiceInf managerServiceInf) {
        this.managerServiceInf = managerServiceInf;
    }

    @GetMapping("{managerIdNumber}")
    public ResponseEntity findManager(@PathVariable("managerIdNumber") String managerIdNumber) throws NotFoundError {
        return new ResponseEntity<>(managerServiceInf.findManager(managerIdNumber), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity createManager(@RequestBody ManagerDto managerDto) {
        return new ResponseEntity<>(managerServiceInf.saveManager(managerDto), HttpStatus.OK);
    }

    @PutMapping("assign/{managerIdNumber}/team/{teamId}")
    public ResponseEntity assignManagerToTeam(@PathVariable("managerIdNumber") String managerIdNumber, @PathVariable("teamId") long teamId) throws NotFoundError {
        return new ResponseEntity<>(managerServiceInf.assignManagerToTeam(managerIdNumber, teamId), HttpStatus.OK);
    }

    @PutMapping("unassign/{managerIdNumber}/team/{teamId}")
    public ResponseEntity unassignManagerToTeam(@PathVariable("managerIdNumber") String managerIdNumber, @PathVariable("teamId") long teamId) throws ServiceSubExistError, NotFoundError {
            return new ResponseEntity<>(managerServiceInf.unassignManagerToTeam(managerIdNumber, teamId), HttpStatus.OK);
    }
}
