package com.job_tracker.user;

import com.job_tracker.resume.Resume;
import com.job_tracker.resume.ResumeDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "resumeDTO", source = "resumes")
    UserDTO toDTO(User user);

    List<ResumeDTO> mapResumes(List<Resume> resumes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jobPosts", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "resumes", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(UserDTO dto);
}
