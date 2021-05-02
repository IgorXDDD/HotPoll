package com.pik.hotpoll.services;

import com.pik.hotpoll.domain.User;
import com.pik.hotpoll.exceptions.ConstraintsViolationException;
import com.pik.hotpoll.repositories.UserRepository;
import com.pik.hotpoll.services.interfaces.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultUserService.class);
    private final UserRepository userRepository;


    @Autowired
    public DefaultUserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User find(String id) throws EntityNotFoundException {
        return findUserChecked(id);
    }


    public User create(User user) throws ConstraintsViolationException {
        User userNew;
        try {
            userNew = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return userNew;
    }

    public void delete(String id) throws EntityNotFoundException, ConstraintsViolationException {
        User user = findUserChecked(id);
        userRepository.delete(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    private User findUserChecked(String id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new EntityNotFoundException("poll not found");
        } else {
            return user.get();
        }

    }

}
