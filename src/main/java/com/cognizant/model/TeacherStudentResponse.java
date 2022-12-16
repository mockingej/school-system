package com.cognizant.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class TeacherStudentResponse {

    private Set<TeacherStudentEmail> teachers;

    @Getter
    @Setter
    @Builder
    public static class TeacherStudentEmail {
        private String email;
        private Set<String> students;
    }

}
