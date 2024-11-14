package com.job_tracker.jobPost;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.job_tracker.jobpost.JobPostRepository;
import com.job_tracker.jobpost.JobStatusEnum;
import com.job_tracker.resume.Resume;
import com.job_tracker.user.User;
import com.job_tracker.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
public class JobPostRepositoryTest {

    @Autowired
    private JobPostRepository jobPostRepository;

    private User user;
    private List<Resume> resumes;
    private PageRequest pageable;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user =
                User
                        .builder()
                        .email("shyamshukla@gmail.com")
                        .fullName("Shyam Shukla")
                        .password("12345678")
                        .build();
        userRepository.save(user);
        resumes = new ArrayList<>();
        pageable = PageRequest.of(0, 7);
    }

    @Test
    void testCountByUser() {
        assertThat(jobPostRepository.countByUser(user)).isNotNull();
    }

    @Test
    void testCountUsersPostPerDay() {
        assertThat(jobPostRepository.findJobCountPerDay(pageable)).isNotNull();
    }

    @Test
    void testExistsByUser() {
        assertThat(jobPostRepository.existsByUser(user)).isNotNull();
    }

    @Test
    void testFindByUser() {
        assertThat(jobPostRepository.findByUser(user, pageable)).isNotNull();
    }

    @Test
    void testFindJobCountPerDay() {
        assertThat(jobPostRepository.findJobCountPerDay(pageable)).isNotNull();
    }

    @Test
    void testFindJobPostByFilters() {
        assertThat(jobPostRepository.findJobPostByFilters("", "", "", LocalDate.now(), JobStatusEnum.APPLIED)).isNotNull();
    }

    @Test
    void testFindJobPostContainingString() {
        assertThat(jobPostRepository.findJobPostContainingString("", pageable)).isNotNull();
    }

    @Test
    void testFindUserJobPostContainingString() {
        assertThat(jobPostRepository.findUserJobPostContainingString(user, "")).isNotNull();
    }

    @Test
    void testTopPerformersOfTheDay() {
        assertThat(jobPostRepository.topPerformersOfTheDay(LocalDate.now())).isNotNull();
    }
}