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
        JSONObject jo = new JSONObject();
        jo.put("id",2137);
        jo.put("title","Pineapple and Pizza?");
        jo.put("date","16.04.2021");
        jo.put("author","Demongo");
        jo.put("timesCompleted",38);
        jo.put("tags",new JSONArray()
                .appendElement("food")
                .appendElement("pineapple")
                .appendElement("pizza"));
        jo.put("alreadyCompleted",false);
        jo.put("questions",new JSONArray()
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
