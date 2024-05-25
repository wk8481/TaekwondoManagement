package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import be.kdg.programming3.projectwilliamkasasa.service.CsvProcessingService;
import be.kdg.programming3.projectwilliamkasasa.service.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class StudentsCsvController {
    private final CsvProcessingService csvProcessingService;

    public StudentsCsvController(CsvProcessingService csvProcessingService) {
        this.csvProcessingService = csvProcessingService;
    }

    @GetMapping("/students/csv")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView uploadCsv() {
        var mav = new ModelAndView("students-csv");
        mav.getModel().put("inProgress", false);
        return mav;
    }

    @PostMapping("/students/csv")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView uploadCsv(
            @RequestParam("students-csv") MultipartFile file)
            throws IOException {
        var mav = new ModelAndView("students-csv");
        csvProcessingService.processStudentsCsv(file.getInputStream());
        mav.getModel().put("inProgress", true);
        return mav;
    }



}
