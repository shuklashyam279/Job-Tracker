package com.job_tracker.jobpost;

import com.job_tracker.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, UUID> {
    String sortBy = "jobDate";

    Page<JobPost> findByUser(User user, Pageable pageable);

    int countByUser(User user);

    boolean existsByUser(User user);

    Page<Object[]> countUsersPostPerDay(@Param("user") User user, Pageable pageable);

    @Query(
            "SELECT e.user, COUNT(e) FROM JobPost e WHERE e.jobDate=:date GROUP BY e.user ORDER BY COUNT(e) DESC"
    )
    public List<Object[]> topPerformersOfTheDay(@Param("date") LocalDate date);

    // =============================Retrieve Job Posts With Filters================================
    @Query(
            "SELECT jp FROM JobPost jp WHERE " +
                    "(jp.jobTitle LIKE %:jobTitle% OR " +
                    "jp.companyName LIKE %:companyName% OR " +
                    "jp.jobDescription LIKE %:jobDescription%) AND " +
                    "(jp.jobDate = :jobDate AND jp.status = :jobStatus) " +
                    "ORDER BY jp.jobDate DESC"
    )
    List<JobPost> findJobPostByFilters(
            @Param("jobTitle") String jobTitle,
            @Param("companyName") String companyName,
            @Param("jobDescription") String jobDescription,
            @Param("jobDate") LocalDate jobDate,
            @Param("jobStatus") JobStatusEnum jobStatus
    );

    // ============================Retrieve Job Posts Containing String======================================
    @Query(
            "SELECT jp FROM JobPost jp " +
                    "LEFT JOIN jp.user u " +
                    "WHERE jp.jobTitle LIKE %:string% OR " +
                    "jp.companyName LIKE %:string% OR " +
                    "jp.jobDescription LIKE %:string% OR " +
                    "jp.status LIKE %:string% OR " +
                    "u.fullName LIKE %:string% " +
                    "ORDER BY jp.jobDate "
    )
    Page<JobPost> findJobPostContainingString(@Param("string") String string, Pageable pageable);

    // ============================Retrieve Users Job Posts Containing String======================================
    @Query(
            "Select jp FROM JobPost jp WHERE " +
                    "(jp.user =:user) AND " +
                    "(jp.jobTitle LIKE %:string% OR " +
                    "jp.companyName LIKE %:string% OR " +
                    "jp.status LIKE %:string% OR " +
                    "jp.jobDescription LIKE %:string%)"
    )
    List<JobPost> findUserJobPostContainingString(User user,@Param("string") String string);

    // ===============================Retrieve Job Posts Per Day=================================================

    @Query("SELECT count(jp), jp.jobDate FROM JobPost jp WHERE jp.clone = false GROUP BY jp.jobDate ORDER BY jp.jobDate DESC")
    List<Object[]> findJobCountPerDay();

}