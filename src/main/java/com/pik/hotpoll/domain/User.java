package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;

import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.services.interfaces.UserService;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;

import java.security.Principal;
import java.util.List;

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

    public static User fromPrincipal(Principal principal, UserService userService) throws ConstraintsViolationException {

        List<User> userList = userService.findByNickname(principal.getName());
        User userForPoll;

        if(userList.isEmpty()){
            String username = StringUtils.substringBetween(principal.toString(), "name=", ",");

            userList = userService.findByNickname(username);
            if(userList.isEmpty()){
                String email = StringUtils.substringBetween(principal.toString(), "email=", "}");
                String password = StringUtils.substringBetween(principal.toString(), "sub=", ",");


                userForPoll = User.builder().nickname(username).email(email).password(password).build();
                userService.create(userForPoll);
            }else {
                userForPoll = userList.get(0);
            }
        }else {
            userForPoll = userList.get(0);
        }

        return userForPoll;
    }

}

