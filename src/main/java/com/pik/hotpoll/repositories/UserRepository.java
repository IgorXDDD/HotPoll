package com.pik.hotpoll.repositories;

import java.util.List;
import java.util.Optional;

import com.pik.hotpoll.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

//@RepositoryRestResource(collectionResourceRel = "people_2", path = "people_2")
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByUsername(@Param("username") String name);
    Optional<User> findById(@Param("id") String id);

    Optional<User> findByEmail(@Param("email") String email);
    Boolean existsByEmail(@Param("email") String email);
    Boolean existsByUsername(@Param("username") String username);
}

