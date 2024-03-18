package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import be.kdg.programming3.projectwilliamkasasa.exception.NotFoundException;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.StudentFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.security.CustomUserDetails;
import be.kdg.programming3.projectwilliamkasasa.service.StudentService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static be.kdg.programming3.projectwilliamkasasa.domain.Role.ADMIN;

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

//    @GetMapping("/students")
//    public String showStudentsView(Model model, HttpSession session) {
//        logger.info("Request for students view!");
////        List<Student> studentList = studentService.getStudents();
//        List<Student> students = studentService.getStudents();
//        model.addAttribute("students", students);
//        updatePageVisitHistory("students", session);
//        return "students";
//    }
    @GetMapping("/students")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allStudent(@AuthenticationPrincipal CustomUserDetails user, HttpServletRequest request,
                                   HttpSession session) {
        Integer userId = user == null ? null : user.getUserId();
        var mav = new ModelAndView();
        mav.setViewName("students");
        mav.addObject("students",
                studentService.getStudents()
                        .stream()
                        .map(student -> new StudentFormViewModel(
                                student.getId(),
                                student.getName(),
                                student.getStartDate(),
                                request.isUserInRole(ADMIN.getCode())
                                        ||
                                        userId != null &&
                                                student.getTechniques()
                                                        .stream()
                                                        .map(StudentTechnique::getTechnique)
                                                        .anyMatch(tech -> tech.getId() == userId)

                        )));
        updatePageVisitHistory("students", session);
        return mav;

    }

    @GetMapping("/students/add")
    public String showAddStudentForm(Model model, HttpSession session) {
        logger.info("Request for add student view!");
        model.addAttribute("studentFormViewModel", new StudentFormViewModel()); // Add an empty StudentView object to the model
        updatePageVisitHistory("addstudent", session);
        return "addstudent";
    }





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
                    studentFormViewModel.getStartDate()
            );

            // Optionally, you can access information from the added student
            logger.info("Added student: {}", addedStudent);

            return "redirect:/students";
        }
    }
    // StudentController.java
    @GetMapping("/student")
    public ModelAndView oneStudent(@RequestParam("id") int id, @AuthenticationPrincipal CustomUserDetails user,
                                   HttpServletRequest request, HttpSession session) {
        logger.info("Request for student details view!");

        var student = studentService.getStudentWithTechniques(id);
        var mav = new ModelAndView();
        try {

            if (student != null) {
                mav.setViewName("student");
                mav.addObject("one_student",
                        new StudentFormViewModel(
                        student.getId(),
                        student.getName(),
                        student.getStartDate(),
                                request.isUserInRole(ADMIN.getCode())
                                ||
                                user != null &&
                                        student.getTechniques()
                                                .stream()
                                                .map(StudentTechnique::getTechnique)
                                                .anyMatch(tech -> tech.getId() == user.getUserId())

                ));

                updatePageVisitHistory("student", session);
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

    @GetMapping("/search-students")
    public String searchStudents() {
        return "search-students";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e) {
        logger.error(e.getMessage());
        return "error-technique";
    }
}
