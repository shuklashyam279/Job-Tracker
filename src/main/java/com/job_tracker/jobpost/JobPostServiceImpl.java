package com.job_tracker.jobpost;

import com.job_tracker.dashboard.TopPerformerDTO;
import com.job_tracker.resume.Resume;
import com.job_tracker.resume.ResumeRepository;
import com.job_tracker.user.User;
import com.job_tracker.user.UserDTO;
import com.job_tracker.user.UserMapper;
import com.job_tracker.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JobPostServiceImpl implements JobPostService {

    public static final String JOB_DATE = "jobDate";
    private final JobPostRepository jobPostRepository;
    private final ResumeRepository resumeRepository;
    private final UserService userService;

    @Autowired
    public JobPostServiceImpl(JobPostRepository jobPostRepository,
                              ResumeRepository resumeRepository,
                              UserService userService) {
        this.jobPostRepository = jobPostRepository;
        this.resumeRepository = resumeRepository;
        this.userService = userService;
    }

    // ==========================Retrieve All JobPosts===========================================

    public List<JobPostDTO> allJobPosts(int pageNumber) {
        Sort sort = Sort.by(JOB_DATE).descending();
        Pageable pageable = PageRequest.of(pageNumber, 50, sort);
        Page<JobPost> page = jobPostRepository.findAll(pageable);
        List<JobPostDTO> jobPosts = page
                .stream()
                .map(JobPostMapper.INSTANCE::toDTO)
                .filter(jobPost -> !jobPost.getClone())
                .collect(Collectors.toList());
        if (jobPosts.isEmpty()) {
            return Collections.emptyList();
        } else if (jobPosts.size() < 50 && page.hasNext()) {
            Pageable remainingPageable = PageRequest.of(1, 50 - jobPosts.size(), sort);
            List<JobPostDTO> remainingJobPosts = jobPostRepository.findAll(remainingPageable)
                    .stream()
                    .map(JobPostMapper.INSTANCE::toDTO)
                    .filter(jobPost -> !jobPost.getClone())
                    .toList();
            jobPosts.addAll(remainingJobPosts);
        }
        return jobPosts;
    }

    // =============================Create Job Post=====================================

    public String createJobPosts(JobPost jobPost) {
        User user = userService.getAuthenticatedUser();
        jobPost.setUser(user);
        jobPost.setClone(false);
        if (jobPost.getJobDate() == null) {
            jobPost.setJobDate(LocalDate.now());
        }
        jobPostRepository.save(jobPost);
        return "Job post created Successfully";
    }

    // =============================Set Resume to Job Post======================================
    public JobPostDTO setResume(JobPost jobPost, UUID resumeId) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        Resume resume = optionalResume.orElseThrow(() -> new IllegalArgumentException("Resume Does Not Exist!!"));
        jobPost.setResume(resume);
        jobPostRepository.save(jobPost);
        return JobPostMapper.INSTANCE.toDTO(jobPost);
    }

    // =================================Delete User's Job Post======================================
    public String deleteUsersJobPost(UUID jobPostId) {
        if (!jobPostRepository.existsByUserAndId(userService.getAuthenticatedUser(), jobPostId)) {
            throw new IllegalArgumentException("JobPost does Not Exist with Id: " + jobPostId);
        }
        jobPostRepository.deleteById(jobPostId);
        return "Job post deleted successfully.";
    }

    // =============================Retrieve User's Job Posts=================================
    public List<JobPostDTO> retrieveUserJobPosts(int pageNumber) {
        Sort sort = Sort.by(JOB_DATE).descending();
        Pageable pageable = PageRequest.of(pageNumber, 50, sort);
        Page<JobPost> page = jobPostRepository.findByUser(userService.getAuthenticatedUser(), pageable);
        List<JobPostDTO> jobPosts = page
                .getContent()
                .stream()
                .map(JobPostMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());

        if (jobPosts.isEmpty()) {
            return new ArrayList<>();
        } else if (page.hasNext()) {
            return jobPosts;
        } else {
            List<JobPostDTO> newJobPosts = page
                    .getContent()
                    .stream()
                    .map(JobPostMapper.INSTANCE::toDTO)
                    .toList();
            jobPosts.addAll(newJobPosts);
            return jobPosts;
        }
    }

    // ==============================Count User Job Posts=====================================
    public Integer countUserJobPosts() {
        User user = userService.getAuthenticatedUser();
        return jobPostRepository.countByUser(user);
    }

    // =========================Retrieve User JobPost With ID===============================
    public JobPost retrieveUserJobPostWithId(UUID jobPostId) {
        Optional<JobPost> optionalJobPost = jobPostRepository.findById(jobPostId);
        return optionalJobPost.orElseThrow(() ->
                new IllegalArgumentException("No job post found with id: " + jobPostId)
        );
    }

    // ============================Update Job Posts=============================
    public String updateJobPost(JobPost jobPost) {
        Optional<JobPost> optionalJobPost = jobPostRepository.findById(jobPost.getId());
        JobPost existingJobPost = optionalJobPost.orElseThrow(() ->
                new IllegalArgumentException(
                        "Job post does not exist with id: " + jobPost.getId()));
        if (jobPost.getJobTitle() != null) {
            existingJobPost.setJobTitle(jobPost.getJobTitle());
        }
        if (jobPost.getCompanyName() != null) {
            existingJobPost.setCompanyName(jobPost.getCompanyName());
        }
        if (jobPost.getJobDescription() != null) {
            existingJobPost.setJobDescription(jobPost.getJobDescription());
        }
        if (jobPost.getJobLink() != null) {
            existingJobPost.setJobLink(jobPost.getJobLink());
        }
        if (jobPost.getStatus() != null) {
            existingJobPost.setStatus(jobPost.getStatus());
        }
        if (jobPost.getJobDate() != null) {
            existingJobPost.setJobDate(jobPost.getJobDate());
        }
        try {
            jobPostRepository.save(existingJobPost);
        } catch (Exception e) {
            return "Failed to update due to: " + e.getMessage();
        }
        return "Job post updated successfully";
    }

    // =============================Clone Job Post==================================
    public String addJobWithJobId(UUID jobPostId) {
        Optional<JobPost> optionalJobPost = jobPostRepository.findById(jobPostId);
        JobPost oldJobPost = optionalJobPost.orElseThrow(() ->
                new IllegalArgumentException("Job does not exist with id: " + jobPostId));
        JobStatusEnum status = JobStatusEnum.BOOKMARKED;
        JobPost newJobPost = new JobPost();
        newJobPost.setUser(userService.getAuthenticatedUser());
        newJobPost.setJobTitle(oldJobPost.getJobTitle());
        newJobPost.setCompanyName(oldJobPost.getCompanyName());
        newJobPost.setJobDescription(oldJobPost.getJobDescription());
        newJobPost.setJobLink(oldJobPost.getJobLink());
        newJobPost.setClone(true);
        newJobPost.setStatus(status);
        newJobPost.setJobDate(LocalDate.now());
        jobPostRepository.save(newJobPost);
        return "Job post added successfully to your account.";
    }

    // ==========================Retrieve User's Job Posts per Day==================================
    public List<Object[]> retrieveUsersPerDayJobPosts(int pageNumber) {
        int pageSize = 7;
        Sort sort = Sort.by(JOB_DATE).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Object[]> page = jobPostRepository.countUsersPostPerDay(userService.getAuthenticatedUser(), pageable);
        List<Object[]> jobPostPerDay = new ArrayList<>(page.getContent());
        if (jobPostPerDay.isEmpty()) {
            return new ArrayList<>();
        } else {
            if (page.hasNext()) {
                return jobPostPerDay;
            } else {
                Page<Object[]> nextPage = jobPostRepository.countUsersPostPerDay(userService.getAuthenticatedUser(), pageable);
                jobPostPerDay.addAll(nextPage.getContent());
            }
        }
        return jobPostPerDay;
    }

    // ==========================Top 3 Performer's of the day with their Job Counts ===================
    public List<TopPerformerDTO> retrieveTopPerformersOfTheDay() {
        LocalDate date = LocalDate.now();
        List<Object[]> results = jobPostRepository.topPerformersOfTheDay(date);
        List<TopPerformerDTO> topPerformerDTOs = results
                .stream()
                .map(item -> {
                    User user = (User) item[0];
                    Long count = (Long) item[1];
                    UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);
                    TopPerformerDTO result = new TopPerformerDTO();
                    result.setFullName(userDTO.getFullName());
                    result.setJobPostCount(count);
                    return result;
                })
                .toList();
        log.info("Value of List Top Performers: " + topPerformerDTOs);
        return topPerformerDTOs;
    }

    // ==========================Retrieve Jobpost with Filters Applied==================================
    public List<JobPostDTO> retrieveJobsByFilters(
            String jobTitle,
            String companyName,
            String jobDescription,
            LocalDate jobDate,
            JobStatusEnum status
    ) {
        return jobPostRepository
                .findJobPostByFilters(jobTitle, companyName, jobDescription, jobDate, status)
                .stream()
                .map(JobPostMapper.INSTANCE::toDTO)
                .toList();
    }

    // ==================================Retrieve Job Posts Containing String==========================================
    public List<JobPostDTO> retrieveJobPostsContainingString(String string, int pageNumber) {
        Sort sort = Sort.by(JOB_DATE).descending();
        int pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<JobPost> page = jobPostRepository.findJobPostContainingString(string, pageable);
        List<JobPostDTO> jobPostDTOs = page.getContent().stream()
                .map(JobPostMapper.INSTANCE::toDTO)
                .filter(jobpost -> !jobpost.getClone())
                .collect(Collectors.toList());

        if (jobPostDTOs.isEmpty()) {
            return new ArrayList<>();
        } else if (page.hasNext()) {
            return jobPostDTOs;
        } else {
            List<JobPostDTO> newJobPostDTOs = page.getContent().stream()
                    .map(JobPostMapper.INSTANCE::toDTO)
                    .filter(jobpost -> !jobpost.getClone())
                    .toList();
            jobPostDTOs.addAll(newJobPostDTOs);
            return jobPostDTOs;
        }
    }

    // ===================================Retrieve User Job Posts Containing String======================================================
    public List<JobPostDTO> retrieveUserJobPostsContainingString(String string) {
        return
                jobPostRepository
                        .findUserJobPostContainingString(userService.getAuthenticatedUser(), string)
                        .stream()
                        .map(JobPostMapper.INSTANCE::toDTO)
                        .filter(jobpost -> !jobpost.getClone())
                        .toList();
    }

    // ===================================Retrieve Job Counts Per Day===============================
    public List<Object[]> retrieveJobCountsPerDay(int pageNumber) {
        int pageSize = 7;
        Sort sort = Sort.by(JOB_DATE).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Object[]> page = jobPostRepository.findJobCountPerDay(pageable);
        if (page.hasContent()) {
            return page.getContent();
        } else {
            // Check if there are more pages available
            if (page.hasNext()) {
                // Fetch the next page
                pageable = pageable.next();
                page = jobPostRepository.findJobCountPerDay(pageable);
                return page.getContent();
            } else {
                return new ArrayList<>();
            }
        }
    }
}