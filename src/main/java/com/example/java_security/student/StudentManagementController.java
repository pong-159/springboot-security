

package com.example.java_security.student;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController

@RequestMapping(value = "mgmt/api/v1/student")
public class StudentManagementController {

    private static  final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "john"),
            new Student(2, "jane"),
            new Student(3, "james")
    );

    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable Integer studentId){
        return STUDENTS.stream()
                .filter(s -> s.id() == studentId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("student " + studentId + " not found"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudent(){
        return STUDENTS;
    }

    @PostMapping
    public void registerStudent(@RequestBody  Student student){
        log.info("register  " +  student);
    }

    @PutMapping(path = "{id}")

    public void updateStudent( @RequestBody Student student, @PathVariable("id") Integer id){
        log.info("update  " +  student);
        log.info("update id " +  id);
    }

    @DeleteMapping(path = "{id}")

    public void deleteStudent(@PathVariable("id")  Integer id){
        log.info("delete  " +  id);
    }


}

