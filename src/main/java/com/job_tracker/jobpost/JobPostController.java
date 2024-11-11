package com.job_tracker.jobpost;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class JobPostController {
    @Autowired
    private JobPostService jobPostService;

    // =============================Retrieve All Job Posts==========================================
    @GetMapping("/v1/dashboard/all-jobs")
    public List<JobPostDTO> allJobPosts(@RequestParam int pageNumber) {
        return jobPostService.allJobPosts(pageNumber);
    }

    // =============================Add User's Job Post==========================================
    @PostMapping("/v1/add-job")
    public ResponseEntity<String> addJob(@Valid @RequestBody JobPost jobPost, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder();
                for (FieldError error : bindingResult.getFieldErrors()) {
                    errorMessage.append(error.getDefaultMessage()).append(" ");
                }
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(errorMessage.toString());
            }
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(jobPostService.createJobPosts(jobPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // =============================Set Resume to User's Job Post==========================================
    @PutMapping("/v1/set-resume")
    public ResponseEntity<?> setResume(
            @RequestBody JobPost jobPost,
            @RequestParam UUID resumeID
    ) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobPostService.setResume(jobPost, resumeID));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ================================Delete User's Job Post=======================================
    @DeleteMapping("/v1/delete-job-post")
    public ResponseEntity<String> deleteUsersJobPost(@RequestParam UUID jobPostId) {
        try {
            jobPostService.deleteUsersJobPost(jobPostId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Jobpost deleted successfully, with ID: " + jobPostId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ===============================Retrieve User's Job Posts========================================
    @GetMapping("/v1/retrieve-user-job-posts")
    public ResponseEntity<?> retrieveUserJobPosts(@RequestParam int pageNumber) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobPostService.retrieveUserJobPosts(pageNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ============================Count User's Job Posts===========================================
    @GetMapping("/v1/count-user-job-posts")
    public ResponseEntity<?> countUserJobPosts() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobPostService.countUserJobPosts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ================================Retrive Job Post with Job Post ID=======================================
    @GetMapping("/v1/retrieve-job-post-with-user-id")
    public ResponseEntity<?> retrieveJobPostWithJobPostId(UUID jobPostId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobPostService.retrieveUserJobPostWithId(jobPostId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ================================Update Job Post Details=======================================
    @PutMapping("/v1/update-job-post")
    public ResponseEntity<String> updateJobPost(@RequestBody JobPost jobPost) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobPostService.updateJobPost(jobPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // =================================Add Job Posts From Dashboard to User's Account======================================
    @PostMapping("/v1/add-job-with-job-id")
    public ResponseEntity<String> addJobWithJobId(@RequestParam UUID jobPostId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobPostService.addJobWithJobId(jobPostId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ==================================Retrieve User's Job Posts Count Of Each Day=====================================
    @GetMapping("/v1/retrieve-user-per-day-jobpost")
    public List<Object[]> retrieveUsersPerDayJobPosts(@RequestParam int pageNumber) {
        return jobPostService.retrieveUsersPerDayJobPosts(pageNumber);
    }

    // ====================================Top 3 Performer's of the day with their Job Counts ===========================
    @GetMapping(
            "/v1/dashboard/top-three-performer-of-the-day-with-their-job-count")
    public ResponseEntity<?> topPerformersOfTheDay() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobPostService.retrieveTopPerformersOfTheDay());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // =============================Retrieve JobPosts with Filters Applied===================================
    @GetMapping("/v1/retrieve-job-posts-with-filters-applied")
    public ResponseEntity<?> retrieveJobPostsWithFiltersApplied(
            @RequestParam String jobTitle,
            @RequestParam String companyName,
            @RequestParam String jobDescription,
            @RequestParam LocalDate jobDate,
            @RequestParam JobStatusEnum status
    ) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            jobPostService.retrieveJobsByFilters(
                                    jobTitle,
                                    companyName,
                                    jobDescription,
                                    jobDate,
                                    status
                            )
                    );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ========================================Dashboard Search Functionality=============================================
    @GetMapping("/v1/dashboard/search-jobPosts-containing-strings")
    public ResponseEntity<?> retrieveJobPostsWithString(@RequestParam String string, @RequestParam int pageNumber) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobPostService.retrieveJobPostsContainingString(string, pageNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ======================================Retrieve User Job Posts Containing String===================================
    @GetMapping("/v1/search-user-jobpost-containing-string")
    public ResponseEntity<?> retrieveUserJobPostsContainingString(@RequestParam String string, @RequestParam int pageNumber) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobPostService.retrieveUserJobPostsContainingString(string));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // =======================================Retrieve Job Post Count Per Day================================
    @GetMapping("/v1/dashboard/retrieve-jobpost-count-per-day")
    public ResponseEntity<?> retrieveJobCountsPerDay(@RequestParam int pageNumber) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jobPostService.retrieveJobCountsPerDay(pageNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}