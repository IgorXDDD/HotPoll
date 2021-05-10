package com.pik.hotpoll.services;
import com.pik.hotpoll.config.UserDetailsImpl;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("DefaultUserDetailsService")
public class DefaultUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByNickname(username).get(0);

        return UserDetailsImpl.build(user);
    }

}

