package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
@Builder(builderClassName = "AnswerBuilder")
@JsonDeserialize(builder = Answer.AnswerBuilder.class)
public class Answer {
    @Id
    private final String id;
    @NotNull
    private final String text;
    @NotNull
    private Integer votes;

    @JsonPOJOBuilder(withPrefix = "")
    public static class AnswerBuilder {

    }
    public void addVote(){
        ++votes;
    }


}

