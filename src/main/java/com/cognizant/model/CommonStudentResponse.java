package com.cognizant.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class CommonStudentResponse {
    Set<String> students;
}
