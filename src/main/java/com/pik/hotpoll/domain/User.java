package com.pik.hotpoll.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder(builderClassName = "UserBuilder")
@JsonDeserialize(builder = User.UserBuilder.class)
public class User {
    @Id
    private final String id;
    @NotNull
    private final String nickname;
    @NotNull
    private final String email;
    @NotNull
    private Boolean emailVerified = false;




    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {

    }


}

