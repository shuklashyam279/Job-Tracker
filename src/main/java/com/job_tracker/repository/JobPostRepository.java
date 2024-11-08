package com.job_tracker.repository;

import com.job_tracker.entity.JobPost;
import com.job_tracker.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, UUID> {
    String sortBy = "jobDate";

    public List<JobPost> findByUser(User user, Sort sort);

    public int countByUser(User user);

    public boolean existsByUser(User user);
}