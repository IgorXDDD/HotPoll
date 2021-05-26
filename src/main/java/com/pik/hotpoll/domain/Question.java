package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.List;


@Data
@Builder(builderClassName = "QuestionBuilder")
@JsonDeserialize(builder = Question.QuestionBuilder.class)
public class Question {
    @Id
    private final String id;
    @NotNull
    private final String text;
    @NotNull
    private final String type;
    @NotNull
    private final List<Answer> answers;

    @JsonPOJOBuilder(withPrefix = "")
    public static class QuestionBuilder {

    }


}

