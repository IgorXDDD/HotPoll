package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder(builderClassName = "VoteBuilder")
@JsonDeserialize(builder = Vote.VoteBuilder.class)
public class Vote {
    @NotNull
    private final String pollID;
    @NotNull
    private final List<AnswerID> answers;

    @Data
    @Builder(builderClassName = "AnswerIDBuilder")
    public static class AnswerID{
        private final int questionID;
        private final int answerID;
        @JsonPOJOBuilder(withPrefix = "")
        public static class AnswerIDBuilder {

        }
    }
    @JsonPOJOBuilder(withPrefix = "")
    public static class VoteBuilder {

    }

}

