package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

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

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {

    }


}

