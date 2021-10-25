package com.xib.assessment.controller;

import com.xib.assessment.apirerror.ExistsError;
import com.xib.assessment.apirerror.MandatoryFieldError;
import com.xib.assessment.apirerror.NotFoundError;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.services.interfaces.AgentServiceInf;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("agent/")
public class AgentController {

    private final AgentServiceInf agentService;

    public AgentController(AgentServiceInf agentService) {
        this.agentService = agentService;
    }

    @GetMapping("")
    public ResponseEntity findAllAgents() throws NotFoundError {
        return new ResponseEntity<>(agentService.findAllAgents(), HttpStatus.OK);
    }

    @GetMapping("all/withPaging")
    public ResponseEntity findAllAgentsWithPaging(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) throws NotFoundError {
        return new ResponseEntity<>(agentService.findAllAgentsWithPaging(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity findAgent(@PathVariable("id") @NotNull long id) throws NotFoundError {
        return new ResponseEntity<>(agentService.findAgent(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity createAgent(@RequestBody AgentDto agent) throws ExistsError, NotFoundError, MandatoryFieldError {
        return new ResponseEntity<>(agentService.saveAgent(agent), HttpStatus.OK);
    }
}
