package com.pik.hotpoll.payload;

public class JwtResponse {
    private String jwt;
    private String id;
    private String username;
    private String email;
    public JwtResponse(String jwt, String id, String username, String email) {
        this.jwt = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
