package com.cognizant.service;

import com.cognizant.entity.Student;
import com.cognizant.exception.NotUniqueException;
import com.cognizant.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService implements ApplicationService<Student> {

    private final StudentRepository studentRepository;
    @Override
    public void save(Student student) {
        if (studentRepository.existsById(student.getEmail()))
            throw new NotUniqueException(String.format("Student %s already exists", student.getEmail()));
        studentRepository.save(student);
    }
}
