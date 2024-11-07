package com.job_tracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class JobPostDTO {

    private UUID jobPostId;

    private String jobTitle;
    private String companyName;
    private String jobDescription;
    private LocalDate jobDate;
    private String jobLink;
}
