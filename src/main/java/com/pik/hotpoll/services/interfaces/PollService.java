package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.Vote;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;

import javax.persistence.EntityNotFoundException;

import com.querydsl.core.types.Predicate;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;


public interface PollService {

    Poll find(String id) throws EntityNotFoundException;

    Poll create(Poll poll, User user) throws ConstraintsViolationException;

    void delete(String id) throws EntityNotFoundException, ConstraintsViolationException;

    Poll addVote(Vote vote) throws EntityNotFoundException;

    Iterable<Poll> findAll(int page, int size, Boolean newest);

    Iterable<Poll> findByTags(List<String> tags, int page, int size, Boolean newest);
    Iterable<Poll> findByName(String name, int page, int size, Boolean newest);

    List<Poll> search(Predicate p);

    Iterable<Poll> findByUsername(String username, int page, int size, Boolean newest);
}
