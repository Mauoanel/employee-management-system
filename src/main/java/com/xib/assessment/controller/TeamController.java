package com.xib.assessment.controller;


import com.xib.assessment.apirerror.NotFoundError;
import com.xib.assessment.apirerror.ServiceSubExistError;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.services.interfaces.TeamServiceInf;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("team/")
public class TeamController {

    private final TeamServiceInf teamService;

    public TeamController(TeamServiceInf teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity findAllTeams() throws NotFoundError {
        return new ResponseEntity<>(teamService.findAllTeams(), HttpStatus.OK);
    }

    @GetMapping("empty/managers/agents")
    public ResponseEntity findTeamsWithNoManagersAndAgents() {
        return new ResponseEntity<>(teamService.findTeamsWithNoManagersAndAgents(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity findTeam(@PathVariable("id") long id) throws NotFoundError {
        return new ResponseEntity<>(teamService.findTeam(id), HttpStatus.OK);
    }

    @PutMapping("{id}/agent")
    public ResponseEntity assignAgentToTeam(@PathVariable("id") long id, @RequestBody TeamDto teamDto) throws ServiceSubExistError, NotFoundError {
        return new ResponseEntity<>(teamService.assignAgentToTeam(teamDto, id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity createTeam(@RequestBody TeamDto teamDto) {
        return new ResponseEntity<>(teamService.saveTeam(teamDto), HttpStatus.OK);
    }
}
