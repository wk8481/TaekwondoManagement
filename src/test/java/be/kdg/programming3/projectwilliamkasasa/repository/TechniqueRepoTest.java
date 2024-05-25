package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TechniqueRepoTest {

    @Autowired
    private TechniqueRepo techniqueRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentTechniqueRepo studentTechniqueRepo;

    @Test
    public void findByIdWithStudentsShouldFetchRelatedData() {
        // Arrange

        // Act
        var techOptional = techniqueRepo.findByIdWithStudents(1);

        // Assert
        assertTrue(techOptional.isPresent());
        var tech = techOptional.get();
        assertEquals(1, tech.getId());
        assertEquals("Dollyo Chagi", tech.getName());

        // Sort and extract student names for comparison
        var students = tech.getStudents()
                .stream()
                .sorted((a1, a2) -> (int) (a1.getId() - a2.getId()))
                .toList();

        // Assuming StudentTechnique has a method getStudent() that returns a Student object
        // and Student has a method getName() that returns the name of the student
        assertEquals("John Doe", students.get(0).getStudent().getName());
        assertEquals(LocalDate.of(2023, 1, 1), students.get(0).getStudent().getStartDate());

        assertEquals("Jane Smith", students.get(1).getStudent().getName());
        assertEquals(LocalDate.of(2023, 2, 15), students.get(1).getStudent().getStartDate());

    }


    @Test
    public void testSaveTechniqueWithNullName() {
        // Arrange
        var technique = new Technique();
        technique.setName(null);
        technique.setDescription("This is a test description");
        technique.setType(Type.valueOf("KICK"));

        // Act
        Executable saveTechnique = () -> techniqueRepo.save(technique);

        // Assert
        var exception = assertThrows(DataIntegrityViolationException.class, saveTechnique);
        assertTrue(exception.getMessage().contains("not-null"));
    }



    @Test
    @Transactional
    public void deleteOneTechnique() {
        // Create a new technique
        Technique technique = techniqueRepo.findById(1).orElse(null);

       //Act
        List<Integer> ids= technique.getStudents().stream().map(StudentTechnique::getId).collect(Collectors.toList());
        for (Integer id : ids) {
            studentTechniqueRepo.deleteById(id);
        }

        // Delete the technique
        techniqueRepo.deleteById(1);

        // Check if the technique is no longer present in the repository
        assertEquals(2, techniqueRepo.findAll().size());
    }


//// Assert that the relationships exist before deletion
//assertTrue(studentRepo.findById(1).get().getTechniques().stream().anyMatch(t -> t.getId() == techniqueId)); // John Doe linked to technique
//assertTrue(studentRepo.findById(2).get().getTechniques().stream().anyMatch(t -> t.getId() == techniqueId)); // Jane Smith linked to technique
//
//// Act
//techniqueRepo.deleteById(techniqueId);
//
//// Assert
//assertFalse(techniqueRepo.findById(techniqueId).isPresent()); // Technique is deleted
//
//// Verify the relationships are not removed after deletion
//assertTrue(studentRepo.findById(1).get().getTechniques().stream().anyMatch(t -> t.getId() == techniqueId)); // John Doe still linked to technique
//assertTrue(studentRepo.findById(2).get().getTechniques().stream().anyMatch(t -> t.getId() == techniqueId)); // Jane Smith still linked to technique




}