package com.pik.hotpoll.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class VersionControllerTest {
    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @Test
    void getVersion() {
        Integer port = webServerAppCtxt.getWebServer().getPort();
        String getVersionUrl = "http://localhost:" + port.toString() + "/api/version";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> ret = restTemplate.exchange(getVersionUrl, HttpMethod.GET, request, String.class);
        assertNotNull(ret.getBody());
        String version = ret.getBody();
        System.out.println(version);
        assertNotEquals("unknown", version);
    }

}