package com.job_tracker.resume;

import com.job_tracker.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {
    int countByUser(User user);

    List<Resume> findByUser(User user);
}