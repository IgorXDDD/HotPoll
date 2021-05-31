package com.pik.hotpoll.controllers;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Vote;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.services.DefaultPollService;
import com.pik.hotpoll.services.DefaultStatisticsService;
import com.pik.hotpoll.services.interfaces.PollService;
import com.pik.hotpoll.services.interfaces.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/vote")
public class VoteController {


    private final PollService pollService;
    private final StatisticsService statisticsService;
    @Autowired
    public VoteController(DefaultPollService pollService, DefaultStatisticsService statisticsService){
        this.pollService = pollService;
        this.statisticsService = statisticsService;
    }


    @PostMapping(name = "",  consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addVote(@RequestBody Vote vote, Principal principal) throws ConstraintsViolationException {
//        System.out.println(principal.getName());
        Poll poll = pollService.addVote(vote);
//        return ResponseEntity.ok(poll);
        if(statisticsService.userVoted(vote, principal.getName())){
            return ResponseEntity.ok(poll);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(name = "")
    public ResponseEntity<?> hasVoted(@RequestParam(value = "pollID" )String pollId, Principal principal) {
        try {
            if (statisticsService.hasUserVotedOnPoll(pollId, principal.getName()))
                return ResponseEntity.ok("YES");
            return ResponseEntity.ok("NO");
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return ResponseEntity.notFound().build();
    }

}
