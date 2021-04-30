package com.pik.hotpoll.controllers;

import com.google.inject.internal.util.Lists;
import com.pik.hotpoll.controllers.mappers.PollMapper;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.PollDTO;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.services.DefaultPollService;
import com.pik.hotpoll.services.PollService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/poll/{pollID}")
public class PollController {

    private final PollService pollService;
    private PollMapper pollMapper;

    @Autowired
    public PollController(DefaultPollService pollService){
        this.pollService = pollService;
    }

    @GetMapping
    public ResponseEntity<?> getPoll(@Validated @PathVariable(required = false) String pollID) {
        if( pollID == null ){
            return ResponseEntity.ok(pollService.findAll());
        }
        return ResponseEntity.ok(pollService.find(pollID));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createPerson(@RequestBody Poll poll) {
        try {
            Poll p = pollService.create(poll);
            return ResponseEntity.ok(p);
        }catch (ConstraintsViolationException ignored){
            return ResponseEntity.ok(poll); //todo lepiej
        }

    }

}
