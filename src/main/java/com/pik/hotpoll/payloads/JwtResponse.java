package com.pik.hotpoll.payloads;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder(builderClassName = "JwtResponseBuilder")
@JsonDeserialize(builder = JwtResponse.JwtResponseBuilder.class)
public class JwtResponse {
    String jwt;
    String id;
    String username;
    String email;


    @JsonPOJOBuilder(withPrefix = "")
    public static class JwtResponseBuilder {

    }
}
