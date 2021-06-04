package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pik.hotpoll.domain.*;
import com.pik.hotpoll.payload.JwtResponse;
import com.pik.hotpoll.payload.LoginRequest;
import com.pik.hotpoll.payload.MessageResponse;
import com.pik.hotpoll.payload.SignupRequest;
import com.pik.hotpoll.repositories.PollRepository;
import com.pik.hotpoll.services.interfaces.PollService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatisticsControllerTest {
    private static JSONObject pollJsonObject;
    private static Poll poll;
    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;
    private static RestTemplate restTemplate;
    private static String createPollUrl;
    private static String jwt;
    private static boolean signed = false;
    private static int addedCount = 0;
    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @Autowired
    private PollRepository repository;

    @Test
    void getNumVotesTest() throws JsonProcessingException {
//        repository.deleteAll();
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
        String statisticsUrl = "http://localhost:" + port.toString() + "/api/statistics";

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

        for (int i = 0; i<100; ++i){
            Poll ret = restTemplate.postForObject(createPollUrl, request, Poll.class);
            assertNotNull(ret);
        }
        addedCount += 100;
        request = new HttpEntity<>(headers);
        ResponseEntity<Long> ret = restTemplate.exchange(statisticsUrl, HttpMethod.GET, request, Long.class);
        assertNotNull(ret);
        assertEquals(addedCount, ret.getBody());
    }

    @Test
    void getCountByNameTest() throws JsonProcessingException {
        repository.deleteAll();
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
        String statisticsUrl = "http://localhost:" + port.toString() + "/api/statistics";

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
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "igor";
            }
        };
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("poll",objectMapper.writeValueAsString(poll));
        body.add("principal", principal.toString());
        HttpEntity<?> request1 = new HttpEntity<Object>(body, headers);
        for (int i = 0; i<100; ++i){
            Poll ret = restTemplate.postForObject(createPollUrl, request1, Poll.class);
            assertNotNull(ret);
        }
        addedCount += 100;
        List<String> tags2 = new ArrayList<>();
        tags2.add("tag12"); tags.add("tag22"); tags.add("tag32");
        poll = Poll.builder().title("poll x").author(user).date(LocalDateTime.now()).tags(tags2).questions(questions).build();
        request = new HttpEntity<>(objectMapper.writeValueAsString(poll), headers);

        for (int i = 0; i<10; ++i){
            Poll ret = restTemplate.postForObject(createPollUrl, request, Poll.class);
            assertNotNull(ret);
        }
        addedCount += 10;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(statisticsUrl).queryParam("name", "x");
        request = new HttpEntity<>(headers);
        ResponseEntity<Long> ret = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, Long.class);
        assertNotNull(ret);
        assertEquals(10, ret.getBody());
    }

    @Test
    void getCountByTagsTest() throws JsonProcessingException {

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
        String statisticsUrl = "http://localhost:" + port.toString() + "/api/statistics";

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

        for (int i = 0; i<100; ++i){
            Poll ret = restTemplate.postForObject(createPollUrl, request, Poll.class);
            assertNotNull(ret);
        }
        addedCount += 100;
        List<String> tags2 = new ArrayList<>();
        tags2.add("x"); tags.add("y"); tags.add("z");
        poll = Poll.builder().title("poll").author(user).date(LocalDateTime.now()).tags(tags2).questions(questions).build();
        request = new HttpEntity<>(objectMapper.writeValueAsString(poll), headers);

        for (int i = 0; i<10; ++i){
            Poll ret = restTemplate.postForObject(createPollUrl, request, Poll.class);
            assertNotNull(ret);
        }
        addedCount += 10;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(statisticsUrl).queryParam("tags", tags2);
        request = new HttpEntity<>(headers);
        ResponseEntity<Long> ret = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, Long.class);
        assertNotNull(ret);
        assertEquals(10, ret.getBody());
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