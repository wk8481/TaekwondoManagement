package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import be.kdg.programming3.projectwilliamkasasa.exception.NotFoundException;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.StudentFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.security.CustomUserDetails;
import be.kdg.programming3.projectwilliamkasasa.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static be.kdg.programming3.projectwilliamkasasa.domain.Role.ADMIN;

@Controller
public class StudentController extends SessionController {

    private final StudentService studentService;






    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;

    }


@GetMapping("/students")
public ModelAndView allStudent(@AuthenticationPrincipal CustomUserDetails user, HttpServletRequest request,
                               HttpSession session) {
    Integer instructorId = user == null ? null : user.getInstructorId();
    var mav = new ModelAndView();
    mav.setViewName("students");
    mav.addObject("all_students",
            studentService.getStudentsWithTechniques()
                    .stream()
                    .map(student -> new StudentFormViewModel(
                            student.getId(),
                            student.getName(),
                            student.getStartDate(),
                            request.isUserInRole(ADMIN.getCode()) ||
                                    (instructorId != null &&
                                            student.getTechniques()
                                                    .stream()
                                                    .map(StudentTechnique::getTechnique)
                                                    .anyMatch(tech -> tech.getId() == instructorId)
                                    )
                    ))
                    .toList());
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
                                                .anyMatch(tech -> tech.getId() == user.getInstructorId())

                ));

                updatePageVisitHistory("student", session);
            }
        } catch (NotFoundException e) {
            logger.error("Error retrieving student details: {}", e.getMessage());
            mav.setViewName("error-technique");
        }
        return mav;
    }



    @GetMapping("/search-students")
    public String searchStudents() {
        logger.info("hello search");
        return "search-students";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e) {
        logger.error(e.getMessage());
        return "error-technique";
    }
}
