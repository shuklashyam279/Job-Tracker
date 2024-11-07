package com.job_tracker.entity;

import com.job_tracker.userClass.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String jobTitle;
    private String companyName;
    private String jobDescription;
    private LocalDate jobDate;
    private String jobLink;
    private String resume;

    @Enumerated(EnumType.STRING)
    private JobStatusEnum Status;
}