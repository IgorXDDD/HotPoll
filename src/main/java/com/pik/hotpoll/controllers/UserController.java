package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.services.DefaultPollService;
import com.pik.hotpoll.services.DefaultUserService;
import com.pik.hotpoll.services.interfaces.PollService;
import com.pik.hotpoll.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;


@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(DefaultUserService userService, ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.userService = userService;

    }

    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @GetMapping("")
    public ResponseEntity<?> getUser(@RequestParam(value = "userID", required = false) String userID) {
        if( userID == null ){
            return ResponseEntity.ok(userService.findAll());
        }
        return ResponseEntity.ok(userService.find(userID));
    }

    @PostMapping(name = "",  consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createUser(@RequestBody User user) throws ConstraintsViolationException {
        User u = userService.create(user);
        return ResponseEntity.ok(u);
    }
}
