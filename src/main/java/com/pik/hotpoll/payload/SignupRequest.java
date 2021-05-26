package com.pik.hotpoll.payload;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "SignupRequestBuilder")
@JsonDeserialize(builder = SignupRequest.SignupRequestBuilder.class)
public class SignupRequest {
    private String username;
    private String email;
    private String password;

    @JsonPOJOBuilder(withPrefix = "")
    public static class SignupRequestBuilder {

    }
}
