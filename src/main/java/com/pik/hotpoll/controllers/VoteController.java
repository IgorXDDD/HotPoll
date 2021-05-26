package com.pik.hotpoll.controllers;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Vote;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.services.DefaultPollService;
import com.pik.hotpoll.services.interfaces.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/vote")
public class VoteController {


    private final PollService pollService;

    @Autowired
    public VoteController(DefaultPollService pollService){
        this.pollService = pollService;
    }


    @PostMapping(name = "",  consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addVote(@RequestBody Vote vote) throws ConstraintsViolationException {
        Poll poll = pollService.addVote(vote);
        return ResponseEntity.ok(poll);
    }

}
