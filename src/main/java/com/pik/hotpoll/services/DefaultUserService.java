package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.Poll;
import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.repositories.UserRepository;
import com.pik.hotpoll.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    @Autowired
    DefaultUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new EntityNotFoundException("poll not found");
        }else{
            return user.get();
        }
    }

    @Override
    public User findById(String id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new EntityNotFoundException("poll not found");
        }else{
            return user.get();
        }
    }

    @Override
    public User create(String username, String password, String email) throws ConstraintsViolationException {
        User userNew;
        try {
            userNew = userRepository.save(User.builder().username(username).password(password).email(email).build());
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintsViolationException(e.getMessage());
        }
        return userNew;
    }

    @Override
    public boolean existsByUsername(String username) {
        Boolean existsByUsername = userRepository.existsByUsername(username);
        if (!existsByUsername) {
            throw new EntityNotFoundException("poll not found");
        }else{
            return true;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        Boolean existsByUsername = userRepository.existsByEmail(email);
        if (!existsByUsername) {
            throw new EntityNotFoundException("poll not found");
        }else{
            return true;
        }
    }

}
