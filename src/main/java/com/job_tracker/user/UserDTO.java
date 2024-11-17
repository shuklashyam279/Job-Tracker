package com.job_tracker.user;

import com.job_tracker.resume.ResumeDTO;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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