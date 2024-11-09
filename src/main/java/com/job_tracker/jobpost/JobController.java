package com.job_tracker.jobpost;

import com.job_tracker.dashboard.TopPerformerDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class JobController {
    @Autowired
    private JobPostServiceImpl jobPostServicesImpl;

    // =============================Retrieve All Job Posts==========================================
    @GetMapping("/v1/dashboard/all-jobs")
    public List<JobPostDTO> allJobPosts() {
        return jobPostServicesImpl.allJobPosts();
    }

    // =============================Add User's Job Post==========================================
    @PostMapping("/v1/add-job")
    public ResponseEntity<String> addJob(@Valid @RequestBody JobPost jobPost,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMessage.toString());
        }
        return jobPostServicesImpl.createJobPosts(jobPost);
    }

    // =============================Set Resume to User's Job Post==========================================
    @PutMapping("/v1/set-resume")
    public ResponseEntity<JobPostDTO> setResume(@RequestBody JobPost jobPost, @RequestParam UUID resumeID) {
        return jobPostServicesImpl.setResume(jobPost, resumeID);
    }

    // ================================Delete User's Job Post=======================================
    @DeleteMapping("/v1/delete-job-post")
    public ResponseEntity<String> deleteUsersJobPost(@RequestParam UUID jobPostId) {
        return jobPostServicesImpl.deleteUsersJobPost(jobPostId);
    }

    // ===============================Retrieve User's Job Posts========================================
    @GetMapping("/v1/retrieve-user-job-posts")
    public ResponseEntity<List<JobPostDTO>> retrieveUserJobPosts() {
        return jobPostServicesImpl.retrieveUserJobPosts();
    }

    // ============================Count User's Job Posts=============================================
    @GetMapping("/v1/count-user-job-posts")
    public ResponseEntity<Integer> countUserJobPosts(){
        return jobPostServicesImpl.countUserJobPosts();
    }

    // ============================Retrieve User's Job Post with Job-post Id==========================
    @GetMapping("/v1/retrieve-job-post-with-user-id")
    public JobPost retrieveJobPostWithUserId(UUID jobPostId){
        return jobPostServicesImpl.retrieveUserJobPostWithId(jobPostId);
    }

    // ============================Update Job Post Details============================================
    @PutMapping("/v1/update-job-post")
    public ResponseEntity<String> updateJobPost(@RequestBody JobPost jobPost){
        return jobPostServicesImpl.updateJobPost(jobPost);
    }

    // ========================Add Job Posts From Dashboard to User's Account=========================
    @PostMapping("/v1/add-job-with-job-id")
    public ResponseEntity<String> addJobWithJobId(@RequestParam UUID jobPostId){
        return jobPostServicesImpl.addJobWithJobId(jobPostId);
    }

    // =========================Retrieve User's Job Posts Count Of Each Day==============================
    @GetMapping("/v1/dashboard/retrieve-jobpost-count-per-day")
    public List<Object[]> retrieveUsersPerDayJobPosts() {
        return jobPostServicesImpl.retrieveUsersPerDayJobPosts();
    }

    // ====================================Top 3 Performer's of the day with their Job Counts ===========================
    @GetMapping("/v1/dashboard/top-three-performer-of-the-day-with-their-job-count")
    public ResponseEntity<List<TopPerformerDTO>> topPerformersOfTheDay() {
        return jobPostServicesImpl.retrieveTopPerformersOfTheDay();
    }

    // =============================Retrieve JobPosts with Filters Applied===================================
    @GetMapping("/v1/retrieve-job-posts-with-filters-applied")
    public ResponseEntity<List<JobPostDTO>> retrieveJobPostsWithFiltersApplied(
            @RequestParam String jobTitle,
            @RequestParam String companyName,
            @RequestParam String jobDescription,
            @RequestParam LocalDate jobDate,
            @RequestParam JobStatusEnum status
    ) {
        return jobPostServicesImpl.retrieveJobsByFilters(
                jobTitle,
                companyName,
                jobDescription,
                jobDate,
                status
        );
    }

    // ========================================Dashboard Search Functionality=============================================
    @GetMapping("/v1/dashboard/search-jobposts-containing-strings")
    public ResponseEntity<List<JobPostDTO>> retrieveJobPostsWithString(@RequestParam String string){
        return jobPostServicesImpl.retrieveJobPostsContainingString(string);
    }

    // ======================================Search Box For User Component===================================
    @GetMapping("/v1/search-user-jobposts-containing-string")
    public ResponseEntity<List<JobPostDTO>> retrieveUserJobPostsContainingString(@RequestParam String string) {
        return jobPostServicesImpl.retrieveUserJobPostsContainingString(string);
    }

    // =======================================Retrieve Job Post Count Per Day================================
    @GetMapping("/v1/retrieve-jobpost-count-per-day")
    public ResponseEntity<List<Object[]>> retrieveJobCountsPerDay() {
        return jobPostServicesImpl.retrieveJobCountsPerDay();
    }
}