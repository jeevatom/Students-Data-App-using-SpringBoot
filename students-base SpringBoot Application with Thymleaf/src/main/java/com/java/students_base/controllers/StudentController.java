package com.java.students_base.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.students_base.model.Student;
import com.java.students_base.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/all_students")
    public String viewAllStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "all_students";  
    }

    @GetMapping("/fees_details/{id}")
    public Student getFeesDetails(@PathVariable Long id) {
        return studentService.getFeesDetails(id);
    }

    @GetMapping("/mark_details/{id}")
    public Student getMarkDetails(@PathVariable Long id) {
        return studentService.getMarkDetails(id);
    }
 

    @GetMapping("/addStudent")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "save_student";
    } 

    @PostMapping("/addStudent")
    public String saveStudent(@ModelAttribute Student student, Model model) {
        studentService.addStudentDetails(student);
        model.addAttribute("message", "Student saved successfully!");
        return "success";
    }
}
