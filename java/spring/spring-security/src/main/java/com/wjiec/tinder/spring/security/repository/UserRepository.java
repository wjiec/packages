package com.wjiec.tinder.spring.security.repository;

import com.wjiec.tinder.spring.security.mode.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

}
