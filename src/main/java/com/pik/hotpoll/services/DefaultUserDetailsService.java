package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    DefaultUserDetailsService(){
        this.userRepository =userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> userL = userRepository.findByUsername(s);
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        User user = userL.get(0);
        grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getAuthority()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthoritySet);
    }
}
