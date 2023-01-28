package com.example.java_security.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/student")
public class StudentController {

    private static  final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "john"),
            new Student(2, "jane"),
            new Student(3, "james")
    );

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable  Integer studentId){
         return STUDENTS.stream()
                 .filter(s -> s.id() == studentId)
                 .findFirst()
                 .orElseThrow(() -> new IllegalArgumentException("student " + studentId + " not found"));
    }
}
