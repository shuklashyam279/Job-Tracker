package com.job_tracker.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class UserServicesTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCountTotalUsers() {
        // Arrange
        Long totalUsers = 2L;
        when(userRepository.count()).thenReturn(totalUsers);

        // Act
        Long actualResult = userService.countTotalUsers();

        // Assert
        assertThat(actualResult).isEqualTo(totalUsers);

    }

    @Test
    void testCreateUser() {
        // Arrange
        User user = User
                .builder()
                .email("shyamshukla279@gmail.com")
                .fullName("Shyam Shukla")
                .password("12345678")
                .build();

        UserDTO actualUserDTO = UserMapper.INSTANCE.toDTO(user);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Act
        // UserDTO savedUserDTO = userServices.createUser(user);

        // Assert
        // assertThat(savedUserDTO).isNotNull();
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> usersList = new ArrayList<>();
        Sort sort = Sort.by("fullName");
        Pageable pageable = PageRequest.of(0, 50, sort);
        Page<User> usersPage2 = Mockito.mock(Page.class);
        when(userRepository.findAll(pageable)).thenReturn(usersPage2);

        // Act
        List<UserDTO> actualList = userService.getAllUsers(0);

        // Assert
        assertThat(actualList).isEqualTo(usersList);

    }

    @Test
    void testGetAuthenticatedUser() {}

    @Test
    void testGetUserDetails() {}
}