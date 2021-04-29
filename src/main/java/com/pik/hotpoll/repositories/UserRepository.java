package com.pik.hotpoll.repositories;

import java.util.List;
import java.util.Optional;

import com.pik.hotpoll.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

//@RepositoryRestResource(collectionResourceRel = "people_2", path = "people_2")
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByNickname(@Param("nickname") String name);
    Optional<User> findById(@Param("id") String id);
}

