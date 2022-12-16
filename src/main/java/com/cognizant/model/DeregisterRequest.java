package com.cognizant.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class DeregisterRequest {

    @NotBlank
    String teacher;

    @NotBlank
    String student;

    @NotBlank
    String message;
}
