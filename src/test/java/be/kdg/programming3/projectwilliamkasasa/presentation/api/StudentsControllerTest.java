package be.kdg.programming3.projectwilliamkasasa.presentation.api;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.NewStudentDto;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.StudentDto;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.UpdateStudentStartDateDto;
import be.kdg.programming3.projectwilliamkasasa.repository.InstructorRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentRepo;

import be.kdg.programming3.projectwilliamkasasa.repository.StudentTechniqueRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class StudentsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepo studentRepo;


    @Autowired
    private InstructorRepo instructorRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private int createdStudentId;
    private int createdInstructorId;
    @Autowired
    private StudentTechniqueRepo studentTechniqueRepo;


    @Test
    public void getInstructorOfStudentShouldReturnNotFoundForNonExistentStudent() throws Exception {
        mockMvc.perform(
                        get("/api/students/{id}/instructor", 100)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getInstructorOfStudentShouldReturnNoContentIfNoAssignedInstructor() throws Exception {

        // Arrange
        var student = new Student("Dummy student", LocalDate.now());
        studentRepo.save(student);

        mockMvc.perform(
                        get("/api/students/{id}/instructor", student.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getInstructorOfStudentShouldReturnOkWithInstructor() throws Exception {
        mockMvc.perform(
                        get("/api/students/{id}/instructor", 5)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void deleteStudentIsNotAllowedIfNotSignedIn() throws Exception {
        mockMvc.perform(
                        delete("/api/students/{id}", 4)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("Sensei")
    public void deleteStudentIsAllowedIfInstructorIsAssigned() throws Exception {
        mockMvc.perform(
                        delete("/api/students/{id}", 6)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/students/{id}", 6)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("TheCEO")
    public void deleteStudentIsAllowedIfAdmin() throws Exception {
        mockMvc.perform(
                        delete("/api/students/{id}", 2)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/students/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("Sensei")
    public void deleteStudentIsNotAllowedIfNotAssigned() throws Exception {
        mockMvc.perform(
                        delete("/api/students/{id}", 7)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/students/{id}", 7)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("TheCEO")
    public void deleteStudentShouldReturnNotFoundIfStudentDoesNotExist() throws Exception {
        mockMvc.perform(
                        delete("/api/students/{id}", 999999)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("Sensei")
    public void addStudentShouldBeBadRequestIfMissingMessageBody() throws Exception {
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("TheCEO")
    public void addStudentShouldBeCreatedIfValidMessageBody() throws Exception {
        var mvcResult = mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(new NewStudentDto("New student", LocalDate.now()
                        ))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("New student"))
                .andExpect(jsonPath("$.startDate").value(LocalDate.now().toString()))
                .andReturn();

        var httpResponseBody = mvcResult.getResponse().getContentAsString();
        int studentId = JsonPath.read(httpResponseBody, "$.id");

        mockMvc.perform(get("/api/students/{id}", studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId));

        mockMvc.perform(delete("/api/students/{id}", studentId)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }


    @Test
    @WithUserDetails("TheCEO")
    public void changeStudentStartDateShouldReturnNoContentIfValidRequest() throws Exception {
        // Arrange
        int studentId = 1;
        LocalDate newStartDate = LocalDate.now().minusDays(5);
        System.out.println("New Start Date: " + newStartDate); // Verify the value of newStartDate

        // Act
        mockMvc.perform(patch("/api/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content("{\"startDate\": \"" + newStartDate.toString() + "\"}"))
                .andExpect(status().isNoContent());

        // Assert - Make a GET request to fetch the updated student information
        mockMvc.perform(get("/api/students/{id}", studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.startDate").value(newStartDate.toString()));
    }

    @Test
    @WithUserDetails("Sensei")
    public void changeStudentStartDateShouldReturnForbiddenIfNotAssigned() throws Exception {
        // Arrange
        int studentId = 3;
        LocalDate originalStartDate = LocalDate.parse("2023-03-20"); // Set the original start date for student ID 1
        LocalDate newStartDate = originalStartDate.minusDays(5);
        System.out.println("New Start Date: " + newStartDate); // Verify the value of newStartDate

        // Act & Assert
        mockMvc.perform(patch("/api/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content("{\"startDate\": \"" + newStartDate.toString() + "\"}"))
                .andExpect(status().isForbidden());

        // Assert - Make a GET request to verify that the start date remains unchanged
        mockMvc.perform(get("/api/students/{id}", studentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.startDate").value(originalStartDate.toString())); // Assert original start date
    }










}



