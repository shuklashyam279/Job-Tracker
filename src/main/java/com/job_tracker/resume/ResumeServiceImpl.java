package com.job_tracker.resume;

import com.job_tracker.user.User;
import com.job_tracker.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService{

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    public User getUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public List<Resume> getAllResume() {
        return resumeRepository.findAll();
    }

    public String saveResume(MultipartFile file) {
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
        resumeRepository.save(resume);
        return "Resume saved successfully.";
    }

    public int countUserResumes() {
        User user = getUser();
        return resumeRepository.countByUser(null);
    }

    public List<ResumeDTO> retrieveUserResumes() {
        List<Resume> resumes = resumeRepository.findByUser(getUser());
        return resumes
                .stream()
                .map(ResumeMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public String deleteUserResume(UUID resumeId) {
        resumeRepository.deleteById(resumeId);
        return "Resume deleted successfully.";
    }

    public byte[] downloadUserResume(UUID resumeId) {
        return resumeRepository.findById(resumeId).orElseThrow().getResumeFile();
    }
}