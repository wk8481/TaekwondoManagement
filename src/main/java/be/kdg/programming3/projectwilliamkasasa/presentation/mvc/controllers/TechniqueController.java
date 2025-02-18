package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.UpdateTechniqueFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.security.CustomUserDetails;
import be.kdg.programming3.projectwilliamkasasa.service.TechniqueService;
import be.kdg.programming3.projectwilliamkasasa.exception.NotFoundException;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.StudentFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.TechniqueFormViewModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static be.kdg.programming3.projectwilliamkasasa.domain.Role.ADMIN;

@Controller
public class TechniqueController extends SessionController {




    private final TechniqueService techniqueService;

    private final Logger logger = LoggerFactory.getLogger(TechniqueController.class);

    @Autowired
    public TechniqueController(TechniqueService techniqueService) {
        this.techniqueService = techniqueService;

    }

    @GetMapping("/techniques")
    public ModelAndView allTechniques(Model model, HttpSession session) {
        logger.info("Request for techniques view!");
        var mav = new ModelAndView();
        mav.setViewName("techniques");
        mav.addObject("all_techniques",
                techniqueService.getTechniques()
                        .stream()
                        .map(technique -> new TechniqueFormViewModel(
                                technique.getId(),
                                technique.getName(),
                                technique.getType(),
                                technique.getDescription(),
                                false
                        ))
                        .toList());


        updatePageVisitHistory("techniques", session);
        return mav;
    }





    //jdbc version
    @GetMapping("/technique")
    public ModelAndView oneTechnique(@RequestParam("id") int id, HttpSession session,
                                     @AuthenticationPrincipal CustomUserDetails user,
                                     HttpServletRequest request) {
        logger.info("Request for technique details view!");


//        try {
            var technique = techniqueService.getTechniqueWithStudents(id);
            var mav = new ModelAndView();
            mav.setViewName("technique");
            mav.addObject("one_technique",
                    new TechniqueFormViewModel(
                            technique.getId(),
                            technique.getName(),
                            technique.getType(),
                            technique.getDescription(),
                            user != null && (user.getInstructorId() == technique.getId() || request.isUserInRole("ADMIN")),
                            technique.getStudents()
                                    .stream().map(
                                            studentTechnique ->
                                                    new StudentFormViewModel(
                                                            studentTechnique.getStudent().getId(),
                                                            studentTechnique.getStudent().getName(),
                                                            studentTechnique.getStudent().getStartDate(),
                                                            false
                                                    )
                                    ).toList()
                    ));

        logger.info("Technique details found. Redirecting to technique-details page.");
        updatePageVisitHistory("technique", session);
        return mav;

    }







    @GetMapping("/techniques/delete/{id}")
    public String deleteTechnique(@PathVariable int id) {
        boolean deletionResult = techniqueService.deleteTechnique(id);
        if (deletionResult) {
            return "redirect:/techniques";
        } else {
            return "redirect:/error";
        }
    }


    //might have put da ting in wrong place but will see

    @PostMapping("/technique/update")
    public String updateTechnique(@Valid UpdateTechniqueFormViewModel techniqueFormViewModel,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal CustomUserDetails user,
                                  HttpServletRequest request, HttpSession session) {
        // Conditions:
        // - The user is an admin
        // - AND no model binding errors
        if(user != null && (user.getInstructorId() == techniqueFormViewModel.getId() || request.isUserInRole(ADMIN.getCode()))
                && (!bindingResult.hasErrors())) {
            techniqueService.updateTechniqueDescription(
                    techniqueFormViewModel.getId(),
                    techniqueFormViewModel.getDescription()
            );



        }
        updatePageVisitHistory("technique", session);
        return "redirect:/technique?id=" + techniqueFormViewModel.getId();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e) {
        logger.error(e.getMessage());
        return "error-technique";
    }
}





