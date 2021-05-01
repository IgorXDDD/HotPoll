package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.List;

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
//@NoArgsConstructor
//@AllArgsConstructor
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

