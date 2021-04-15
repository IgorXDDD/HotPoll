package com.pik.hotpoll.controllers;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/poll")
public class PollController {
    @GetMapping("")
    public String getPolls() {
        JSONObject jo = new JSONObject();
        jo.put("name","Cool poll");
        jo.put("description","Very fabulous test poll.");
        return jo.toString();
    }

    @PostMapping("")
    public String postPoll(@RequestParam(value = "name", defaultValue = "Nice poll") String name) {
        JSONObject jo = new JSONObject();
        jo.put("message","Added poll: name: " + name + " ,description: Neat!");
        return jo.toString();
    }
}
