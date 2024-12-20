package com.job_tracker.jobpost;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String username;
    private boolean clone;
    private JobStatusEnum jobStatus;

    @JsonIgnore
    public boolean getClone() {
        return clone;
    }
}