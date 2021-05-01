package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pik.hotpoll.domain.Answer;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Question;
import com.pik.hotpoll.domain.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PollControllerTest {
    private static JSONObject pollJsonObject;
    private static Poll poll;
    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;
    private static RestTemplate restTemplate;
    private static String createPollUrl;

    @Test
    void getPolls() throws JsonProcessingException {
        postPoll();
        createPollUrl = "http://localhost:4444/poll";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Poll[]> ret = restTemplate.getForEntity(createPollUrl, Poll[].class);
        assertNotNull(ret.getBody());
        List<Poll> polls = Arrays.asList(ret.getBody());

        System.out.println(polls.get(0).getAuthor());
        System.out.println(polls.get(0).getDate());
        System.out.println(polls.size());
        assertNotEquals(0, polls.size());
    }

    @Test
    void getPoll() throws JsonProcessingException {
        createPollUrl = "http://localhost:4444/poll";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = User.builder().nickname("igor").id("igor").build();
        List<String> tags = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        tags.add("tag1"); tags.add("tag2"); tags.add("tag3");
        answers.add(Answer.builder().id("1").text("tak").votes(2).build());
        answers.add(Answer.builder().id("2").text("nie").votes(2).build());
        questions.add(Question.builder().type("radio").id("1").text("student?").answers(answers).build());
        questions.add(Question.builder().type("radio").id("2").text("debil?").answers(answers).build());
        poll = Poll.builder().title("poll").author(user).date(LocalDateTime.now()).tags(tags).questions(questions).build();
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(poll), headers);

        Poll ret = restTemplate.postForObject(createPollUrl, request, Poll.class);
        assertNotNull(ret);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createPollUrl)
                .queryParam("pollID", ret.getId());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<Poll> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Poll.class);
        assertNotNull(response.getBody());
        assertEquals(ret.getId() , response.getBody().getId());
    }

    @Test
    void postPoll() throws JsonProcessingException {
        createPollUrl = "http://localhost:4444/poll";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = User.builder().nickname("igor").id("igor").build();
        List<String> tags = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        tags.add("tag1"); tags.add("tag2"); tags.add("tag3");
        answers.add(Answer.builder().id("1").text("tak").votes(2).build());
        answers.add(Answer.builder().id("2").text("nie").votes(2).build());
        questions.add(Question.builder().type("radio").id("1").text("student?").answers(answers).build());
        questions.add(Question.builder().type("radio").id("2").text("debil?").answers(answers).build());
        poll = Poll.builder().title("poll").author(user).date(LocalDateTime.now()).tags(tags).questions(questions).build();
        HttpEntity<String> request =
                new HttpEntity<>(objectMapper.writeValueAsString(poll), headers);

        Poll ret =
                restTemplate.postForObject(createPollUrl, request, Poll.class);
        assertNotNull(ret);
        assertEquals(poll.getAuthor(), ret.getAuthor());
        assertEquals(poll.getDate(), ret.getDate());
        assertEquals(poll.getTitle(), ret.getTitle());
        assertEquals(poll.getQuestions(), ret.getQuestions());
        assertNotEquals(poll.getId(), ret.getId());

    }
}