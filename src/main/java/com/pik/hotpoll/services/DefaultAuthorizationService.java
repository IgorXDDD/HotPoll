package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.services.interfaces.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthorizationService implements AuthorizationService {
    @Override
    public boolean canCreatePoll(User user) {
        return false;
    }
}
