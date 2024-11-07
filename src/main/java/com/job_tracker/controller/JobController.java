package com.job_tracker.controller;

import com.job_tracker.entity.JobPost;
import com.job_tracker.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {
    @Autowired
    private JobPostService jobPostServices;

    @GetMapping("/all_jobs")
    public List<JobPost> allJobPosts() {
        return jobPostServices.allJobPosts();
    }

    @PostMapping("/add_job")
    public ResponseEntity<JobPost> addJob(@Validated @RequestBody JobPost jobPost) {
        return jobPostServices.createJobPosts(jobPost);
    }
}