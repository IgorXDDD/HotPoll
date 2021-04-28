package com.pik.hotpoll.repositories;

import com.pik.hotpoll.domain.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends MongoRepository<Answer, Integer> {
    Answer findAnswerById(Integer id);
}
