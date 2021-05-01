package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.services.DefaultPollService;
import com.pik.hotpoll.services.interfaces.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;


@RestController
@RequestMapping("/poll")
public class PollController {


    private final PollService pollService;
    private final ObjectMapper objectMapper;

@Autowired
public PollController(DefaultPollService pollService, ObjectMapper objectMapper){
    this.objectMapper = objectMapper;
    this.pollService = pollService;

}

    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @GetMapping("")
    public ResponseEntity<?> getPoll( @RequestParam(value = "pollID", required = false) String pollID) {
        if( pollID == null ){
            return ResponseEntity.ok(pollService.findAll());
        }
        return ResponseEntity.ok(pollService.find(pollID));
    }

    @PostMapping(name = "",  consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createPerson(@RequestBody Poll poll) throws ConstraintsViolationException {
            Poll p = pollService.create(poll);
            return ResponseEntity.ok(p);
    }
}
