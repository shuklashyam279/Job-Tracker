package com.job_tracker.service;

import java.util.List;

import com.job_tracker.entity.User;
import com.job_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<User> createUser(User user) {
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}