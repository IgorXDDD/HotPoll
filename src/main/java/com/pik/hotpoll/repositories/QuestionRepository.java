package com.pik.hotpoll.repositories;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Question, Integer> {
    Question findQuestionById(Integer id);
}
