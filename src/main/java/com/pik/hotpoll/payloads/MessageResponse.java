package com.pik.hotpoll.payloads;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder(builderClassName = "MessageResponseBuilder")
@JsonDeserialize(builder = MessageResponse.MessageResponseBuilder.class)
public class MessageResponse {
    String string;


    @JsonPOJOBuilder(withPrefix = "")
    public static class MessageResponseBuilder {

    }
}
