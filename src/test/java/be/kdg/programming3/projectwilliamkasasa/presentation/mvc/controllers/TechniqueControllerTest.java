package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;

import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers.TechniqueController;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.StudentFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.TechniqueFormViewModel;
import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers.StudentController;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsInAnyOrder;
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

    @Test
    public void techniqueViewShouldBeRenderedWithTechniqueAndStudentData() throws Exception {
        var mvcResult = mockMvc.perform(
                        get("/technique")
                                .queryParam("id", "1") // /technique?id=1
                )
                .andExpect(status().isOk())
                .andExpect(view().name("technique"))
                .andExpect(model().attribute("one_technique",
                        /*equals(new TechniqueViewModel(
                                1, "Java", "Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.", Type.LANGUAGE, false,
                                List.of(
                                    new StudentViewModel(1, "William Kasasa", LocalDate.of(2021, 9, 1), false),
                                    new StudentViewModel(2, "Lars Willemsens", LocalDate.of(2021, 9, 1), false),
                                    new StudentViewModel(3, "Jens Van Liefferinge", LocalDate.of(2021, 9, 1), false)
                                )
                        ))*/
                        Matchers.samePropertyValuesAs(new TechniqueFormViewModel(
                                1, "Dollyo Chagi", Type.KICK, "Powerful kicking technique", false
                        ), "students")
                ))
                .andReturn();

        var technique = (TechniqueFormViewModel) mvcResult.getModelAndView().getModel().get("one_technique");
        var techniquesStudents = technique.getStudents();
        assertEquals(2, techniquesStudents.size());
        MatcherAssert.assertThat(techniquesStudents, containsInAnyOrder(
                Matchers.samePropertyValuesAs(new StudentFormViewModel(1, "John Doe", LocalDate.of(2023, 1, 1), false)),
                Matchers.samePropertyValuesAs(new StudentFormViewModel(3, "Bob Johnson", LocalDate.of(2023, 3, 20), false))
        ));
    }

    @Test
    @WithUserDetails("wk8481")
    public void techniqueViewShouldAllowModificationIfInstructorSignedIn() throws Exception {
        var mvcResult = mockMvc.perform(
                        get("/technique")
                                .queryParam("id", "1") // /technique?id=1
                )
                .andExpect(status().isOk())
                .andExpect(view().name("technique"))
                .andExpect(model().attribute("one_technique",
                        Matchers.samePropertyValuesAs(new TechniqueFormViewModel(
                                1, "Dollyo Chagi", Type.KICK, "Powerful Kicking Technique", true
                        ), "students")
                ))
                .andReturn();


        var technique = (TechniqueFormViewModel) mvcResult.getModelAndView().getModel().get("one_technique");
        var techniquesStudents = technique.getStudents();
        assertEquals(3, techniquesStudents.size());


    }
}