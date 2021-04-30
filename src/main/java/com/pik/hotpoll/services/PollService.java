package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;

import javax.persistence.EntityNotFoundException;

public interface PollService {

    Poll find(String id) throws EntityNotFoundException;

    Poll create(Poll poll) throws ConstraintsViolationException;

    void delete(String id) throws EntityNotFoundException, ConstraintsViolationException;

//    void addQuestion(Integer questionId, Integer pollId) throws ConstraintsViolationException, EntityNotFoundException;
//
//    void deleteQuestion(Integer questionId, Integer pollId) throws ConstraintsViolationException, EntityNotFoundException;

    Iterable<Poll> findAll();
}
