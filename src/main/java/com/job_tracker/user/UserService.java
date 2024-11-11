package com.job_tracker.user;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getAuthenticatedUser();

    UserDTO createUser(User user);

    List<UserDTO> getAllUsers(int pageNumber);

    UserDTO getUserDetails();

    Long countTotalUsers();
}