package com.cognizant.controller;

import com.cognizant.entity.Teacher;
import com.cognizant.model.*;
import com.cognizant.service.TeacherService;
import com.cognizant.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping(value = "/teachers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@Valid @RequestBody EntityRequest entityRequest) {
        Teacher teacher = Mapper.teacherMapper(entityRequest);
        teacherService.save(teacher);
        return new ResponseEntity<>("Successfully Created!", HttpStatus.CREATED);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        teacherService.registerStudents(registerRequest);
        return new ResponseEntity<>("Successfully Registered", HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/deregister", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deregister(@Valid @RequestBody DeregisterRequest deregisterRequest) {
        teacherService.deregisterStudents(deregisterRequest);
        return new ResponseEntity<>("Successfully Deregistered", HttpStatus.OK);
    }

    @GetMapping(value = "/commonstudents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonStudentResponse> deregister(@RequestParam List<String> teacher) {
        return new ResponseEntity<>(teacherService.retrieveAllCommonStudents(new HashSet<>(teacher)), HttpStatus.OK);
    }

    @GetMapping(value = "/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherStudentResponse> retrieveAll() {
        return new ResponseEntity<>(teacherService.retrieveAllTeachers(), HttpStatus.OK);
    }

}
