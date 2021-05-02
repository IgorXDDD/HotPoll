package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.hotpoll.domain.Answer;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Question;
import com.pik.hotpoll.domain.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PollControllerTest {
    private static JSONObject pollJsonObject;
    private static Poll poll;
    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;
    private static RestTemplate restTemplate;
    private static String createPollUrl;
    @BeforeClass
    public static void runBeforeAllTestMethods() throws JSONException, JsonProcessingException {
        createPollUrl = "http://localhost:4444/poll";

        objectMapper = new ObjectMapper();
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        pollJsonObject = new JSONObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        User user = User.builder().username("igor").id("igor").build();
        List<String> tags = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();

        tags.add("tag1"); tags.add("tag2"); tags.add("tag3");
        answers.add(Answer.builder().id("1").text("tak").votes(2).build());
        answers.add(Answer.builder().id("2").text("nie").votes(2).build());
        questions.add(Question.builder().type("radio").id("1").text("student?").answers(answers).build());
        questions.add(Question.builder().type("radio").id("2").text("debil?").answers(answers).build());
        poll = Poll.builder().author(user).date(LocalDateTime.now()).tags(tags).questions(questions).build();
//        pollJsonObject.put("title", "JP2");
//        pollJsonObject.put("date", LocalDateTime.now().format(formatter));
//        pollJsonObject.put("user", objectMapper.writeValueAsString(user));

    }

    @Test
    void getPolls() {
    }

    @Test
    void getPoll() {
    }

//    @Test
//    void postPoll() throws JsonProcessingException {
//        createPollUrl = "http://localhost:4444/pollack";
//
//        objectMapper = new ObjectMapper();
//        restTemplate = new RestTemplate();
//        headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
//        User user = User.builder().nickname("igor").id("igor").build();
//        List<String> tags = new ArrayList<>();
//        List<Question> questions = new ArrayList<>();
//        List<Answer> answers = new ArrayList<>();
//
//        tags.add("tag1"); tags.add("tag2"); tags.add("tag3");
//        answers.add(Answer.builder().id("1").text("tak").votes(2).build());
//        answers.add(Answer.builder().id("2").text("nie").votes(2).build());
//        questions.add(Question.builder().type("radio").id("1").text("student?").answers(answers).build());
//        questions.add(Question.builder().type("radio").id("2").text("debil?").answers(answers).build());
//        poll = Poll.builder().title("poll").author(user).date(LocalDateTime.now()).tags(tags).questions(questions).build();
//        HttpEntity<String> request =
//                new HttpEntity<>(objectMapper.writeValueAsString(poll), headers);
//
//        String personResultAsJsonStr =
//                restTemplate.postForObject(createPollUrl, request, String.class);
//        System.out.println(personResultAsJsonStr);
//        JsonNode root = objectMapper.readTree(personResultAsJsonStr);
//
//        assertNotNull(personResultAsJsonStr);
//        assertNotNull(root);
//        assertNotNull(root.path("name").asText());
//    }
}