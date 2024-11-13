package com.job_tracker.user;

import java.util.List;

import com.job_tracker.resume.ResumeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {

    private String fullName;
    private String email;
    private String role;
    private List<ResumeDTO> resumeDTO;
}