package com.job_tracker.resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeServiceImpl resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/v1/all-resume")
    public List<ResumeDTO> getAllResume() {
        return resumeService.getAllResume();
    }

    @PostMapping("/v1/create-resume")
    public ResponseEntity<?> createResume(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(resumeService.saveResume(file));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/v1/user-resume-count")
    public ResponseEntity<?> countUsersResume() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(resumeService.countUserResumes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/v1/retrieve-user-resumes")
    public ResponseEntity<?> retrieveUserResume() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(resumeService.retrieveUserResumes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/v1/delete-user-resume")
    public ResponseEntity<String> deleteUserResume(UUID resumeId) {
        try {
            resumeService.deleteUserResume(resumeId);
            return ResponseEntity.status(HttpStatus.OK).body("Resume Deleted Successfully with ID: " + resumeId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/v1/download-user-resume")
    public ResponseEntity<?> downloadUserResume(@RequestParam UUID resumeId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(resumeService.downloadUserResume(resumeId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}