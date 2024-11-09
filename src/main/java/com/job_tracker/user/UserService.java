package com.job_tracker.user;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User getAuthenticatedUser();

    public UserDTO createUser(User user);

    public List<UserDTO> getAllUsers();

    public UserDTO getUserDetails();

    public Long countTotalUsers();
}