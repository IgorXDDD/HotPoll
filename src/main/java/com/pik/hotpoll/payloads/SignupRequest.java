package com.pik.hotpoll.payloads;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.pik.hotpoll.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder(builderClassName = "SignupRequestBuilder")
@JsonDeserialize(builder = SignupRequest.SignupRequestBuilder.class)
public class SignupRequest {
    private String username;
    private String password;
    private String email;


    @JsonPOJOBuilder(withPrefix = "")
    public static class SignupRequestBuilder {

    }
}
