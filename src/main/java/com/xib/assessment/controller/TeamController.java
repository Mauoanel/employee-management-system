package com.xib.assessment.controller;


import com.xib.assessment.apirerror.ApiError;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.services.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("team/")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity findAllTeams(){
        try {
            return new ResponseEntity<>(teamService.findAllTeams(), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("empty/managers/agents")
    public ResponseEntity findTeamsWithNoManagersAndAgents(){
        try {
            return new ResponseEntity<>(teamService.findTeamsWithNoManagersAndAgents(), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity findTeam(@PathVariable("id") long id){
        try {
            return new ResponseEntity<>(teamService.findTeam(id), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}/agent")
    public ResponseEntity assignAgentToTeam(@PathVariable("id") long id, @RequestBody TeamDto teamDto){
        try {
            return new ResponseEntity<>(teamService.assignAgentToTeam(teamDto, id), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity createTeam(@RequestBody TeamDto teamDto ){
        try {
            return new ResponseEntity<>( teamService.saveTeam(teamDto), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
