package com.cognizant.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@Builder
public class RegisterRequest {

    @NotBlank
    private String teacher;

    @NotEmpty
    private Set<String> students;
}
