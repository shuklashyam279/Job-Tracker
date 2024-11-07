package com.job_tracker.service;

import com.job_tracker.dto.ResumeDTO;
import com.job_tracker.entity.Resume;
import com.job_tracker.repository.ResumeRepository;
import com.job_tracker.userClass.User;
import com.job_tracker.userClass.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

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
        resume.setUser(getUser());
        return resumeRepository.save(resume);
    }

    public int countUserResumes() {
        User user = getUser();
        return resumeRepository.countByUser(null);
    }

    public List<ResumeDTO> retrieveUserResumes() {
        List<Resume> resumes = resumeRepository.findByUser(getUser());
        return resumes
                .stream()
                .map(resume -> resume.toDTO())
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> deleteUserResume(UUID resumeId) {
        resumeRepository.deleteById(resumeId);
        return ResponseEntity.status(HttpStatus.OK).body("Resume deleted successfully.");
    }
}