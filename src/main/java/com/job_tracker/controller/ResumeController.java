package com.job_tracker.controller;

import com.job_tracker.dto.ResumeDTO;
import com.job_tracker.entity.Resume;
import com.job_tracker.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class ResumeController {

    @Autowired
    private ResumeService resumeServices;

    @GetMapping("/v1/all-resume")
    public List<Resume> getAllResume() {
        return resumeServices.getAllResume();
    }

    @PostMapping("/v1/create-resume")
    public ResponseEntity<Resume> createResume(@RequestParam("file") MultipartFile file) {
        try {
            Resume savedResume = resumeServices.saveResume(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedResume);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/v1/user-resume-count")
    public int countUsersResume() {
        return resumeServices.countUserResumes();
    }

    @GetMapping("/v1/retrieve-user-resumes")
    public List<ResumeDTO> retrieveUserResume() {
        return resumeServices.retrieveUserResumes();
    }

    @DeleteMapping("/v1/delete-user-resume")
    public ResponseEntity<String> deleteUserResume(UUID resumeId) {
        return resumeServices.deleteUserResume(resumeId);
    }

}