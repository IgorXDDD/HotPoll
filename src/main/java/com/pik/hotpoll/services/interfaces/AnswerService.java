package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.Answer;
import com.pik.hotpoll.domain.Question;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;

import javax.persistence.EntityNotFoundException;

public interface AnswerService {

    Answer find(Integer answerId) throws EntityNotFoundException;

    Answer create(Answer answer) throws ConstraintsViolationException;

    void delete(Integer id) throws EntityNotFoundException, ConstraintsViolationException;

    Iterable<Answer> findAll();
}
