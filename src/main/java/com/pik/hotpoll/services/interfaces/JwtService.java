package com.pik.hotpoll.services.interfaces;

import com.pik.hotpoll.domain.User;

import java.util.Optional;

public interface JwtService {
    String toToken(User user);

    Optional<String> getSubFromToken(String token);
}
