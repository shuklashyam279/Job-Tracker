package com.job_tracker.jobpost;

import com.job_tracker.dashboard.TopPerformerDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface JobPostService {

    List<JobPostDTO> allJobPosts(int pageNumber);

    String createJobPosts(JobPost jobPost);

    JobPostDTO setResume(JobPost jobPost, UUID resumeId);

    String deleteUsersJobPost(UUID jobPostId);

    List<JobPostDTO> retrieveUserJobPosts(int pageNumber);

    Integer countUserJobPosts();

    JobPost retrieveUserJobPostWithId(UUID jobPostId);

    String updateJobPost(JobPost jobPost);

    String addJobWithJobId(UUID jobPostId);

    List<Object[]> retrieveUsersPerDayJobPosts(int pageNumber);

    List<TopPerformerDTO> retrieveTopPerformersOfTheDay();

    List<JobPostDTO> retrieveJobsByFilters(
            String jobTitle,
            String companyName,
            String jobDescription,
            LocalDate jobDate,
            JobStatusEnum status
    );

    List<JobPostDTO> retrieveJobPostsContainingString(String string, int pageNumber);

    List<JobPostDTO> retrieveUserJobPostsContainingString(String string);

    List<Object[]> retrieveJobCountsPerDay(int pageNumber);
}