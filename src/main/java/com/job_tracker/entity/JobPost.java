package com.job_tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job_tracker.dto.JobPostDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Nullable
    private Boolean clone;


    @Size(min = 3, message = "Job title must have at least 3 characters.")
    private String jobTitle;

    @Size(min = 3, message = "Company name must have at least 3 characters.")
    private String companyName;

    private LocalDate jobDate;

    @Column(length = 2560)
    @Size(min = 3, message = "Job Description must have at least 3 characters.")
    private String jobDescription;

    @Column(length = 2048)
    private String jobLink;

    @Enumerated(EnumType.STRING)
    private JobStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Nullable
    @JsonIgnore
    private User user;

    @OneToOne
    @PrimaryKeyJoinColumn
    @Nullable
    @JsonIgnore
    private Resume resume;

    @Nullable
    private String resumeName;

    public void setResumeName() {
        this.resumeName = resume.getResumeName();
    }

    public JobPostDTO toDTO() {
        JobPostDTO dto = new JobPostDTO();
        dto.setJobPostId(this.id);
        dto.setJobTitle(this.jobTitle);
        dto.setCompanyName(this.companyName);
        dto.setJobDescription(this.jobDescription);
        dto.setJobDate(this.jobDate);
        dto.setJobLink(this.jobLink);
        dto.setUsername(user.getUsername());
        dto.setClone(this.clone);
        dto.setJobStatus(this.status);
        return dto;
    }

}