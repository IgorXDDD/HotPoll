package com.pik.hotpoll.domain;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Entity
public class Poll {
    public static final Integer MAX_QUESTION_AMOUNT = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime date = ZonedDateTime.now();

    @Column(nullable = false)
    private Integer authorId;

    @Column
    private Integer timesCompleted;

    @Column
    private String tag1;

    @Column
    private String tag2;

    @Column
    private String tag3;

    @Column
    private Integer numOfQuestions;

    @OneToMany(fetch = FetchType.LAZY)
    private Map<Integer,Question> questions;

    public Poll(String title, Integer authorId) {
        this.title = title;
        this.date = null;
        this.authorId = authorId;
        this.timesCompleted = null;
        this.tag1 = null;
        this.tag2 = null;
        this.tag3 = null;
        this.numOfQuestions = null;
    }

    public Poll() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getTimesCompleted() {
        return timesCompleted;
    }

    public void setTimesCompleted(Integer timesCompleted) {
        this.timesCompleted = timesCompleted;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public Integer getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(Integer numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public Map<Integer, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<Integer, Question> questions) {
        this.questions = questions;
    }
}
