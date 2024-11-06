package com.job_tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.job_tracker.entity.JobPost;
import com.job_tracker.repository.JobPostRepository;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    public List<JobPost> allJobPosts(){
        return jobPostRepository.findAll();
    }

    public ResponseEntity<JobPost> createJobPosts(JobPost jobPost){
        jobPostRepository.save(jobPost);
        // return ResponseEntity.ok(jobPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobPost);
    }
}