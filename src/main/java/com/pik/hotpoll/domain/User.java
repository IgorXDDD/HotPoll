package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder(builderClassName = "UserBuilder")
@JsonDeserialize(builder = User.UserBuilder.class)
public class User {
    @Id
    private String id;
    @NotNull
    private final String nickname;
    private final String email;
    @NotNull
    private final String password;


    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {

    }

}

