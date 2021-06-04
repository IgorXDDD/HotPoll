package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pik.hotpoll.domain.Answer;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Question;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.payload.JwtResponse;
import com.pik.hotpoll.payload.LoginRequest;
import com.pik.hotpoll.payload.MessageResponse;
import com.pik.hotpoll.payload.SignupRequest;
import com.pik.hotpoll.repositories.UserRepository;
import com.pik.hotpoll.services.interfaces.PollService;
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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;

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
    @Autowired
    private PollService pollService;

    @Autowired
    private UserRepository userRepository;


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
        for(Poll p : polls){
            System.out.println(p.getDate());
        }
        System.out.println(polls.get(0).getAuthor());
        System.out.println(polls.get(0).getDate());
        System.out.println(polls.size());
        assertNotEquals(0, polls.size());
    }

    @Test
    void deletePoll() throws JsonProcessingException {
        Integer port = webServerAppCtxt.getWebServer().getPort();
        createPollUrl = "http://localhost:" + port.toString() + "/api/poll";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+jwt);
        HttpEntity<String> request =
                new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createPollUrl).queryParam("size", Integer.MAX_VALUE);
        ResponseEntity<Poll[]> ret = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, Poll[].class);
        assertNotNull(ret.getBody());
        List<Poll> polls = Arrays.asList(ret.getBody());
        int numPolls = polls.size();
        assertNotEquals(0, numPolls);
        Poll toDelete = polls.get(0);
        assertNotEquals("", toDelete.getId());
        builder = UriComponentsBuilder.fromHttpUrl(createPollUrl).queryParam("pollID", toDelete.getId());

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        ResponseEntity<String> delStatus = restTemplate.exchange(builder.toUriString(), HttpMethod.PATCH, request, String.class);
        assertNotNull(delStatus.getBody());
        assertEquals(HttpStatus.OK, delStatus.getStatusCode());
        builder = UriComponentsBuilder.fromHttpUrl(createPollUrl).queryParam("size", Integer.MAX_VALUE);
        ResponseEntity<Poll[]> ret_2 = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, Poll[].class);
        assertNotNull(ret_2.getBody());
        List<Poll> polls_2 = Arrays.asList(ret_2.getBody());
        assertEquals( numPolls - 1, polls_2.size());


    }

    @Test
    void searchPolls() throws JsonProcessingException, ConstraintsViolationException {
        Integer port = webServerAppCtxt.getWebServer().getPort();
        createPollUrl = "http://localhost:" + port.toString() + "/api/poll";

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+jwt);
        HttpEntity<String> request =
                new HttpEntity<>(headers);

        User user = User.builder().nickname("igor").id("igor").build();
        Random random = new Random();
        for(int i = 0 ; i < 1000; ++i) {
            List<String> tags = new ArrayList<>();
            List<Question> questions = new ArrayList<>();
            List<Answer> answers = new ArrayList<>();

            tags.add("tag" + random.nextInt(10));
            tags.add("tag" + random.nextInt(10));
            tags.add("tag" + random.nextInt(10));
            answers.add(Answer.builder().id("1").text("tak").votes(2).build());
            answers.add(Answer.builder().id("2").text("nie").votes(2).build());
            questions.add(Question.builder().type("radio").id("1").text("student?").answers(answers).build());
            questions.add(Question.builder().type("radio").id("2").text("debil?").answers(answers).build());
            Poll poll = Poll.builder().title("poll").author(user).date(LocalDateTime.now()).tags(tags).questions(questions).timesFilled(random.nextInt(100000)).build();
            pollService.create(poll, user);
        }
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag9");


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createPollUrl).queryParam("tags", tags);

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        System.out.println(request.toString());
        System.out.println(builder.toUriString());
        ResponseEntity<Poll[]> ret = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request,  Poll[].class);
        assertNotNull(ret.getBody());
        assertEquals(HttpStatus.OK, ret.getStatusCode());
        List<Poll> polls = Arrays.asList(ret.getBody());
        int prev = Integer.MAX_VALUE;
        for (Poll p : polls){

            System.out.println(p.getTimesFilled());
            System.out.println(p.getDate());
            System.out.println(p.getTags());
            assertTrue(prev >= p.getTimesFilled());
            assertFalse(Collections.disjoint(tags, p.getTags()));
            prev = p.getTimesFilled();
        }


    }


    @Test
    void updatePoll() throws JsonProcessingException {
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
        Poll updatedPoll = Poll.builder().id(ret.getId()).title("updatedPoll").author(user).date(LocalDateTime.now()).tags(tags).questions(questions).build();
        request = new HttpEntity<>(objectMapper.writeValueAsString(updatedPoll), headers);
        HttpEntity<Poll> response = restTemplate.exchange(createPollUrl, HttpMethod.PUT, request, Poll.class);
        assertNotNull(response.getBody());
        assertEquals(updatedPoll.getId(), response.getBody().getId());
        assertEquals(updatedPoll.getTitle(), response.getBody().getTitle());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createPollUrl).queryParam("pollID", updatedPoll.getId());
        response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, Poll.class);
        assertNotNull(response.getBody());
        assertEquals(updatedPoll.getId(), response.getBody().getId());
        assertEquals(updatedPoll.getTitle(), response.getBody().getTitle());

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
        poll = Poll.builder().title("poll").author(user).date(LocalDateTime.now()).tags(tags).timesFilled(0).questions(questions).build();
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
        User user = userRepository.findByNickname("user1").get(0);
        List<String> tags = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        tags.add("tag1"); tags.add("tag2"); tags.add("tag3");
        answers.add(Answer.builder().id("1").text("tak").votes(2).build());
        answers.add(Answer.builder().id("2").text("nie").votes(2).build());
        questions.add(Question.builder().type("radio").id("1").text("student?").answers(answers).build());
        questions.add(Question.builder().type("radio").id("2").text("debil?").answers(answers).build());
        poll = Poll.builder().title("poll").author(user).date(LocalDateTime.now()).tags(tags).timesFilled(0).questions(questions).build();
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(poll), headers);
        System.out.println(objectMapper.writeValueAsString(poll));
        Poll ret = restTemplate.postForObject(createPollUrl, request, Poll.class);
        System.out.println(ret.getId());
        System.out.println(objectMapper.writeValueAsString(ret));
        System.out.println(request.toString());
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
            List<User> userList = userRepository.findByNickname("user1");
            if(!userList.isEmpty()){
                return;
            }

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
            System.out.println(ignored);
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