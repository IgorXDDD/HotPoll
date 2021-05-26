package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "VoteBuilder")
@JsonDeserialize(builder = Vote.VoteBuilder.class)
public class Vote {
    @NotNull
    private final String pollID;
    @NotNull
    private final int questionID;
    @NotNull
    private final int answerID;


    @JsonPOJOBuilder(withPrefix = "")
    public static class VoteBuilder {

    }

}

