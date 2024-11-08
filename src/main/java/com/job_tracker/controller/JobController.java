package com.job_tracker.controller;

import com.job_tracker.dto.JobPostDTO;
import com.job_tracker.entity.JobPost;
import com.job_tracker.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class JobController {
    @Autowired
    private JobPostService jobPostServices;

    @GetMapping("/v1/all-jobs")
    public List<JobPostDTO> allJobPosts() {
        return jobPostServices.allJobPosts();
    }

    @PostMapping("/v1/add-job")
    public ResponseEntity<JobPostDTO> addJob(@RequestBody JobPost jobPost) {
        return jobPostServices.createJobPosts(jobPost);
    }

    @PutMapping("/v1/set-resume")
    public ResponseEntity<JobPostDTO> setResume(
            @RequestBody JobPost jobPost,
            @RequestParam UUID resumeID
    ) {
        return jobPostServices.setResume(jobPost, resumeID);
    }

    @DeleteMapping("/v1/delete-job-post")
    public ResponseEntity<String> deleteUsersJobPost(@RequestParam UUID jobPostId) {
        return jobPostServices.deleteUsersJobPost(jobPostId);
    }

    @GetMapping("/v1/retrieve-user-job-posts")
    public ResponseEntity<List<JobPost>> retrieveUserJobPosts() {
        return jobPostServices.retrieveUserJobPosts();
    }

    @GetMapping("/v1/count-user-job-posts")
    public ResponseEntity<Integer> countUserJobPosts(){
        return jobPostServices.countUserJobPosts();
    }

    @GetMapping("/v1/retrieve-job-post-with-user-id")
    public JobPost retrieveJobPostWithUserId(UUID jobPostId){
        return jobPostServices.retrieveUserJobPostWithId(jobPostId);
    }

    @PutMapping("/v1/update-job-post")
    public ResponseEntity<String> updateJobPost(@RequestBody JobPost jobPost){
        return jobPostServices.updateJobPost(jobPost);
    }

    @PostMapping("/v1/add-job-with-job-id")
    public ResponseEntity<String> addJobWithJobId(@RequestParam UUID jobPostId){
        return jobPostServices.addJobWithJobId(jobPostId);
    }
}