package com.wjiec.tinder.spring.security.service;

import com.wjiec.tinder.spring.security.dto.UserDTO;
import com.wjiec.tinder.spring.security.exception.UserAlreadyExistsException;
import com.wjiec.tinder.spring.security.mode.User;
import com.wjiec.tinder.spring.security.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User registerNewUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if (repository.findByEmail(userDTO.getEmail()) != null) {
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        repository.save(user);

        return user;
    }

    public User loadUser(String identifier) {
        User user = repository.findByUsername(identifier);
        if (user == null) {
            user = repository.findByEmail(identifier);
        }

        return user;
    }

}
