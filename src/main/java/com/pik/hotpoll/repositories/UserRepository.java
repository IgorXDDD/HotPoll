package com.pik.hotpoll.repositories;

import java.util.List;

import com.pik.hotpoll.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people_2", path = "people_2")
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByLastName(@Param("name") String name);

}