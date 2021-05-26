package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;




@Data
@Document(collection = "polls")

@Builder(builderClassName = "PollBuilder")
@JsonDeserialize(builder = Poll.PollBuilder.class)
public class Poll {
    @Id
    private final String id;
    @NotNull
    private final String title;
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    @NotNull
    private final LocalDateTime date;
    @NotNull
    private final User author;
    private final List<String> tags;
    @NotNull
    private final List<Question> questions;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PollBuilder {

    }

}