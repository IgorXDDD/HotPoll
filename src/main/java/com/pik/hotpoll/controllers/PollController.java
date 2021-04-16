package com.pik.hotpoll.controllers;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/poll")
public class PollController {
    @GetMapping("")
    public String getPolls() {
        JSONObject jo = new JSONObject()
                .appendField("id",2137)
                .appendField("title","Pineapple and Pizza?")
                .appendField("date","16.04.2021")
                .appendField("author","Demongo")
                .appendField("timesCompleted",38)
                .appendField("tags",new JSONArray()
                        .appendElement("food")
                        .appendElement("pineapple")
                        .appendElement("pizza"))
                .appendField("alreadyCompleted",false)
                .appendField("questions",new JSONArray()
                        .appendElement(new JSONObject()
                        .appendField("qid",1)
                        .appendField("question", "Does pineapple belong on pizza?")
                        .appendField("type","radio")
                        .appendField("answers", new JSONArray()
                                .appendElement(new JSONObject()
                                        .appendField("aid",1)
                                        .appendField("answer","Hell Yeah!"))
                                .appendElement(new JSONObject()
                                        .appendField("aid",2)
                                        .appendField("answer", "Eww!"))))
        );
        return jo.toString();
    }

    @PostMapping("")
    public String postPoll(@RequestParam(value = "name", defaultValue = "Nice poll") String name) {
        JSONObject jo = new JSONObject();
        jo.put("message","Added poll: name: " + name + " ,description: Neat!");
        return jo.toString();
    }
}
