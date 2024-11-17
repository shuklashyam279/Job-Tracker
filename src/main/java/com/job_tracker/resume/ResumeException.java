package com.job_tracker.resume;

public class ResumeException extends RuntimeException {
    public ResumeException(String message, Exception cause) {
        super(message, cause);
    }
}