package com.cognizant.utils;

import com.cognizant.entity.Student;
import com.cognizant.entity.Teacher;
import com.cognizant.model.EntityRequest;


public final class Mapper {

    private Mapper() {
    }

    public static Teacher teacherMapper(EntityRequest entityRequest) {
        Teacher teacher = new Teacher();
        teacher.setName(entityRequest.getName());
        teacher.setEmail(entityRequest.getEmail());
        return teacher;
    }

    public static Student studentMapper(EntityRequest entityRequest) {
        Student student = new Student();
        student.setName(entityRequest.getName());
        student.setEmail(entityRequest.getEmail());
        return student;
    }

}
