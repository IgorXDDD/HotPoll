package com.pik.hotpoll.repositories;

import com.pik.hotpoll.domain.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends MongoRepository<Poll, Integer> {

    Poll findPollById(Integer id);
}
