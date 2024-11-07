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

    @GetMapping("/all_jobs")
    public List<JobPost> allJobPosts() {
        return jobPostServices.allJobPosts();
    }

    @PostMapping("/add_job")
    public ResponseEntity<JobPostDTO> addJob(@RequestBody JobPost jobPost){
        return jobPostServices.createJobPosts(jobPost);
    }

    @PutMapping("/set-resume")
    public ResponseEntity<JobPostDTO> setResume(@RequestBody JobPost jobPost,@RequestParam UUID resumeID){
        return jobPostServices.setResume(jobPost, resumeID);
    }
}