package com.job_tracker.service;

import com.job_tracker.entity.Resume;
import com.job_tracker.repository.ResumeRepository;
import com.job_tracker.userClass.User;
import com.job_tracker.userClass.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Resume> getAllResume() {
        return resumeRepository.findAll();
    }

    public Resume saveResume(MultipartFile file) {
        Resume resume = new Resume();
        try {
            byte[] resumeFile = file.getBytes();
            resume.setResume(resumeFile);
            String resumeName = file.getOriginalFilename();
            resume.setResumeName(resumeName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save resume", e);
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        resume.setUser(user);
        return resumeRepository.save(resume);
        // return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}