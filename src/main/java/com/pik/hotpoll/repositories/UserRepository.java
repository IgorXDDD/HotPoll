package com.pik.hotpoll.repositories;

import java.util.List;
import java.util.Optional;

import com.pik.hotpoll.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByNickname(@Param("nickname") String name);
    List<User> findByEmail(@Param("email") String email);
    Optional<User> findById(@Param("id") String id);
}
