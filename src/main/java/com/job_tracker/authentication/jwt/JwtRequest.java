package com.job_tracker.authentication.jwt;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtRequest {

    @NotEmpty(message = "Email can not be empty.")
    @NotBlank(message = "Email can not be blank.")
    @Email(message = "Email is not formated properly.")
    private String email;
    @NotEmpty(message = "Password can not be empty.")
    @NotBlank(message = "Password can not be blank.")
    @Size(min = 8, message = "Password can not have smaller than 8 characters")
    private String password;
}