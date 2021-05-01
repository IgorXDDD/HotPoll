package com.pik.hotpoll.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PollDTO {

    @JsonIgnore
    private Integer id;
    private String title;
    private ZonedDateTime date = ZonedDateTime.now();
    private Integer authorId;
    private Integer timesCompleted;
    private String tag1;
    private String tag2;
    private String tag3;
    private Integer numOfQuestions;
    private Map<Integer,Question> questions;

    public PollDTO(Integer id, String title, ZonedDateTime date, Integer authorId, Integer timesCompleted,
                   String tag1, String tag2, String tag3, Integer numOfQuestions, Map<Integer, Question> questions) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.authorId = authorId;
        this.timesCompleted = timesCompleted;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.numOfQuestions = numOfQuestions;
        this.questions = questions;
    }

    private PollDTO(){
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public Integer getTimesCompleted() {
        return timesCompleted;
    }

    public String getTag1() {
        return tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public Integer getNumOfQuestions() {
        return numOfQuestions;
    }

    public Map<Integer, Question> getQuestions() {
        return questions;
    }

    public static class PollDTOBuilder{
        private Integer id;
        private String title;
        private ZonedDateTime date = ZonedDateTime.now();
        private Integer authorId;
        private Integer timesCompleted;
        private String tag1;
        private String tag2;
        private String tag3;
        private Integer numOfQuestions;
        private Map<Integer,Question> questions;

        public PollDTOBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public PollDTOBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public PollDTOBuilder setDate(ZonedDateTime date) {
            this.date = date;
            return this;
        }

        public PollDTOBuilder setAuthorId(Integer authorId) {
            this.authorId = authorId;
            return this;
        }

        public PollDTOBuilder setTimesCompleted(Integer timesCompleted) {
            this.timesCompleted = timesCompleted;
            return this;
        }

        public PollDTOBuilder setTag1(String tag1) {
            this.tag1 = tag1;
            return this;
        }

        public PollDTOBuilder setTag2(String tag2) {
            this.tag2 = tag2;
            return this;
        }

        public PollDTOBuilder setTag3(String tag3) {
            this.tag3 = tag3;
            return this;
        }

        public PollDTOBuilder setNumOfQuestions(Integer numOfQuestions) {
            this.numOfQuestions = numOfQuestions;
            return this;
        }

        public PollDTOBuilder setQuestions(Map<Integer, Question> questions) {
            this.questions = questions;
            return this;
        }

        public PollDTO build(){
            return new PollDTO(id, title, date, authorId, timesCompleted,
                    tag1, tag2, tag3, numOfQuestions, questions);
        }
    }
}
