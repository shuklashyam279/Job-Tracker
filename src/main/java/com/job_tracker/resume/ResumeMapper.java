package com.job_tracker.resume;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResumeMapper {
    ResumeMapper INSTANCE = Mappers.getMapper(ResumeMapper.class);

    ResumeDTO toDTO(Resume resume);

    @Mapping(target = "resumeFile", ignore = true)
    @Mapping(target = "user", ignore = true)
    Resume toEntity(ResumeDTO resumeDTO);

    ResumeFileDTO toResumeFileDTO(Resume resume);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "resumeName", ignore = true)
    @Mapping(target = "user", ignore = true)
    Resume toResumeFromFile(ResumeFileDTO resumeFileDTO);
}