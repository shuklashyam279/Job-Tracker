package com.job_tracker.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.job_tracker.jwtAuthentication.JwtHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtHelper jwtHelper;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(roles = "user")
    void testCountTotalUsers() throws Exception {
        when(userService.countTotalUsers()).thenReturn(1L);
        mockMvc.perform(get("/v1/dashboard/count-total-users")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "user")
    @DirtiesContext
    void testCreateUser() throws JsonProcessingException, Exception {
        UserDTO userDTO = UserDTO
                .builder()
                .email("shyamshukla@gmail.com")
                .fullName("Shyam Shukla")
                .build();
        when(userService.createUser(Mockito.any())).thenReturn(userDTO);

        mockMvc
                .perform(
                        post("/sign-up/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(userDTO))
                )
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "user")
    void testGetAllUsers() throws Exception {
        List<UserDTO> users = new ArrayList<>();
        when(userService.getAllUsers(1)).thenReturn(users);
        mockMvc
                .perform(get("/all-users").param("pageNumber", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserDetails() throws Exception {
        UserDTO userDTO = new UserDTO();
        when(userService.getUserDetails()).thenReturn(userDTO);
        mockMvc.perform(get("/v1/user-detail")).andExpect(status().isOk());
    }
}