package com.pik.hotpoll.payloads;

import java.util.List;

public class JwtResponse {
    String jwt;
    String id;
    String username;
    String email;

    public JwtResponse(String jwt, String id, String username, String email) {
        this.jwt = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
