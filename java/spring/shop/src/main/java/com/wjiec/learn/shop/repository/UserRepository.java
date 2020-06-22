package com.wjiec.learn.shop.repository;

import com.wjiec.learn.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

}
