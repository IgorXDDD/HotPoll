package com.pik.hotpoll.payload;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.pik.hotpoll.domain.Poll;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
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
