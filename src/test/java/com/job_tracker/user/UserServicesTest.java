package com.job_tracker.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;


@ExtendWith(MockitoExtension.class)
public class UserServicesTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @BeforeEach
    @WithMockUser(roles = "user")
    void setUp(){
        User user = User
                .builder()
                .email("shyamshukla@gmail.com")
                .fullName("Shyam Shukla")
                .password("12345678")
                .build();
    }

    @Test
    void testCountTotalUsers() {
        Long totalUsers = 2L;
        when(userRepository.count()).thenReturn(totalUsers);
        Long actualResult = userService.countTotalUsers();
        assertThat(actualResult).isNotNull();
    }

    @Test
    void testCreateUser() {
        User user = User
                .builder()
                .email("shyamshukla279@gmail.com")
                .fullName("Shyam Shukla")
                .password("12345678")
                .build();

        UserDTO actualUserDTO = UserMapper.INSTANCE.toDTO(user);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        UserDTO savedUserDTO = userService.createUser(user);
        assertThat(savedUserDTO).isNotNull();
    }

    @Test
    void testGetAllUsers() {
        List<User> usersList = new ArrayList<>();
        Sort sort = Sort.by("fullName");
        Pageable pageable = PageRequest.of(0, 50, sort);
        Page<User> usersPage2 = Mockito.mock(Page.class);
        when(userRepository.findAll(pageable)).thenReturn(usersPage2);

        List<UserDTO> actualList = userService.getAllUsers(0);
        assertThat(actualList).isEqualTo(usersList);
    }

    @Test
    void testGetAuthenticatedUser() {}

    @Test
    void testGetUserDetails() {}
}