package com.job_tracker.userClass;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

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
}