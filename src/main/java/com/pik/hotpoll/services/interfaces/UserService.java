package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);

    List<Object> findById(String id);
}
