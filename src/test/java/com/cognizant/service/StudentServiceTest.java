package com.cognizant.service;

import com.cognizant.entity.Student;
import com.cognizant.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService sut;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void save() {
        Student student = new Student();
        student.setEmail("test@test.com");
        student.setName("Test");

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        sut.save(student);
        verify(studentRepository, times(1)).save(any(Student.class));

    }
}