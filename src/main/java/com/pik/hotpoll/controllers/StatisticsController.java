package com.pik.hotpoll.controllers;

import com.pik.hotpoll.services.DefaultPollService;
import com.pik.hotpoll.services.interfaces.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {


    private final PollService pollService;

    @Autowired
    public StatisticsController(DefaultPollService pollService){
        this.pollService = pollService;
    }


    @GetMapping("")
    public ResponseEntity<?> getPollsNum() {
        return ResponseEntity.ok( pollService.getPollsNum() );
    }

}
