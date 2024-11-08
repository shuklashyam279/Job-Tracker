package com.job_tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job_tracker.dto.JobPostDTO;
import com.job_tracker.userClass.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
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
    private boolean clone;
    private String jobTitle;
    private String companyName;
    private LocalDate jobDate;

    @Column(length = 512)
    private String jobDescription;

    @Column(length = 512)
    private String jobLink;

    @Enumerated(EnumType.STRING)
    private JobStatusEnum Status;

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
        return dto;
    }

}