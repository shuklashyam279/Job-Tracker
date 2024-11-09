package com.job_tracker.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public UserDTO createUser(User user) {
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole("user");
        userRepository.save(newUser);
        return UserMapper.INSTANCE.toDTO(newUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    public UserDTO getUserDetails() {
        Optional<User> optionalUser = userRepository.findById(
                getAuthenticatedUser().getId()
        );
        User user = optionalUser.orElseThrow(() ->
                new IllegalArgumentException(
                        "User Not Found with ID: " + getAuthenticatedUser().getId()
                )
        );
        return UserMapper.INSTANCE.toDTO(user);
    }

    public Long countTotalUsers() {
        return userRepository.count();
    }

}