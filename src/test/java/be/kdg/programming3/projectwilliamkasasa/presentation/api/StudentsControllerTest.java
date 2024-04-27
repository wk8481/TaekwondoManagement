package be.kdg.programming3.projectwilliamkasasa.presentation.api;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentRepo;

import be.kdg.programming3.projectwilliamkasasa.repository.StudentTechniqueRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.TechniqueRepo;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class StudentsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TechniqueRepo techniqueRepo;

    @Autowired
    private StudentTechniqueRepo studentTechniqueRepo;

    private int createdStudentId;
    private int createdStudentTechniqueId;

    @BeforeEach
    public void setupEach() {
        var createdStudent = studentRepo.save(
                new Student("Dummy student", LocalDate.now()));
        createdStudentId = createdStudent.getId();
        // Let's make this student know a technique.
//        var createdStudentTechnique = studentTechniqueRepo.save(
//                new StudentTechnique(createdStudent,
//                        techniqueRepo.findByType().orElseThrow())
////        );
//        createdStudentTechniqueId = createdStudentTechnique.getId();
    }

}