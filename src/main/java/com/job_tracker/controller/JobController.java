package com.job_tracker.controller;

import com.job_tracker.dto.JobPostDTO;
import com.job_tracker.entity.JobPost;
import com.job_tracker.service.JobPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class JobController {
    @Autowired
    private JobPostService jobPostServices;

    // =============================Retrieve All Job Posts==========================================
    @GetMapping("/v1/all-jobs")
    public List<JobPostDTO> allJobPosts() {
        return jobPostServices.allJobPosts();
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
        return jobPostServices.createJobPosts(jobPost);
    }

    // =============================Set Resume to User's Job Post==========================================
    @PutMapping("/v1/set-resume")
    public ResponseEntity<JobPostDTO> setResume(@RequestBody JobPost jobPost, @RequestParam UUID resumeID) {
        return jobPostServices.setResume(jobPost, resumeID);
    }

    // ================================Delete User's Job Post=======================================
    @DeleteMapping("/v1/delete-job-post")
    public ResponseEntity<String> deleteUsersJobPost(@RequestParam UUID jobPostId) {
        return jobPostServices.deleteUsersJobPost(jobPostId);
    }

    // ===============================Retrieve User's Job Posts========================================
    @GetMapping("/v1/retrieve-user-job-posts")
    public ResponseEntity<List<JobPost>> retrieveUserJobPosts() {
        return jobPostServices.retrieveUserJobPosts();
    }

    // ============================Count User's Job Posts=============================================
    @GetMapping("/v1/count-user-job-posts")
    public ResponseEntity<Integer> countUserJobPosts(){
        return jobPostServices.countUserJobPosts();
    }

    // ============================Retrieve User's Job Post with Job-post Id==========================
    @GetMapping("/v1/retrieve-job-post-with-user-id")
    public JobPost retrieveJobPostWithUserId(UUID jobPostId){
        return jobPostServices.retrieveUserJobPostWithId(jobPostId);
    }

    // ============================Update Job Post Details============================================
    @PutMapping("/v1/update-job-post")
    public ResponseEntity<String> updateJobPost(@RequestBody JobPost jobPost){
        return jobPostServices.updateJobPost(jobPost);
    }

    // ========================Add Job Posts From Dashboard to User's Account=========================
    @PostMapping("/v1/add-job-with-job-id")
    public ResponseEntity<String> addJobWithJobId(@RequestParam UUID jobPostId){
        return jobPostServices.addJobWithJobId(jobPostId);
    }

    // =========================Retrieve User's Job Posts Count Of Each Day==============================
    @GetMapping("/v1/retrieve-users-per-day-jobposts")
    public List<Object[]> retrieveUsersPerDayJobPosts() {
        return jobPostServices.retrieveUsersPerDayJobPosts();
    }
}