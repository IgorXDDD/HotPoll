package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findByEmail(String email);

    Optional<User> findById(String id);

    User find(String id) throws EntityNotFoundException;

    User create(User user) throws ConstraintsViolationException;

    void delete(String id) throws EntityNotFoundException, ConstraintsViolationException;

    Iterable<User> findAll();
}
