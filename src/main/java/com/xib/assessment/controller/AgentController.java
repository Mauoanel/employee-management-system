package com.xib.assessment.controller;

import com.xib.assessment.apirerror.ApiError;
import com.xib.assessment.services.AgentService;
import com.xib.assessment.dto.AgentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("agent/")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("")
    public ResponseEntity findAllAgents(){
        try {
            return new ResponseEntity<>( agentService.findAllAgents(), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("all/withPaging")
    public ResponseEntity findAllAgentsWithPaging(@RequestParam(defaultValue = "0") Integer pageNo,
                                        @RequestParam(defaultValue = "10") Integer pageSize){
        try {
            return new ResponseEntity<>( agentService.findAllAgentsWithPaging(pageNo, pageSize), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity findAgent(@PathVariable("id") @NotNull long id){
        try {
            return new ResponseEntity<>( agentService.findAgent(id), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity createAgent(@RequestBody AgentDto agent ){
        try {
            return new ResponseEntity<>( agentService.saveAgent(agent), HttpStatus.OK);
        } catch (ApiError apiError) {
            return new ResponseEntity<>(apiError.getBusinessRuleMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
