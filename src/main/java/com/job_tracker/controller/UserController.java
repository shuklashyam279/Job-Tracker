package com.job_tracker.controller;

import java.util.List;

import com.job_tracker.dto.UserDTO;
import com.job_tracker.entity.User;
import com.job_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userServices;

    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return userServices.getAllUsers();
    }

    @PostMapping("/sign-up/")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user){
        return userServices.createUser(user);
    }

    @GetMapping("/v1/user-detail")
    public UserDTO getUserDetails(){
        return userServices.getUserDetails();
    }

    @GetMapping("/v1/dashboard/count-total-users")
    public ResponseEntity<Long> countTotalUsers() {
        return userServices.countTotalUsers();
    }
}