//package com.pik.hotpoll.controllers;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.pik.hotpoll.config.SecurityConfig;
//import com.pik.hotpoll.domain.Answer;
//import com.pik.hotpoll.domain.Poll;
//import com.pik.hotpoll.domain.Question;
//import com.pik.hotpoll.domain.User;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.BeforeClass;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.*;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
//class UserControllerTest {
//    private static JSONObject pollJsonObject;
//    private static User user;
//    private static ObjectMapper objectMapper;
//    private static HttpHeaders headers;
//    private static RestTemplate restTemplate;
//    private static String createUserUrl;
//
//    @Test
//    @WithMockUser(username = "Pa", password = "haslo")
//    void getUsers() throws JsonProcessingException {
//        postUser();
//        createUserUrl = "http://localhost:4444/api/user";
//
//        objectMapper = new ObjectMapper();
//        restTemplate = new RestTemplate();
//        headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<User[]> ret = restTemplate.getForEntity(createUserUrl, User[].class);
//        assertNotNull(ret.getBody());
//        List<User> users = Arrays.asList(ret.getBody());
//
//        System.out.println(users.get(0).getId());
//        System.out.println(users.get(0).getNickname());
//        System.out.println(users.size());
//        assertNotEquals(0, users.size());
//    }
//
//    @Test
//    @WithMockUser(username = "Pa", password = "haslo")
//    void getUser() throws JsonProcessingException {
//        createUserUrl = "http://localhost:4444/api/user";
//
//        objectMapper = new ObjectMapper();
//        restTemplate = new RestTemplate();
//        headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        User user = User.builder().nickname("igor").email("email@com").password("abc").build();
//        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
//
//        User ret = restTemplate.postForObject(createUserUrl, request, User.class);
//        assertNotNull(ret);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createUserUrl)
//                .queryParam("userID", ret.getId());
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        HttpEntity<User> response = restTemplate.exchange(
//                builder.toUriString(),
//                HttpMethod.GET,
//                entity,
//                User.class);
//        assertNotNull(response.getBody());
//        assertEquals(ret.getId() , response.getBody().getId());
//    }
//
//    @Test
//    @WithMockUser(username = "Pa", password = "haslo")
//    void postUser() throws JsonProcessingException {
//        createUserUrl = "http://localhost:4444/api/user";
//
//        objectMapper = new ObjectMapper();
//        restTemplate = new RestTemplate();
//        headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        User user = User.builder().nickname("igor2").email("email2@com").password("abc2").build();
//        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);
//
//        User ret = restTemplate.postForObject(createUserUrl, request, User.class);
//        assertNotNull(ret);
//        assertEquals(user.getNickname(), ret.getNickname());
//        assertEquals(user.getEmail(), ret.getEmail());
//        assertEquals(user.getPassword(), ret.getPassword());
//        assertNotEquals(user.getId(), ret.getId());
//
//    }
//}