package com.cognizant.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DeregisterRequest {

    @NotBlank
    @Email
    private String teacher;

    @NotBlank
    @Email
    private String student;

    @NotBlank
    private String message;
}
