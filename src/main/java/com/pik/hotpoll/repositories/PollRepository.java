package com.pik.hotpoll.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.pik.hotpoll.domain.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface PollRepository extends MongoRepository<Poll, String> {

    List<Poll> findByAuthorUsername(@Param("username") String name);
    List<Poll> findByTitle(@Param("title") String title);
    List<Poll> findByTags(@Param("tags") List<String> tags);
    List<Poll> findByDate(@Param("date") LocalDateTime date);
    Optional<Poll> findById(@Param("id") String id);
}

