//package be.kdg.programming3.projectwilliamkasasa.presentation.mvc.controllers;
//
//import be.kdg.programming3.projectwilliamkasasa.presentation.mvc.viewmodels.StudentFormViewModel;
//import be.kdg.programming3.projectwilliamkasasa.repository.StudentRepo;
//import org.hamcrest.MatcherAssert;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@AutoConfigureMockMvc
//class StudentControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private StudentRepo studentRepo;
//
//    @Test
//    public void studentViewShouldBeRenderedWithStudentData() throws Exception {
//        var mvcResult = mockMvc.perform(
//                        get("/student")
//                                .queryParam("id", "1") // /student?id=1
//                )
//                .andExpect(status().isOk())
//                .andExpect(view().name("student"))
//                .andExpect(model().attribute("one_student",
//                        /*equals(new StudentViewModel(
//                                1, "William Kasasa", LocalDate.of(2021, 9, 1), false
//                        ))*/
//                        Matchers.samePropertyValuesAs(new StudentFormViewModel(
//                                1, "William Kasasa", LocalDate.of(2021, 9, 1), false
//                        ))
//                ))
//                .andReturn();
//
//        var student = (StudentFormViewModel) mvcResult.getModelAndView().getModel().get("one_student");
//        assertEquals("William Kasasa", student.getName());
//        assertEquals(LocalDate.of(2021, 9, 1), student.getEnrollmentDate());
//        assertFalse(student.isGraduated());
//    }
//
//
//
//}