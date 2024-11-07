package com.job_tracker.service;

import com.job_tracker.dto.JobPostDTO;
import com.job_tracker.entity.JobPost;
import com.job_tracker.entity.Resume;
import com.job_tracker.repository.JobPostRepository;
import com.job_tracker.repository.ResumeRepository;
import com.job_tracker.userClass.User;
import com.job_tracker.userClass.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    public List<JobPost> allJobPosts() {
        return jobPostRepository.findAll();
    }

    public ResponseEntity<JobPostDTO> createJobPosts(JobPost jobPost) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jobPost.setUser(user);
        jobPostRepository.save(jobPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobPost.toDTO());
    }

    public ResponseEntity<JobPostDTO> setResume(JobPost jobPost, UUID resumeId) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        Resume resume = optionalResume.orElseThrow(() -> new IllegalArgumentException("Resume Does Not Exist!!"));
        jobPost.setResume(resume);
        jobPostRepository.save(jobPost);
        return ResponseEntity.status(HttpStatus.OK).body(jobPost.toDTO());
    }
}