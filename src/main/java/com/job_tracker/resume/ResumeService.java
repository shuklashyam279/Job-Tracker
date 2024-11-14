package com.job_tracker.resume;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface ResumeService {

    List<ResumeDTO> getAllResume();

    String saveResume(MultipartFile file);

    int countUserResumes();

    List<ResumeDTO> retrieveUserResumes();

    String deleteUserResume(UUID resumeId);

    byte[] downloadUserResume(UUID resumeId);
}