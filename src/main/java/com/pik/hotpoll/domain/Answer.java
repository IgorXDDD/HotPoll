package com.pik.hotpoll.domain;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String answerText;

    public Answer(String answerText) {
        this.answerText = answerText;
    }

    public Answer(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
