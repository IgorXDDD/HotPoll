package com.pik.hotpoll.payload;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "LoginRequestBuilder")
@JsonDeserialize(builder = LoginRequest.LoginRequestBuilder.class)
public class LoginRequest {
    private String username;
    private String password;

    @JsonPOJOBuilder(withPrefix = "")
    public static class LoginRequestBuilder {

    }
}
