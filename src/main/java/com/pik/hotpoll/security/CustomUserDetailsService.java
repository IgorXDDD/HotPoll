package com.pik.hotpoll.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.domain.UserDTO;
import com.pik.hotpoll.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO user =  objectMapper.convertValue(userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                ), UserDTO.class);

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        ObjectMapper objectMapper = new ObjectMapper();
        Optional<User> user = userRepository.findById(id.toString());
        if(!user.isPresent()){
            throw new ResourceNotFoundException();
        }

        return UserPrincipal.create(objectMapper.convertValue(user.get(), UserDTO.class));
    }
}
