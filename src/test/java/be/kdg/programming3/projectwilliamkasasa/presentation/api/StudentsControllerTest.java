package be.kdg.programming3.projectwilliamkasasa.presentation.api;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.NewStudentDto;
import be.kdg.programming3.projectwilliamkasasa.repository.InstructorRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentRepo;

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

    @BeforeEach
    public void setupEach() {
        // Create a student
        var createdStudent = studentRepo.save(new Student("Dummy student", LocalDate.now()));
        createdStudentId = createdStudent.getId();

        // Find the instructor by username
        var createdInstructor = instructorRepo.findByUsername("Sensei");

        // If instructor not found, create a new one
        if (createdInstructor == null) {
            createdInstructor = instructorRepo.save(new Instructor("wk8481", "password"));
        }

        // Assign the student to the instructor
        createdStudent.setInstructor(createdInstructor);
        studentRepo.save(createdStudent);

        // Update the createdInstructorId
        createdInstructorId = createdInstructor.getId();
    }

    @AfterEach
    public void tearDownEach() {
        studentRepo.deleteById(createdStudentId);
        instructorRepo.deleteById(createdInstructorId);
    }

    @Test
    public void getInstructorOfStudentShouldReturnNotFoundForNonExistentStudent() throws Exception {
        mockMvc.perform(
                        get("/api/students/{id}/instructor", 100)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getInstructorOfStudentShouldReturnNoContentIfNoAssignedInstructor() throws Exception {
        mockMvc.perform(
                        get("/api/students/{id}/instructor", 5)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getInstructorOfStudentShouldReturnOkWithInstructor() throws Exception {
        mockMvc.perform(
                        get("/api/students/{id}/instructor", createdStudentId)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(createdInstructorId)));
    }

    @Test
    public void deleteStudentIsNotAllowedIfNotSignedIn() throws Exception {
        mockMvc.perform(
                        delete("/api/students/{id}", createdStudentId)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("Sensei")
    public void deleteStudentIsAllowedIfInstructorIsAssigned() throws Exception {
        mockMvc.perform(
                        delete("/api/students/{id}", createdStudentId)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/students/{id}", createdStudentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("TheCEO")
    public void deleteStudentIsAllowedIfAdmin() throws Exception {
        mockMvc.perform(
                        delete("/api/students/{id}", createdStudentId)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/students/{id}", createdStudentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("Sensei")
    public void deleteStudentIsNotAllowedIfNotAssigned() throws Exception {
        mockMvc.perform(
                        delete("/api/students/{id}", 2)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/students/{id}", 2)
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
    @WithUserDetails("Sensei")
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
}



