package com.pik.hotpoll.domain;

import javax.persistence.*;
import java.util.Map;

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


@Entity
public class Question {

    enum QuestionType{RADIO, CHECKBOX}
    public static Integer MAX_ANSWER_AMOUNT = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;


    @OneToMany(fetch = FetchType.LAZY)
    private Map<Integer, Answer> answers;

    public Question(String questionText, QuestionType questionType) {
        this.questionText = questionText;
        this.questionType = questionType;
    }

    public Question() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Map<Integer, Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, Answer> answers) {
        this.answers = answers;
    }
}
