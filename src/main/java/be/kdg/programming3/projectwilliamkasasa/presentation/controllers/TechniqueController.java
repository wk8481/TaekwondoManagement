package be.kdg.programming3.projectwilliamkasasa.presentation.controllers;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import be.kdg.programming3.projectwilliamkasasa.exception.NotFoundException;
import be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels.TechniqueDto;
import be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels.TechniqueFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.service.TechniqueService;
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

import java.util.List;
import java.util.Optional;

@Controller
public class TechniqueController extends SessionController {


    private TechniqueFormViewModel techniqueFormViewModel;

    private TechniqueService techniqueService;

    private Logger logger = LoggerFactory.getLogger(TechniqueController.class);

    @Autowired
    public TechniqueController(TechniqueService techniqueService, TechniqueFormViewModel techniqueFormViewModel) {
        this.techniqueService = techniqueService;
        this.techniqueFormViewModel = techniqueFormViewModel;
    }

    @GetMapping("/techniques")
    public String showTechniquesView(Model model, HttpSession session) {
        logger.info("Request for techniques view!");
        List<Technique> techniques = techniqueService.getTechniques();
        model.addAttribute("techniques", techniques);
        updatePageVisitHistory("techniques", session);
        return "techniques";
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
    @GetMapping("/techniques/details/{id}")
    public String showTechniqueDetails(@PathVariable("id") int id, Model model, HttpSession session) {
        logger.info("Request for technique details view!");

        try {
            Optional<TechniqueDto> techniqueDTO = techniqueService.getTechniqueDtoById(id);

            if (techniqueDTO.isPresent()) {
                model.addAttribute("techniqueDetails", techniqueDTO.get());
                updatePageVisitHistory("technique-details", session);
                logger.info("Technique details found. Redirecting to technique-details page.");
                return "technique-details";
            } else {
                // Handle the case when no technique with the given ID or associated details is found
                // You can redirect to an error page or handle it as per your requirements
                logger.warn("Technique details not found for ID: {}", id);
                return "error-technique"; // Provide the name of the error page here
            }
        } catch (NotFoundException e) {
            logger.error("Error retrieving technique details: " + e.getMessage());
            return "error-technique";
        }
    }

    //jpa version
//    @GetMapping("/techniques/details/{id}")
//    public String showTechniqueDetails(@PathVariable int id, Model model) {
//        Optional<TechniqueDto> techniqueDtoOptional = techniqueService.getTechniqueDtoById(id);
//
//        if (techniqueDtoOptional.isPresent()) {
//            TechniqueDto techniqueDto = techniqueDtoOptional.get();
//            model.addAttribute("techniqueDetails", techniqueDto);
//
//            // Check if the instructor is not null before accessing the ID
//            if (techniqueDto.getInstructor() != null) {
//                model.addAttribute("instructorId", techniqueDto.getInstructor().getId());
//            } else {
//                // Handle the case where the instructor is null
//                model.addAttribute("instructorId", "N/A");
//            }
//
//            return "technique-details";
//        } else {
//            // Handle the case where the technique is not found
//            return "redirect:/techniques";
//        }
//    }
//     New method for showing the search form
    @GetMapping("/techniques/search")
    public String showSearchForm(Model model, HttpSession session) {

        model.addAttribute("types", Type.values());
        updatePageVisitHistory("search", session);
        model.addAttribute("searchFormViewModel", new TechniqueFormViewModel());
        return "search";
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

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e) {
        logger.error(e.getMessage());
        return "error-technique";
    }
}





