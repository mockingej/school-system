package com.cognizant.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    @Email
    private String teacher;

    @NotEmpty
    private Set<@Email String> students;
}
