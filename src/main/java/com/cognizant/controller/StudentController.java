package com.cognizant.controller;

import com.cognizant.entity.Student;
import com.cognizant.model.EntityRequest;
import com.cognizant.service.StudentService;
import com.cognizant.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@Valid @RequestBody EntityRequest entityRequest) {
        Student student = Mapper.studentMapper(entityRequest);
        studentService.save(student);
        return new ResponseEntity<>("Successfully Created!", HttpStatus.CREATED);
    }

}
