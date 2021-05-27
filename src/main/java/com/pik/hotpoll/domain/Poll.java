package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;




@Data
@TypeAlias("poll")
@Document(collection = "polls")
@QueryEntity
@Builder(builderClassName = "PollBuilder")
@JsonDeserialize(builder = Poll.PollBuilder.class)
public class Poll {
    @Id
    private String id;
    @NotNull
    private final String title;
    @Indexed(direction = IndexDirection.DESCENDING)
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    @NotNull
    private final LocalDateTime date;
    @NotNull
    private final User author;
    private final List<String> tags;
    @NotNull
    private final List<Question> questions;

    @Indexed(direction = IndexDirection.DESCENDING)
    @NotNull
    private int timesFilled;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PollBuilder {

    }

    public void addVote(Vote vote){
        ++timesFilled;
        for (Vote.AnswerID id : vote.getAnswers()){
            questions.get(id.getQuestionID()).getAnswers().get(id.getAnswerID()).addVote();
        }
    }

}