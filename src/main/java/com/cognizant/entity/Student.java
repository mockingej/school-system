package com.cognizant.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Student {
    @Id
    @Column(unique = true)
    private String email;

    private String name;

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private Set<Teacher> teachers = new HashSet<>();

}
