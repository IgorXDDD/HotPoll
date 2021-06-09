package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.search.BasicPredicateBuilder;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.services.DefaultPollService;
import com.pik.hotpoll.services.interfaces.PollService;
import com.pik.hotpoll.services.interfaces.UserService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/poll")
public class PollController {


    private final PollService pollService;
    private final ObjectMapper objectMapper;
    private final UserService userService;

@Autowired
public PollController(DefaultPollService pollService, ObjectMapper objectMapper,
                      UserService userService){
    this.objectMapper = objectMapper;
    this.pollService = pollService;
    this.userService = userService;

}

    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @GetMapping("")
    public ResponseEntity<?> getPoll( @RequestParam(value = "pollID", required = false) String pollID, @RequestParam(value = "tags", required = false) List<String> tags,
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
        @RequestParam(value = "newest", required = false) Boolean newest, @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "username", required = false) String username ) {
        if(newest == null)
            newest = false;

        if(pollID != null){
            return ResponseEntity.ok(pollService.find(pollID));
        }

        if(name != null){
            return ResponseEntity.ok(pollService.findByName(name, page, size, newest));
        }
        if(tags != null){
            return ResponseEntity.ok(pollService.findByTags(tags, page, size, newest));
        }
        if(username != null){
            return ResponseEntity.ok(pollService.findByUsername(username, page, size, newest));
        }
        return ResponseEntity.ok(pollService.findAll(page, size, newest));

    }

    @PostMapping(name = "",  consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createPoll(@RequestBody Poll poll, Principal principal) throws ConstraintsViolationException {


        Poll p = pollService.create(poll, User.fromPrincipal(principal,userService));
        return ResponseEntity.ok(p);
    }

    @PutMapping(name = "",  consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, Principal principal) throws ConstraintsViolationException {
        Poll p = pollService.create(poll, User.fromPrincipal(principal,userService));
        return ResponseEntity.ok(p);
    }

    @PatchMapping("")
    public ResponseEntity<String> deletePoll( @RequestParam(value = "pollID") String pollID) {
        try{
            pollService.delete(pollID);
            return ResponseEntity.ok("ok");
        }catch (ConstraintsViolationException e){
            return new ResponseEntity<String>("no such poll", HttpStatus.BAD_REQUEST);
        }
    }


}
