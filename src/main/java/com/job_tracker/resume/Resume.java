package com.job_tracker.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.job_tracker.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Files;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    private String resumeName;


    public String extractFileName(byte[] resume) {
        try {
            // Create a temporary file from the byte array
            java.nio.file.Path tempFile = Files.createTempFile("temp-resume", ".tmp");
            Files.write(tempFile, resume);

            // Get the file name from the temporary file path
            String fileName = tempFile.getFileName().toString();

            // Assign the file name to resumeName
            resumeName = fileName;

            // Delete the temporary file
            Files.delete(tempFile);

            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract file name from resume", e);
        }
    }

    @Lob
    @Column(name = "resume_file", columnDefinition = "BLOB")
    private byte[] resumeFile;

    public void setResume(byte[] resume) {
        int resumeSize = 200 * 1024; // 200 KB
        if (resume != null && resume.length <= resumeSize) {
            this.resumeFile = resume;
        } else {
            throw new IllegalArgumentException(
                    "File size exceeds the maximum allowed size of 200 KB"
            );
        }
    }

    public ResumeDTO toDTO() {
        ResumeDTO dto = new ResumeDTO();
        dto.setId(this.id);
        dto.setResumeName(getResumeName());
        dto.setUser(this.user);
        return dto;
    }
}