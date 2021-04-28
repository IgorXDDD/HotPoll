package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.Question;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

public interface QuestionService {

    Question find(Integer questionId) throws EntityNotFoundException;

    Question create(Question question) throws ConstraintsViolationException;

    void delete(Integer id) throws EntityNotFoundException, ConstraintsViolationException;

    void addAnswer(Integer answerId, Integer questionId) throws ConstraintsViolationException, EntityNotFoundException;

    void deleteAnswer(Integer answerId, Integer questionId) throws ConstraintsViolationException, EntityNotFoundException;

    Iterable<Question> findAll();

}
