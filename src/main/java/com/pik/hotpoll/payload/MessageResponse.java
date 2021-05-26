package com.pik.hotpoll.payload;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "MessageResponseBuilder")
@JsonDeserialize(builder = MessageResponse.MessageResponseBuilder.class)
public class MessageResponse {
    private String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MessageResponseBuilder {

    }

}
