package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pik.hotpoll.domain.Answer;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Question;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.payload.JwtResponse;
import com.pik.hotpoll.payload.LoginRequest;
import com.pik.hotpoll.payload.MessageResponse;
import com.pik.hotpoll.payload.SignupRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class PollControllerTest {
    private static JSONObject pollJsonObject;
    private static Poll poll;
    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;
    private static RestTemplate restTemplate;
    private static String createPollUrl;
    private static String jwt;
    private static boolean signed = false;
    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;


    @Test
    void getPolls() throws JsonProcessingException {
        Integer port = webServerAppCtxt.getWebServer().getPort();
        postPoll();
        createPollUrl = "http://localhost:" + port.toString() + "/api/poll";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+jwt);
        HttpEntity<String> request =
                new HttpEntity<>(headers);
        ResponseEntity<Poll[]> ret = restTemplate.exchange(createPollUrl, HttpMethod.GET, request, Poll[].class);
        assertNotNull(ret.getBody());
        List<Poll> polls = Arrays.asList(ret.getBody());

        System.out.println(polls.get(0).getAuthor());
        System.out.println(polls.get(0).getDate());
        System.out.println(polls.size());
        assertNotEquals(0, polls.size());
    }

    @Test
    void getPoll() throws JsonProcessingException {
        Integer port = webServerAppCtxt.getWebServer().getPort();
        createPollUrl = "http://localhost:" + port.toString() + "/api/poll";
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+jwt);

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


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createPollUrl).queryParam("pollID", ret.getId());

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
        Integer port = webServerAppCtxt.getWebServer().getPort();
        createPollUrl = "http://localhost:" + port.toString() + "/api/poll";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+jwt);
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
        assertEquals(poll.getAuthor(), ret.getAuthor());
        assertEquals(poll.getDate(), ret.getDate());
        assertEquals(poll.getTitle(), ret.getTitle());
        assertEquals(poll.getQuestions(), ret.getQuestions());
        assertNotEquals(poll.getId(), ret.getId());

    }

    @BeforeEach
    public void setup() throws JsonProcessingException {
        try {
            if (signed)
                return;
            Integer port = webServerAppCtxt.getWebServer().getPort();
            String signUpUrl = "http://localhost:"+ port.toString() +"/api/auth/signup";
            objectMapper = new ObjectMapper();
            restTemplate = new RestTemplate();
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            SignupRequest user = SignupRequest.builder().username("user1").email("user1@com").password("password").build();
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
            MessageResponse ret = restTemplate.postForObject(signUpUrl, request, MessageResponse.class);
            signed = true;
        }catch (HttpClientErrorException ignored){

        }
    }

    @Test
    void signIn() throws JsonProcessingException {
        Integer port = webServerAppCtxt.getWebServer().getPort();
        String signInUrl = "http://localhost:"+ port.toString() +"/api/auth/signin";

        objectMapper = new ObjectMapper();
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginRequest user = LoginRequest.builder().username("user1").password("password").build();
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
        JwtResponse ret = restTemplate.postForObject(signInUrl, request, JwtResponse.class);
        assertNotNull(ret);
        assertNotNull(ret.getJwt());
        jwt = ret.getJwt();
    }

}