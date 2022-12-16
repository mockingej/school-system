package com.cognizant.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Teacher {
    @Id
    @Column(unique = true)
    private String email;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "enrolled",
            joinColumns = @JoinColumn(name = "teacher_email"),
            inverseJoinColumns = @JoinColumn(name = "student_email")
    )
    private Set<Student> students = new HashSet<>();
}
