package com.cognizant.service;

import com.cognizant.entity.Student;
import com.cognizant.entity.Teacher;
import com.cognizant.exception.EntityNotFoundException;
import com.cognizant.exception.NotUniqueException;
import com.cognizant.model.CommonStudentResponse;
import com.cognizant.model.DeregisterRequest;
import com.cognizant.model.RegisterRequest;
import com.cognizant.repository.StudentRepository;
import com.cognizant.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @InjectMocks
    private TeacherService sut;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRepository studentRepository;

    private RegisterRequest buildRegisterRequest() {
        return RegisterRequest
                .builder()
                .teacher("teacherken@gmail.com")
                .students(Set.of("studentjon@gmail.com", "studenthon@gmail.com"))
                .build();
    }

    private DeregisterRequest buildeDeregisterRequest() {
        return DeregisterRequest
                .builder()
                .teacher("teacherken@gmail.com")
                .student("studentjon@gmail.com")
                .build();
    }


    @Test
    void save() throws NotUniqueException {
        Teacher teacher = new Teacher();
        teacher.setEmail("test@test.com");
        teacher.setName("Test");

        System.out.println(teacher);

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        sut.save(teacher);
        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }


    @Test
    void registerStudents() {
        RegisterRequest registerRequest = RegisterRequest
                .builder()
                .teacher("teacherken@gmail.com")
                .students(Set.of("studentjon@gmail.com", "studenthon@gmail.com"))
                .build();

        Teacher teacher = new Teacher();
        teacher.setEmail("teacherken@gmail.com");

        Student student1 = new Student();
        student1.setEmail("studentjon@gmail.com");
        Student student2 = new Student();
        student2.setEmail("studenthon@gmail.com");

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.of(teacher));
        when(studentRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(student1))
                .thenReturn(Optional.of(student2));

        sut.registerStudents(registerRequest);

        verify(teacherRepository, times(1)).findByEmail(anyString());
        verify(studentRepository, times(2)).findByEmail(anyString());
    }


    @Test
    void registerStudents_TeacherNotFound() {
        RegisterRequest registerRequest = buildRegisterRequest();

        Teacher teacher = new Teacher();
        teacher.setEmail("teacherken@gmail.com");

        Student student1 = new Student();
        student1.setEmail("studentjon@gmail.com");
        Student student2 = new Student();
        student2.setEmail("studenthon@gmail.com");

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> sut.registerStudents(registerRequest));

        verify(teacherRepository, times(1)).findByEmail(anyString());
        assertEquals(exception.getMessage(), "Teacher teacherken@gmail.com not found");
    }

    @Test
    void registerStudents_StudentNotFound() {
        RegisterRequest registerRequest = buildRegisterRequest();

        Teacher teacher = new Teacher();
        teacher.setEmail("teacherken@gmail.com");

        Student student1 = new Student();
        student1.setEmail("studentjon@gmail.com");
        Student student2 = new Student();
        student2.setEmail("studenthon@gmail.com");

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.of(teacher));
        when(studentRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(student1))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> sut.registerStudents(registerRequest));

        verify(teacherRepository, times(1)).findByEmail(anyString());
        verify(studentRepository, times(2)).findByEmail(anyString());
    }


    @Test
    void deregisterStudents() {
        DeregisterRequest deregisterRequest = buildeDeregisterRequest();

        Teacher teacher = new Teacher();
        teacher.setEmail("teacherken@gmail.com");

        Student student1 = new Student();
        student1.setEmail("studentjon@gmail.com");
        Student student2 = new Student();
        student2.setEmail("studenthon@gmail.com");

        teacher.getStudents().addAll(Set.of(student1, student2));

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.of(teacher));

        sut.deregisterStudents(deregisterRequest);

        verify(teacherRepository, times(1)).findByEmail(anyString());
        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }

    @Test
    void deregisterStudents_TeacherNotFound() {
        DeregisterRequest deregisterRequest = buildeDeregisterRequest();

        Teacher teacher = new Teacher();
        teacher.setEmail("teacherken@gmail.com");

        Student student1 = new Student();
        student1.setEmail("studentjon@gmail.com");
        Student student2 = new Student();
        student2.setEmail("studenthon@gmail.com");

        teacher.getStudents().addAll(Set.of(student1, student2));

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> sut.deregisterStudents(deregisterRequest));

        assertEquals(exception.getMessage(), "Teacher teacherken@gmail.com not found");

        verify(teacherRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void deregisterStudents_StudentNotFound() {
        DeregisterRequest deregisterRequest = buildeDeregisterRequest();

        Teacher teacher = new Teacher();
        teacher.setEmail("teacherken@gmail.com");

        Student student2 = new Student();
        student2.setEmail("studenthon@gmail.com");

        teacher.getStudents().add(student2);

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.of(teacher));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> sut.deregisterStudents(deregisterRequest));
        assertEquals(exception.getMessage(), "Student studentjon@gmail.com not found");
        verify(teacherRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void retrieveAllCommonStudents() {

        Teacher teacher = new Teacher();
        teacher.setEmail("teacherken@gmail.com");

        Student student1 = new Student();
        student1.setEmail("studentjon@gmail.com");
        Student student2 = new Student();
        student2.setEmail("studenthon@gmail.com");
        teacher.getStudents().addAll(Set.of(student1, student2));


        Set<String> input = Set.of("teacherken@gmail.com");

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.of(teacher));
        CommonStudentResponse expected = sut.retrieveAllCommonStudents(input);

        assertEquals(expected.getStudents().size(), 2);
        assertEquals(expected.getStudents(), Set.of(student1.getEmail(), student2.getEmail()));
        verify(teacherRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void retrieveAllCommonStudentsWithTwoTeachers() {

        Teacher teacher1 = new Teacher();
        teacher1.setEmail("teacherken@gmail.com");

        Student student1 = new Student();
        student1.setEmail("studentjon@gmail.com");
        Student student2 = new Student();
        student2.setEmail("studenthon@gmail.com");
        Student student3 = new Student();
        student3.setEmail("studentjane@gmail.com");
        teacher1.getStudents().addAll(Set.of(student1, student2, student3));


        Teacher teacher2 = new Teacher();
        teacher2.setEmail("teacherJen@gmail.com");
        teacher2.getStudents().addAll(Set.of(student1, student3));

        Set<String> input = Set.of("teacherken@gmail.com", "teacherJen@gmail.com");

        when(teacherRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(teacher1))
                .thenReturn(Optional.of(teacher2));
        CommonStudentResponse expected = sut.retrieveAllCommonStudents(input);

        assertEquals(expected.getStudents().size(), 2);
        assertEquals(expected.getStudents(), Set.of(student1.getEmail(), student3.getEmail()));
        verify(teacherRepository, times(2)).findByEmail(anyString());
    }
}