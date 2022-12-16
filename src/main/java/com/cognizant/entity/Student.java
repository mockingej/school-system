package com.cognizant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Student {
    @Id
    @Column(unique = true)
    private String email;

    private String name;

    @ManyToMany(mappedBy = "students")
    private Set<Teacher> teachers = new HashSet<>();

}
