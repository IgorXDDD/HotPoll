package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pik.hotpoll.domain.*;
import com.pik.hotpoll.payload.JwtResponse;
import com.pik.hotpoll.payload.LoginRequest;
import com.pik.hotpoll.payload.MessageResponse;
import com.pik.hotpoll.payload.SignupRequest;
import org.json.JSONObject;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class VoteControllerTest {
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
    void addVote() throws JsonProcessingException {

        Integer port = webServerAppCtxt.getWebServer().getPort();
        String signInUrl = "http://localhost:"+ port.toString() +"/api/auth/signin";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginRequest user_ = LoginRequest.builder().username("user1").password("password").build();
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(user_), headers);
        JwtResponse ret_ = restTemplate.postForObject(signInUrl, request, JwtResponse.class);
        assertNotNull(ret_);
        assertNotNull(ret_.getJwt());
        jwt = ret_.getJwt();

        createPollUrl = "http://localhost:" + port.toString() + "/api/poll";
        String voteUrl = "http://localhost:" + port.toString() + "/api/vote";

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
        request = new HttpEntity<>(objectMapper.writeValueAsString(poll), headers);

        Poll ret = restTemplate.postForObject(createPollUrl, request, Poll.class);
        assertNotNull(ret);
        List<Vote.AnswerID> answerIDS = new ArrayList<>();
        answerIDS.add(Vote.AnswerID.builder().questionID(1).answerID(0).build());
        answerIDS.add(Vote.AnswerID.builder().questionID(0).answerID(0).build());
        Vote vote = Vote.builder().pollID(ret.getId()).answers(answerIDS).build();
        request = new HttpEntity<>(objectMapper.writeValueAsString(vote), headers);
        System.out.println(objectMapper.writeValueAsString(vote));
        Poll afterVote = restTemplate.postForObject(voteUrl, request, Poll.class);

        assertNotNull(afterVote);
        assertEquals(poll.getQuestions().get(0).getAnswers().get(0).getVotes() + 1 , afterVote.getQuestions().get(0).getAnswers().get(0).getVotes());
        assertEquals(poll.getQuestions().get(1).getAnswers().get(0).getVotes() + 1 , afterVote.getQuestions().get(1).getAnswers().get(0).getVotes());
        assertEquals(poll.getTimesFilled() + 1 , afterVote.getTimesFilled());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createPollUrl).queryParam("pollID", ret.getId());


        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<Poll> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Poll.class);
        assertNotNull(response.getBody());
        assertEquals(ret.getId() , response.getBody().getId());
        assertEquals(poll.getQuestions().get(0).getAnswers().get(0).getVotes() + 1 , response.getBody().getQuestions().get(0).getAnswers().get(0).getVotes());
    }

    @Test
    void hasVoted() throws JsonProcessingException {

        Integer port = webServerAppCtxt.getWebServer().getPort();
        String signInUrl = "http://localhost:"+ port.toString() +"/api/auth/signin";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginRequest user_ = LoginRequest.builder().username("user1").password("password").build();
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(user_), headers);
        JwtResponse ret_ = restTemplate.postForObject(signInUrl, request, JwtResponse.class);
        assertNotNull(ret_);
        assertNotNull(ret_.getJwt());
        jwt = ret_.getJwt();

        createPollUrl = "http://localhost:" + port.toString() + "/api/poll";
        String voteUrl = "http://localhost:" + port.toString() + "/api/vote";

        headers.set("Authorization", "Bearer "+jwt);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(voteUrl).queryParam("pollID", 0);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);
        assertNotNull(response.getBody());
        assertEquals("NO" , response.getBody());

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
        request = new HttpEntity<>(objectMapper.writeValueAsString(poll), headers);

        Poll ret = restTemplate.postForObject(createPollUrl, request, Poll.class);
        assertNotNull(ret);
        List<Vote.AnswerID> answerIDS = new ArrayList<>();
        answerIDS.add(Vote.AnswerID.builder().questionID(1).answerID(0).build());
        answerIDS.add(Vote.AnswerID.builder().questionID(0).answerID(0).build());
        Vote vote = Vote.builder().pollID(ret.getId()).answers(answerIDS).build();
        request = new HttpEntity<>(objectMapper.writeValueAsString(vote), headers);
        System.out.println(objectMapper.writeValueAsString(vote));
        Poll afterVote = restTemplate.postForObject(voteUrl, request, Poll.class);

        assertNotNull(afterVote);

        builder = UriComponentsBuilder.fromHttpUrl(voteUrl).queryParam("pollID", ret.getId());


        entity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);
        assertNotNull(response.getBody());
        assertEquals("YES" , response.getBody());
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

}