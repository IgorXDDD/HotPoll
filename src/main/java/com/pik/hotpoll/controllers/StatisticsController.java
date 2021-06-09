package com.pik.hotpoll.controllers;

import com.pik.hotpoll.services.DefaultStatisticsService;
import com.pik.hotpoll.services.interfaces.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {


    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(DefaultStatisticsService statisticsService){
        this.statisticsService = statisticsService;
    }


    @GetMapping("")
    public ResponseEntity<?> getPollsNum(@RequestParam(value = "tags", required = false) List<String> tags,
                                         @RequestParam(value = "name", required = false) String name) {
        if(tags != null){
            return ResponseEntity.ok(statisticsService.getCountByTags(tags));
        }
        if(name != null){
            return ResponseEntity.ok(statisticsService.getCountByName(name));
        }
        return ResponseEntity.ok( statisticsService.getPollsNum() );
    }

}
