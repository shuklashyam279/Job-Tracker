package com.job_tracker.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

    @GetMapping("/")
    public String welcome(){
        return "Congratulations!!";
    }
}