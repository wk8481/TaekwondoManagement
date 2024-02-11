package be.kdg.programming3.projectwilliamkasasa.presentation.controllers;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.exception.NotFoundException;
import be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels.StudentDto;
import be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels.StudentFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels.TechniqueFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.service.StudentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentController extends SessionController {

    private StudentService studentService;



    private StudentFormViewModel studentFormViewModel;

    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public StudentController(StudentService studentService, StudentFormViewModel studentFormViewModel) {
        this.studentService = studentService;
        this.studentFormViewModel = studentFormViewModel;
    }

    @GetMapping("/students")
    public String showStudentsView(Model model, HttpSession session) {
        logger.info("Request for students view!");
//        List<Student> studentList = studentService.getStudents();
        List<Student> students = studentService.getStudents();
        model.addAttribute("students", students);
        updatePageVisitHistory("students", session);
        return "students";
    }

    @GetMapping("/students/add")
    public String showAddStudentForm(Model model, HttpSession session) {
        logger.info("Request for add student view!");
        model.addAttribute("studentFormViewModel", new StudentFormViewModel()); // Add an empty StudentView object to the model
        updatePageVisitHistory("addstudent", session);
        return "addstudent";
    }

    //original version
//    @PostMapping("/students/add")
//    public String processAddStudentForm(@Valid @ModelAttribute("studentFormViewModel") StudentFormViewModel studentFormViewModel,
//                                        BindingResult bindingResult) {
//        logger.info("Processing" + studentFormViewModel.toString());
//
//        if (bindingResult.hasErrors()) {
//            // Handle validation errors here
//            bindingResult.getAllErrors().forEach(e -> logger.warn(e.toString()));
//            return "addstudent";
//        } else {
//            logger.info("No validation errors, adding the student...");
//            studentService.addStudentList(studentFormViewModel.getId(),
//                    studentFormViewModel.getName(),
//                    studentFormViewModel.getStart());
//            return "redirect:/students";
//        }
//
//    }


//    em
    @PostMapping("/students/add")
    public String processAddStudentForm(@Valid @ModelAttribute("studentFormViewModel") StudentFormViewModel studentFormViewModel,
                                        BindingResult bindingResult) {
        logger.info("Processing" + studentFormViewModel.toString());

        if (bindingResult.hasErrors()) {
            // Handle validation errors here
            bindingResult.getAllErrors().forEach(e -> logger.warn(e.toString()));
            return "addstudent";
        } else {
            logger.info("No validation errors, adding the student...");

            // Assuming your StudentServiceImpl is a Spring bean
            Student addedStudent = studentService.addStudentListEM(
                    studentFormViewModel.getId(),
                    studentFormViewModel.getName(),
                    studentFormViewModel.getStart()
            );

            // Optionally, you can access information from the added student
            logger.info("Added student: {}", addedStudent);

            return "redirect:/students";
        }
    }
    // StudentController.java
    @GetMapping("/students/details/{id}")
    public ModelAndView showStudentDetails(@PathVariable int id, HttpSession session) {
        logger.info("Request for student details view!");

        ModelAndView mav = new ModelAndView();
        try {
            Student student = studentService.getStudentWithTechniques(id);

            if (student != null) {
                mav.setViewName("studentdetails");
                mav.addObject("studentFormViewModel",
                        new StudentFormViewModel(
                        student.getId(),
                        student.getName(),
                        student.getStart(),
                        student.getTechniques().stream()
                                .map(studentTechnique ->
                                        new TechniqueFormViewModel(
                                                studentTechnique.getTechnique().getName(),
                                                studentTechnique.getTechnique().getType(),
                                                studentTechnique.getTechnique().getDescription()
                                        ))
                                .toList())
                );
                updatePageVisitHistory("studentdetails", session);
            } else {
                // Handle the case where the student with the given ID is not found
                // You might redirect to an error page or handle it as needed
                mav.setViewName("studentNotFound");
            }
        } catch (NotFoundException e) {
            logger.error("Error retrieving student details: " + e.getMessage());
            mav.setViewName("error-technique");
        }
        return mav;
    }

    @PostMapping("/students/removetechniques")
    public String processRemoveTechniquesFromStudent(@ModelAttribute("studentFormViewModel") StudentFormViewModel studentFormViewModel) {
        logger.info("Processing remove techniques from student: {}", studentFormViewModel.toString());

        // Assuming that studentFormViewModel contains the necessary information, such as student ID and a list of techniques to remove.
        studentService.removeTechniquesFromStudent(studentFormViewModel.getId(), studentFormViewModel.getTechniques());

        return "redirect:/students"; //not implemeneted


    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e) {
        logger.error(e.getMessage());
        return "error-technique";
    }
}
