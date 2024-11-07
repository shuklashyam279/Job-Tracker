package com.job_tracker.repository;

import com.job_tracker.entity.Resume;
import com.job_tracker.userClass.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {
    public int countByUser(User user);

    public List<Resume> findByUser(User user);
}