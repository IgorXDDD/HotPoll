package com.pik.hotpoll.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("")
    public String testString(@RequestParam(value = "text", defaultValue = "test") String text) {
        if(text.equals("OK")){
            System.out.println("Correct test param!");
        }else {
            System.out.println("Incorrect test param!: " + text);
        }
        return String.format("{\"string\": \"Testing %s!\"}", text);

    }
}
