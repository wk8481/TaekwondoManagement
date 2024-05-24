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

@Controller
public class TechniqueController extends SessionController {




    private final TechniqueService techniqueService;

    private Logger logger = LoggerFactory.getLogger(TechniqueController.class);

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

    @GetMapping("/techniques/add")
    public String showAddTechniqueForm(Model model, HttpSession session) {
        logger.info("Request for add technique view!");
        model.addAttribute("techniqueFormViewModel", new TechniqueFormViewModel());
        updatePageVisitHistory("addtechnique", session);
        return "addtechnique";
    }

    @PostMapping("/techniques/add")
    public String processAddTechniqueForm(@Valid @ModelAttribute("techniqueFormViewModel") TechniqueFormViewModel techniqueFormViewModel,
                                          BindingResult bindingResult) {
        logger.info("Processing:" + techniqueFormViewModel.toString());
        if (bindingResult.hasErrors()) {
            // Handle validation errors, e.g., return to the form with error messages
            bindingResult.getAllErrors().forEach(e -> logger.warn(e.toString()));
            return "addtechnique"; // This should be the name of your HTML template for adding techniques
        } else {
            logger.info("No validation errors, adding the technique...");

            // If no errors, add the technique to the repository
            techniqueService.addTechnique(techniqueFormViewModel.getId(),
                    techniqueFormViewModel.getName(),
                    techniqueFormViewModel.getType(),
                    techniqueFormViewModel.getDescription());

            // Redirect to the "techniques" view
            return "redirect:/techniques";
        }

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

//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//
        }




    @GetMapping("/techniques/search")
    public String showSearchForm(Model model, HttpSession session) {

        model.addAttribute("types", Type.values());
        updatePageVisitHistory("search", session);
        model.addAttribute("searchFormViewModel", new TechniqueFormViewModel());
        return "search-students";
    }

    // New method for processing search form
    @PostMapping("/techniques/search")
    public String searchTechniques(@RequestParam Type type, Model model) {
        List<Technique> techniques = techniqueService.getTechniqueByType(type);
        model.addAttribute("techniques", techniques);
        return "techniques";
    }

    @GetMapping("/techniques/delete/{id}")
    public String deleteTechnique(@PathVariable int id) {
        techniqueService.deleteTechnique(id);
        return "redirect:/techniques";
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
        if((user.getInstructorId() == techniqueFormViewModel.getId() || request.isUserInRole("ADMIN"))
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





