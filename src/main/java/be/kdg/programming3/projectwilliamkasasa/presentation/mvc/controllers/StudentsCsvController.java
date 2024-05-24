package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import be.kdg.programming3.projectwilliamkasasa.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public class StudentsCsvController {
    private final StudentService studentService;

    public StudentsCsvController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ModelAndView uploadCsv() {
        var mav = new ModelAndView("students_csv");
        mav.getModel().put("inProgress", false);
        return mav;
    }

    @PostMapping
    public ModelAndView uploadCsv(
            @RequestParam("students_csv") MultipartFile file)
            throws IOException {
        var mav = new ModelAndView("students_csv");
        studentService.processStudentsCsv(file.getInputStream());
        mav.getModel().put("inProgress", true);
        return mav;
    }



}
