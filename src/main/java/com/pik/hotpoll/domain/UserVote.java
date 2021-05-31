package com.pik.hotpoll.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Builder(builderClassName = "UserVoteBuilder")
@JsonDeserialize(builder = UserVote.UserVoteBuilder.class)
public class UserVote {
    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Poll poll;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserVoteBuilder {

    }
}
