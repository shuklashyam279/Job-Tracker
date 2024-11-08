package com.job_tracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job_tracker.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDTO {

    private UUID resumeId;
    private String resumeName;

    @JsonIgnore
    private User user;
}