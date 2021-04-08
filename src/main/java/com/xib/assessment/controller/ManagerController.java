package com.xib.assessment.controller;


import com.xib.assessment.apirerror.ApiError;
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
    public ResponseEntity findManager(@PathVariable("managerIdNumber") String managerIdNumber){
        try {
            return new ResponseEntity<>(managerService.findManager(managerIdNumber), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity createManager(@RequestBody ManagerDto managerDto){
        try {
            return new ResponseEntity<>( managerService.saveManager(managerDto), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("assign/{managerIdNumber}/team/{teamId}")
    public ResponseEntity assignManagerToTeam(@PathVariable("managerIdNumber") String managerIdNumber,@PathVariable("teamId") long teamId){
        try {
            return new ResponseEntity<>(managerService.assignManagerToTeam(managerIdNumber, teamId), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("unassign/{managerIdNumber}/team/{teamId}")
    public ResponseEntity unassignManagerToTeam(@PathVariable("managerIdNumber") String managerIdNumber,@PathVariable("teamId") long teamId){
        try {
            return new ResponseEntity<>(managerService.unassignManagerToTeam(managerIdNumber, teamId), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
