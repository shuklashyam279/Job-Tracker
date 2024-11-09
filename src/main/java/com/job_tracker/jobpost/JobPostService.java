package com.job_tracker.jobpost;

import com.job_tracker.dashboard.TopPerformerDTO;
import com.job_tracker.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface JobPostService {
    public User getUser();

    List<JobPostDTO> allJobPosts();

    ResponseEntity<String> createJobPosts(JobPost jobPost);

    ResponseEntity<JobPostDTO> setResume(JobPost jobPost, UUID resumeId);

    ResponseEntity<String> deleteUsersJobPost(UUID jobPostId);

    ResponseEntity<List<JobPostDTO>> retrieveUserJobPosts();

    ResponseEntity<Integer> countUserJobPosts();

    JobPost retrieveUserJobPostWithId(UUID jobPostId);

    ResponseEntity<String> updateJobPost(JobPost jobPost);

    ResponseEntity<String> addJobWithJobId(UUID jobPostid);

    boolean checkJobPostInUserJobList(UUID jobPostId);

    List<Object[]> retrieveUsersPerDayJobPosts();

    ResponseEntity<List<TopPerformerDTO>> retrieveTopPerformersOfTheDay();

    ResponseEntity<List<JobPostDTO>> retrieveJobsByFilters(
            String jobTitle,
            String companyName,
            String jobDescription,
            LocalDate jobDate,
            JobStatusEnum status
    );

    ResponseEntity<List<JobPostDTO>> retrieveJobPostsContainingString(
            String string
    );

    ResponseEntity<List<JobPostDTO>> retrieveUserJobPostsContainingString(
            String string
    );

    ResponseEntity<List<Object[]>> retrieveJobCountsPerDay();
}