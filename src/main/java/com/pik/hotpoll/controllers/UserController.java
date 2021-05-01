package com.pik.hotpoll.controllers;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("")
    public String getUser(@RequestParam(value = "name", defaultValue = "Adam") String name) {
        JSONObject jo = new JSONObject();
        jo.put("name",name);
        jo.put("surname","Kowalski");
        return jo.toString();
    }

    @PostMapping("")
    public String postUser(@RequestParam(value = "name", defaultValue = "Adam") String name) {
        JSONObject jo = new JSONObject();
        jo.put("message","Added user: name: " + name + " ,surname: Kowalski");
        return jo.toString();
    }
}
