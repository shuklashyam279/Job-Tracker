package com.job_tracker.jobpost;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job_tracker.resume.Resume;
import com.job_tracker.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
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

    @Nullable
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

}