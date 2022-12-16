package com.cognizant.service;

import com.cognizant.entity.Student;
import com.cognizant.entity.Teacher;
import com.cognizant.exception.EntityNotFoundException;
import com.cognizant.exception.NotUniqueException;
import com.cognizant.model.CommonStudentResponse;
import com.cognizant.model.DeregisterRequest;
import com.cognizant.model.RegisterRequest;
import com.cognizant.model.TeacherStudentResponse;
import com.cognizant.repository.StudentRepository;
import com.cognizant.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.cognizant.model.TeacherStudentResponse.TeacherStudentEmail;

@Service
@RequiredArgsConstructor
public class TeacherService implements ApplicationService<Teacher> {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public void save(Teacher teacher) {
        if (teacherRepository.existsById(teacher.getEmail()))
            throw new NotUniqueException(String.format("Teacher %s already exists", teacher.getEmail()));
        teacherRepository.save(teacher);
    }

    public void registerStudents(RegisterRequest registerRequest) {

        Teacher teacher = findByEmail(registerRequest.getTeacher());
        Set<Student> students = new HashSet<>();

        registerRequest
                .getStudents()
                .forEach(email -> students.add(
                        studentRepository
                                .findByEmail(email)
                                .orElseThrow(() -> new EntityNotFoundException(String.format("Student %s not found", email)))
                )
                );

        teacher.getStudents().addAll(students);
        teacherRepository.save(teacher);
    }

    public void deregisterStudents(DeregisterRequest deregisterRequest) {
        Teacher teacher = findByEmail(deregisterRequest.getTeacher());
        Set<Student> filtered = teacher
                .getStudents()
                .stream()
                .filter(student -> !student.getEmail().equals(deregisterRequest.getStudent()))
                .collect(Collectors.toSet());

        if (filtered.size() == teacher.getStudents().size()) {
            throw new EntityNotFoundException(String.format("Student %s not found", deregisterRequest.getStudent()));
        }

        teacher.setStudents(filtered);
        teacherRepository.save(teacher);
    }

    public CommonStudentResponse retrieveAllCommonStudents(Set<String> teachers) {
        Set<Teacher> teacherSet = teachers.stream().map(this::findByEmail).collect(Collectors.toSet());
        Map<String, Integer> frequencies = new HashMap<>();

        teacherSet
                .stream()
                .map(Teacher::getStudents)
                .forEach(studentSet ->
                        studentSet.forEach(
                                student ->
                                        frequencies.put(student.getEmail(),
                                                frequencies.getOrDefault(student.getEmail(), 0) + 1
                                        )
                        )
                );


        Set<String> students = frequencies
                .entrySet()
                .stream().filter(entry -> entry.getValue() == teacherSet.size())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        return CommonStudentResponse
                .builder()
                .students(students)
                .build();
    }

    public TeacherStudentResponse retrieveAllTeachers() {
        List<Teacher> allTeachers = teacherRepository.findAll();
        Set<TeacherStudentEmail> teachers = allTeachers
                .stream()
                .map(teacher -> TeacherStudentEmail
                        .builder()
                        .email(teacher.getEmail())
                        .students(
                                teacher
                                        .getStudents()
                                        .stream()
                                        .map(Student::getEmail).
                                        collect(Collectors.toSet()))
                        .build()).collect(Collectors.toSet());

        return TeacherStudentResponse
                .builder()
                .teachers(teachers)
                .build();

    }

    private Teacher findByEmail(String email) {
        return teacherRepository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher %s not found", email)));
    }

}
