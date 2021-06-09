package com.pik.hotpoll.payload;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "JwtResponseBuilder")
@JsonDeserialize(builder = JwtResponse.JwtResponseBuilder.class)
public class JwtResponse {
    private String jwt;
    private String id;
    private String username;
    private String email;

    @JsonPOJOBuilder(withPrefix = "")
    public static class JwtResponseBuilder {

    }
}
