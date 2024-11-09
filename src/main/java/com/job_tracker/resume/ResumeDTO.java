package com.job_tracker.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job_tracker.user.User;
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

    private UUID id;
    private String resumeName;

    @JsonIgnore
    private User user;
}