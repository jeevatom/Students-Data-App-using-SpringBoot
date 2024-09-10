package com.java.students_base.service;

import com.java.students_base.UserRepository.StudentRepository;
import com.java.students_base.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getFeesDetails(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public Student getMarkDetails(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public Student addStudentDetails(Student student) {
        return studentRepository.save(student);
    }
}
