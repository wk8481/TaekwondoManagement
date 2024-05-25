package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import be.kdg.programming3.projectwilliamkasasa.domain.*;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.StudentFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.TechniqueFormViewModel;
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
    private StudentRepo studentRepo;

    @Autowired
    private TechniqueRepo techniqueRepo;

    @Autowired
    private InstructorRepo instructorRepo;

//    @BeforeEach
//    public void setUp() {
//        // Insert some sample techniques into the database
//        techniqueRepo.save(new Technique("Dollyo Chagi", Type.KICK, "Powerful kicking technique"));
//        techniqueRepo.save(new Technique("Arae Makki", Type.BLOCK, "Defensive blocking technique"));
//        techniqueRepo.save(new Technique("Momtong Jireugi", Type.PUNCH, "Basic punching technique"));
//    }

//    @Test
//    public void techniqueViewShouldBeRenderedWithTechniqueAndStudentData() throws Exception {
//
//        var mvcResult = mockMvc.perform(
//                        get("/technique")
//                                .queryParam("id", "1") // /technique?id=1
//                )
//                .andExpect(status().isOk())
//                .andExpect(view().name("technique"))
//                .andExpect(model().attribute("one_technique",
//                        Matchers.samePropertyValuesAs(new TechniqueFormViewModel(
//                                1, "Dollyo Chagi", Type.KICK, "Powerful kicking technique", false
//                        ), "students")
//                ))
//                .andReturn();
//
//        var technique = (TechniqueFormViewModel) mvcResult.getModelAndView().getModel().get("one_technique");
//        var techniquesStudents = technique.getStudents();
//        assertEquals(5, techniquesStudents.size());
//        MatcherAssert.assertThat(techniquesStudents, containsInAnyOrder(
//                Matchers.samePropertyValuesAs(new StudentFormViewModel(1, "John Doe", LocalDate.of(2023, 1, 1), false)),
//                Matchers.samePropertyValuesAs(new StudentFormViewModel(2, "Jane Smith", LocalDate.of(2023, 2, 15), false)),
//                Matchers.samePropertyValuesAs(new StudentFormViewModel(5, "Charlie Brown", LocalDate.of(2023, 5, 30), false)),
//                Matchers.samePropertyValuesAs(new StudentFormViewModel(7, "Eddie Murphy", LocalDate.of(2023, 7, 30), false)),
//                Matchers.samePropertyValuesAs(new StudentFormViewModel(10, "Hannah Montana", LocalDate.of(2023, 10, 30), false))
//        ));
//    }

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
                                3, "Momtong Jireugi", Type.PUNCH, "Basic punching technique", true
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
}