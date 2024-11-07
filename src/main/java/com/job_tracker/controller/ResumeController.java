package com.job_tracker.controller;

import com.job_tracker.entity.Resume;
import com.job_tracker.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ResumeController {

    @Autowired
    private ResumeService resumeServices;

    @GetMapping("/all-resume")
    public List<Resume> getAllResume() {
        return resumeServices.getAllResume();
    }

    @PostMapping("/create-resume")
    public ResponseEntity<Resume> createResume(@RequestParam("file") MultipartFile file){
        try {
            Resume savedResume = resumeServices.saveResume(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedResume);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}