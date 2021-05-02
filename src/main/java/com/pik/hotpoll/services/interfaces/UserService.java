package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    User findByEmail(String email);

    User findById(String id);

    User create(String username, String password, String email) throws ConstraintsViolationException;

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
