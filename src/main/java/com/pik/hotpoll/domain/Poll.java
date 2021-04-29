package com.pik.hotpoll.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

//        JSONObject jo = new JSONObject()
//                .appendField("id",2137)
//                .appendField("title","Pineapple and Pizza?")
//                .appendField("date","16.04.2021")
//                .appendField("author","Demongo")
//                .appendField("timesCompleted",38)
//                .appendField("tags",new JSONArray()
//                        .appendElement("food")
//                        .appendElement("pineapple")
//                        .appendElement("pizza"))
//                .appendField("alreadyCompleted",false)
//                .appendField("questions",new JSONArray()
//                        .appendElement(new JSONObject()
//                        .appendField("qid",1)
//                        .appendField("question", "Does pineapple belong on pizza?")
//                        .appendField("type","radio")
//                        .appendField("answers", new JSONArray()
//                                .appendElement(new JSONObject()
//                                        .appendField("aid",1)
//                                        .appendField("answer","Hell Yeah!"))
//                                .appendElement(new JSONObject()
//                                        .appendField("aid",2)
//                                        .appendField("answer", "Eww!"))))
//        );




@Data
@Document(collection = "polls")
//@NoArgsConstructor
//@AllArgsConstructor
@Builder(builderClassName = "PollBuilder")
@JsonDeserialize(builder = Poll.PollBuilder.class)
public class Poll {
    @Id
//    @JsonIgnore
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