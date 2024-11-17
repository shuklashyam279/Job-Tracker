package com.job_tracker.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getAuthenticatedUser();

    UserDTO createUser(User user);

    List<UserDTO> getAllUsers(int pageNumber);

    UserDTO getUserDetails();

    Long countTotalUsers();
}