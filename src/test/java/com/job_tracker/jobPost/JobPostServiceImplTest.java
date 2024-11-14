package com.job_tracker.jobPost;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.job_tracker.jobpost.*;
import com.job_tracker.jwtAuthentication.JwtHelper;
import com.job_tracker.user.User;
import com.job_tracker.user.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
public class JobPostServiceImplTest {

    @Mock
    private JobPostRepository jobPostRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JobPostService jobPostService;

    @MockBean
    private JwtHelper jwtHelper;

    private JobPost jobPost;
    private List<JobPostDTO> jobPosts;
    private UUID jobPostId;
    private String jobPostIdString;
    private Integer pageNumber;
    private Optional<JobPost> optionalJobPost;
    private User user;
    private Sort sort;
    private Pageable pageable;
    private Pageable pageable2;
    private Integer pageSize;
    Page<JobPost> page;
    Page<Object[]> page2;
    List<Object[]> objectList;

    @BeforeEach
    void setUp() {
        jobPost =
                JobPost
                        .builder()
                        .id(UUID.randomUUID())
                        .companyName("abcd")
                        .jobDate(LocalDate.now())
                        .jobTitle("abcd")
                        .jobLink("abcd")
                        .status(JobStatusEnum.APPLIED)
                        .build();
        jobPosts = new ArrayList<>();
        pageable = PageRequest.of(0, 7);

        jobPostId = UUID.randomUUID();
        jobPostIdString = jobPostId.toString();
        pageNumber = 0;
        optionalJobPost = Optional.ofNullable(new JobPost());
        user =
                User
                        .builder()
                        .email("shyamshukla@gmail.com")
                        .fullName("Shyam Shukla")
                        .password("12345678")
                        .build();
        sort = Sort.by("jobDate").descending();
        pageSize = 7;
        pageable = PageRequest.of(pageNumber, pageSize, sort);
        pageable2 = PageRequest.of(pageNumber, 50, sort);
        page = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        page2 = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);
        objectList = new ArrayList<>();
    }

    @Test
    @WithMockUser
    void testAddJobWithJobId() {
        when(jobPostRepository.findById(jobPostId)).thenReturn(optionalJobPost);
        when(jobPostRepository.save(Mockito.any(JobPost.class)))
                .thenReturn(Mockito.any(JobPost.class));
        assertThat(jobPostService.addJobWithJobId(jobPostId)).isNotNull();
    }

    @Test
    void testAllJobPosts() {
        when(jobPostRepository.findAll(pageable2)).thenReturn(page);
        assertThat(jobPostService.allJobPosts(pageNumber)).isNotNull();
    }

    @Test
    @WithMockUser
    void testCountUserJobPosts() {
        when(userService.getAuthenticatedUser()).thenReturn(user);

        when(jobPostRepository.countByUser(user)).thenReturn(7);
        assertThat(jobPostService.countUserJobPosts()).isNotNull();
    }

    @Test
    @WithMockUser
    void testCreateJobPosts() {
        when(userService.getAuthenticatedUser()).thenReturn(user);

        when(jobPostRepository.save(Mockito.any(JobPost.class)))
                .thenReturn(jobPost);
        assertThat(jobPostService.createJobPosts(jobPost)).isNotNull();
    }

    @Test
    void testDeleteUsersJobPost() {
        when(userService.getAuthenticatedUser()).thenReturn(user);
        Mockito.doNothing().when(jobPostRepository).deleteById(jobPostId);
        when(jobPostRepository.findById(jobPostId)).thenReturn(optionalJobPost);
        assertThat(jobPostService.deleteUsersJobPost(jobPostId)).isNotNull();
    }

    @Test
    void testRetrieveJobCountsPerDay() {
        when(jobPostRepository.findJobCountPerDay(pageable)).thenReturn(page2);
        assertThat(jobPostService.retrieveJobCountsPerDay(pageNumber)).isNotNull();
    }

    @Test
    void testRetrieveJobsByFilters() {}

    @Test
    void testRetrieveTopPerformersOfTheDay() {
        when(jobPostRepository.topPerformersOfTheDay(LocalDate.now()))
                .thenReturn(objectList);
        assertThat(jobPostService.retrieveTopPerformersOfTheDay()).isNotNull();
    }

    @Test
    @WithMockUser
    void testRetrieveUserJobPostWithId() {
        when(jobPostRepository.findById(jobPostId)).thenReturn(optionalJobPost);
        assertThat(jobPostService.retrieveUserJobPostWithId(jobPostId)).isNotNull();
    }

    @Test
    @WithMockUser
    void testRetrieveUserJobPosts() {
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(jobPostRepository.findByUser(user, pageable2)).thenReturn(page);
        assertThat(jobPostService.retrieveUserJobPosts(pageNumber)).isNotNull();
    }

    @Test
    @WithMockUser
    void testRetrieveUsersPerDayJobPosts() {
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(jobPostRepository.countUsersPostPerDay(user, pageable)).thenReturn(page2);
        assertThat(jobPostService.retrieveUsersPerDayJobPosts(pageNumber)).isNotNull();
    }

    @Test
    void testRetrieveJobPostsContainingString() {
        when(jobPostRepository.findJobPostContainingString("", pageable2))
                .thenReturn(page);
        assertThat(jobPostService.retrieveJobPostsContainingString("", pageNumber))
                .isNotNull();
    }

    @Test
    @WithMockUser
    void testRetrieveUserJobPostsContainingString() {
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(jobPostRepository.findUserJobPostContainingString(user, "")).thenReturn(page);
        //when(jobPostRepository.findUserJobPostContainingString(user, "", pageable2)).thenReturn(page);
        assertThat(jobPostService.retrieveUserJobPostsContainingString("")).isNotNull();
        //assertThat(jobPostService.retrieveUserJobPostsContainingString("", pageNumber)).isNotNull();
    }

    @Test
    @WithMockUser
    void testSetResume() {}

    @Test
    void testUpdateJobPost() {
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(jobPostRepository.findById(jobPost.getId()))
                .thenReturn(optionalJobPost);
        when(jobPostRepository.save(jobPost)).thenReturn(jobPost);
        assertThat(jobPostService.updateJobPost(jobPost)).isNotNull();
    }
}