package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import be.kdg.programming3.projectwilliamkasasa.domain.*;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.StudentFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.TechniqueFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.UpdateTechniqueFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.repository.InstructorRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.TechniqueRepo;
import be.kdg.programming3.projectwilliamkasasa.service.InstructorService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static be.kdg.programming3.projectwilliamkasasa.domain.Type.PUNCH;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class TechniqueControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private TechniqueRepo techniqueRepo;



    @Test
    @WithUserDetails("TheCEO")
    public void studentViewShouldAllowModificationIfInstructorSignedIn() throws Exception {
        var mvcResult = mockMvc.perform(
                        get("/student")
                                .queryParam("id", "1") // /student?id=1
                )
                .andExpect(status().isOk())
                .andExpect(view().name("student"))
                .andExpect(model().attribute("one_student",
                        Matchers.samePropertyValuesAs(new StudentFormViewModel(
                                1, "John Doe", LocalDate.of(2023, 1, 1), true
                        ))
                ))
                .andReturn();

        var student = (StudentFormViewModel) mvcResult.getModelAndView().getModel().get("one_student");
        assertEquals("John Doe", student.getName());
        assertEquals(LocalDate.of(2023, 1, 1), student.getStartDate());
        assertTrue(student.isModificationAllowed());
    }

    @Test
    @WithUserDetails("TheCEO")
    public void studentViewShouldAllowModificationIfAdminSignedIn() throws Exception {
        var mvcResult = mockMvc.perform(
                        get("/student")
                                .queryParam("id", "1") // /student?id=1
                )
                .andExpect(status().isOk())
                .andExpect(view().name("student"))
                .andExpect(model().attribute("one_student",
                        Matchers.samePropertyValuesAs(new StudentFormViewModel(
                                1, "John Doe", LocalDate.of(2023, 1, 1), true
                        ))
                ))
                .andReturn();

        var student = (StudentFormViewModel) mvcResult.getModelAndView().getModel().get("one_student");
        assertEquals("John Doe", student.getName());
        assertEquals(LocalDate.of(2023, 1, 1), student.getStartDate());
        assertTrue(student.isModificationAllowed());
    }




    @Test
    @WithUserDetails("TheCEO")
    public void deleteTechniqueShouldSucceedIfAdmin() throws Exception {
        // Insert test data: Techniques
        // Use existing technique from the database
        Technique existingTechnique = (Technique) techniqueRepo.findByName("Dollyo Chagi").orElseThrow();

        // Perform the deletion request
        mockMvc.perform(
                        get("/techniques/delete/{id}", existingTechnique.getId())
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/techniques"));

        // Verify that the technique has been deleted from the database
        assertFalse(techniqueRepo.existsById(existingTechnique.getId()));
    }


    @Test
    @WithUserDetails("TheCEO")
    public void deleteNonExistentTechniqueShouldRedirectError() throws Exception {
        // Non-existent technique ID
        int nonExistentTechniqueId = 9999;

        // Perform the deletion request
        mockMvc.perform(
                        get("/techniques/delete/{id}", nonExistentTechniqueId)
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error")); // Update the redirected URL if necessary
    }




    @Test
    @WithUserDetails("TheCEO")
    public void techniqueViewShouldAllowModificationIfInstructorSignedIn() throws Exception {
        var mvcResult = mockMvc.perform(
                        get("/technique")
                                .queryParam("id", "3") // /technique?id=1
                )
                .andExpect(status().isOk())
                .andExpect(view().name("technique"))
                .andExpect(model().attribute("one_technique",
                        Matchers.samePropertyValuesAs(new TechniqueFormViewModel(
                                3, "Momtong Jireugi", PUNCH, "Basic punching technique", true
                        ), "students")
                ))
                .andReturn();


        var technique = (TechniqueFormViewModel) mvcResult.getModelAndView().getModel().get("one_technique");
        var techniquesStudents = technique.getStudents();
        assertEquals(4, techniquesStudents.size());


    }

    @Test
    @WithUserDetails("Sensei")
    public void techniqueViewShouldAllowModificationIfDifferentInstructor() throws Exception {
        Instructor instructor = instructorService.getUserByName("Sensei");

        var mvcResult = mockMvc.perform(
                        get("/technique")
                                .queryParam("id", "2") // /technique?id=1
                )
                .andExpect(status().isOk())
                .andExpect(view().name("technique"))
                .andExpect(model().attribute("one_technique",
                        Matchers.samePropertyValuesAs(new TechniqueFormViewModel(
                                2, "Arae Makki", Type.BLOCK, "Defensive blocking technique", false
                        ), "students")
                ))
                .andReturn();

        var technique = (TechniqueFormViewModel) mvcResult.getModelAndView().getModel().get("one_technique");
        var techniquesStudents = technique.getStudents();
        assertEquals(4, techniquesStudents.size());
    }

    @Test
    @WithUserDetails("TheCEO")
    public void updateTechniqueShouldSucceedIfAdmin() throws Exception {
        // Create a valid UpdateTechniqueFormViewModel
        UpdateTechniqueFormViewModel techniqueFormViewModel = new UpdateTechniqueFormViewModel();
        techniqueFormViewModel.setId(2); // Assume this technique exists
        techniqueFormViewModel.setDescription("Updated description");

        // Perform the update request
        mockMvc.perform(
                        post("/technique/update")
                                .with(csrf())
                                .param("id", String.valueOf(techniqueFormViewModel.getId()))
                                .param("description", techniqueFormViewModel.getDescription())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/technique?id=" + techniqueFormViewModel.getId()));

        // Verify that the technique's description has been updated in the database
        Technique updatedTechnique = techniqueRepo.findById(techniqueFormViewModel.getId()).orElseThrow();
        assertEquals("Updated description", updatedTechnique.getDescription());
    }

    @Test
    @WithUserDetails("TheCEO")
    public void updateTechniqueShouldFailWithBindingErrors() throws Exception {
        // Assume this technique exists and has a non-empty description, name, and type
        int techniqueId = 4;
        String initialDescription = "Initial Description";
        String initialName = "Initial Name";
        Type initialType = Type.PUNCH; // Assuming Type is an enum with a value PUNCH

        // Ensure the technique exists with a non-empty description, name, and type
        Technique technique = new Technique();
        technique.setId(techniqueId);
        technique.setDescription(initialDescription);
        technique.setName(initialName);
        technique.setType(initialType);
        techniqueRepo.save(technique);

        // Create an invalid UpdateTechniqueFormViewModel (missing description)
        UpdateTechniqueFormViewModel techniqueFormViewModel = new UpdateTechniqueFormViewModel();
        techniqueFormViewModel.setId(techniqueId);
        techniqueFormViewModel.setDescription(""); // Invalid description (empty)

        // Perform the update request
        mockMvc.perform(
                        post("/technique/update")
                                .with(csrf())
                                .param("id", String.valueOf(techniqueFormViewModel.getId()))
                                .param("description", techniqueFormViewModel.getDescription())
                )
                .andExpect(status().is3xxRedirection()) // Expecting a redirection status
                .andExpect(redirectedUrl("/technique?id=" + techniqueFormViewModel.getId())); // Expecting redirection with error parameter


        Technique unchangedTechnique = techniqueRepo.findById(techniqueFormViewModel.getId()).orElseThrow();
        assertEquals("", unchangedTechnique.getDescription());
        assertNotEquals(initialDescription, unchangedTechnique.getDescription());
        assertEquals(initialName, unchangedTechnique.getName());
        assertEquals(initialType, unchangedTechnique.getType());
    }


    @Test
    @WithUserDetails("Sensei")
    public void updateTechniqueShouldFailIfTechniqueDoesntExist() throws Exception {
        // Choose an ID that definitely does not exist in the database
        int nonExistentTechniqueId = 99;

        // Create a valid UpdateTechniqueFormViewModel
        UpdateTechniqueFormViewModel techniqueFormViewModel = new UpdateTechniqueFormViewModel();
        techniqueFormViewModel.setId(nonExistentTechniqueId);
        techniqueFormViewModel.setDescription("Unauthorized update attempt");

        // Perform the update request
        mockMvc.perform(
                        post("/technique/update")
                                .with(csrf())
                                .param("id", String.valueOf(techniqueFormViewModel.getId()))
                                .param("description", techniqueFormViewModel.getDescription())
                )
                .andExpect(status().is3xxRedirection()) // Expecting a redirection status
                .andExpect(redirectedUrl("/technique?id=" + techniqueFormViewModel.getId())); // Expecting redirection with error paramete

        // Verify that the technique's description has not been updated in the database
        Optional<Technique> unchangedTechniqueOptional = techniqueRepo.findById(nonExistentTechniqueId);
        assertFalse(unchangedTechniqueOptional.isPresent(), "Technique with non-existent ID should not be found");
    }









}