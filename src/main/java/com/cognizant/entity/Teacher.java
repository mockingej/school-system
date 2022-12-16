package com.cognizant.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Teacher {
    @Id
    @Column(unique = true)
    private String email;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "enrolled",
            joinColumns = @JoinColumn(name = "teacher_email"),
            inverseJoinColumns = @JoinColumn(name = "student_email")
    )
    @Fetch(FetchMode.SELECT)
    private Set<Student> students = new HashSet<>();
}
