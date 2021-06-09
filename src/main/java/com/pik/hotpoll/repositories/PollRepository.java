package com.pik.hotpoll.repositories;

import com.pik.hotpoll.domain.User;
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


    List<Poll> findByTitleLikeIgnoreCase(@Param("title") String title, Pageable paging);

    Long countByTitleLike(@Param("title") String title);

    @Query(value = "{tags: {$all: ?0 } }")
    List<Poll> findByTags(@Param("tags") List<String> tags, Pageable paging);

    @Query(value = "{tags: {$all: ?0 } }", count = true)
    Long countByTags(@Param("tags") List<String> tags);

    List<Poll> findByDate(@Param("date") LocalDateTime date);

    List<Poll> findByAuthor(@Param("author") User author, Pageable paging);
}

