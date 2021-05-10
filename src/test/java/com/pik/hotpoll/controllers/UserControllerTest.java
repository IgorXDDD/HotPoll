package com.pik.hotpoll.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pik.hotpoll.config.SecurityConfig;
import com.pik.hotpoll.domain.Answer;
import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Question;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.payload.JwtResponse;
import com.pik.hotpoll.payload.LoginRequest;
import com.pik.hotpoll.payload.MessageResponse;
import com.pik.hotpoll.payload.SignupRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    private static JSONObject pollJsonObject;
    private static User user;
    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;
    private static RestTemplate restTemplate;
    private static String createUserUrl;
    private static String jwt;
    private static boolean signed = false;
    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

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


    @Test
    void getUsers() throws JsonProcessingException {
        Integer port = webServerAppCtxt.getWebServer().getPort();
        createUserUrl = "http://localhost:" + port.toString() + "/api/user";

        objectMapper = new ObjectMapper();
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+jwt);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<User[]> ret = restTemplate.exchange(
                createUserUrl,
                HttpMethod.GET,
                entity,
                User[].class);
        assertNotNull(ret.getBody());
        List<User> users = Arrays.asList(ret.getBody());

        System.out.println(users.get(0).getId());
        System.out.println(users.get(0).getNickname());
        System.out.println(users.size());
        assertNotEquals(0, users.size());
    }

    @Test
    void getUser() throws JsonProcessingException {
        Integer port = webServerAppCtxt.getWebServer().getPort();
        createUserUrl = "http://localhost:" + port.toString() + "/api/user";

        objectMapper = new ObjectMapper();
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+jwt);
        User user = User.builder().nickname("igor").email("email@com").password("abc").build();
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);

        User ret = restTemplate.postForObject(createUserUrl, request, User.class);
        assertNotNull(ret);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createUserUrl)
                .queryParam("userID", ret.getId());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<User> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                User.class);
        assertNotNull(response.getBody());
        assertEquals(ret.getId() , response.getBody().getId());
    }

    @Test
    void postUser() throws JsonProcessingException {
        Integer port = webServerAppCtxt.getWebServer().getPort();
        createUserUrl = "http://localhost:" + port.toString() + "/api/user";

        objectMapper = new ObjectMapper();
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+jwt);
        User user = User.builder().nickname("igor2").email("email2@com").password("abc2").build();
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);

        User ret = restTemplate.postForObject(createUserUrl, request, User.class);
        assertNotNull(ret);
        assertEquals(user.getNickname(), ret.getNickname());
        assertEquals(user.getEmail(), ret.getEmail());
        assertEquals(user.getPassword(), ret.getPassword());
        assertNotEquals(user.getId(), ret.getId());

    }
}