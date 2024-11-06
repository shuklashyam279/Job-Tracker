package com.job_tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

    @GetMapping("/")
    public String welcome(){
        return "Welcome to Job Tracking Web Application!";
    }
}
