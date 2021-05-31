package com.pik.hotpoll.repositories;

import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.UserVote;
import com.pik.hotpoll.domain.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserVoteRepository extends MongoRepository<UserVote, String> {
    List<UserVote> findByUser(@Param("user") User user);
}
