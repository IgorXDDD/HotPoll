package com.pik.hotpoll.repositories;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.pik.hotpoll.domain.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends MongoRepository<Poll, String>, QuerydslPredicateExecutor<Poll> {


    List<Poll> findByAuthorNickname(@Param("nickname") String name);
    List<Poll> findByTitle(@Param("title") String title);

    @Query(value = "{tags: {$in: ?0 } }")
    List<Poll> findByTags(@Param("tags") List<String> tags, Pageable paging);

    List<Poll> findByDate(@Param("date") LocalDateTime date);

    Optional<Poll> findById(@Param("id") String id);
}

