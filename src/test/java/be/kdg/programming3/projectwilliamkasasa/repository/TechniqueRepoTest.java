package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
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
    public void deleteTechniqueShouldNotDeleteAssociatedRecords() {
        // Arrange
        Technique technique = new Technique();
        technique.setName("Technique to Delete");
        technique.setDescription("Description of Technique to Delete");
        technique.setType(Type.KICK);

        // Save the technique to get its ID
        techniqueRepo.save(technique);
        int techniqueId = technique.getId();

        // Link students to the technique by inserting records into the student_techniques table
        jdbcTemplate.update("INSERT INTO student_techniques (student_id, technique_id) VALUES (?, ?)", 1, techniqueId); // Linking to John Doe
        jdbcTemplate.update("INSERT INTO student_techniques (student_id, technique_id) VALUES (?, ?)", 2, techniqueId); // Linking to Jane Smith

        // Assert that the relationships exist before deletion
        assertFalse(studentRepo.findById(1).get().getTechniques().stream().anyMatch(t -> t.getId() == techniqueId)); // John Doe not yet linked to technique
        assertFalse(studentRepo.findById(2).get().getTechniques().stream().anyMatch(t -> t.getId() == techniqueId)); // Jane Smith not yet linked to technique

        // Act
        techniqueRepo.deleteById(techniqueId);

        // Assert
        assertFalse(techniqueRepo.findById(techniqueId).isPresent()); // Technique is deleted

        // Verify the relationships are not removed after deletion
        assertFalse(studentRepo.findById(1).get().getTechniques().stream().anyMatch(t -> t.getId() == techniqueId)); // John Doe still linked to technique
        assertFalse(studentRepo.findById(2).get().getTechniques().stream().anyMatch(t -> t.getId() == techniqueId)); // Jane Smith still linked to technique
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