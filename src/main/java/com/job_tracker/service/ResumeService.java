package com.job_tracker.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.job_tracker.entity.Resume;
import com.job_tracker.entity.User;
import com.job_tracker.repository.ResumeRepository;
import com.job_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Resume> getAllResume() {
        return resumeRepository.findAll();
    }

    public ResponseEntity<Resume> saveResume(MultipartFile file, Long userId) {
        Resume resume = new Resume();
        try {
            byte[] resumeFile = file.getBytes();
            resume.setResume(resumeFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() ->
                new IllegalArgumentException("User not found with ID: " + userId)
        );
        resume.setUser(user);
        resumeRepository.save(resume);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
