package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TechniqueRepoTest {

    @Autowired
    private TechniqueRepo techniqueRepo;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    public void setUp() {
        techniqueRepo = applicationContext.getBean(TechniqueRepo.class);
    }

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
        // There are other ways to compare lists in tests (Hamcrest, AssertJ, ...)
        var students = tech.getStudents()
                .stream()
                .sorted((a1, a2) -> (int) (a1.getId() - a2.getId()))
                .toList();
        assertEquals("William", students.get(0).toString());
        assertEquals(LocalDate.of(2024, 1, 10),
                students.get(0).getStudent().getStartDate());
        assertEquals("John", students.get(1).toString());
        assertEquals(LocalDate.of(2024, 1, 11),
                students.get(1).getStudent().getStartDate());
        assertEquals("Jane", students.get(2).toString());
        assertEquals(LocalDate.of(2024, 1, 12),
                students.get(2).getStudent().getStartDate());
    }

    @Test
    public void testSaveTechniqueWithNullName() {
        // Arrange
        var technique = new Technique();
        technique.setName(null);
        technique.setDescription("This is a test description");
        technique.setType(Type.valueOf("Test type"));

        // Act
        Executable saveTechnique = () -> techniqueRepo.save(technique);

        // Assert
        var exception = assertThrows(DataIntegrityViolationException.class, saveTechnique);
        assertTrue(exception.getMessage().contains("not-null"));
    }

    @Test
    public void deleteTechniqueShouldDeleteAssociatedRecords() {
        // Arrange
        Technique technique = new Technique();
        technique.setName("Technique to Delete");
        technique.setDescription("Description of Technique to Delete");
        technique.setType(Type.KICK);

        // Save the technique to get its ID
        techniqueRepo.save(technique);
        int techniqueId = technique.getId();

        // Act
        techniqueRepo.deleteById(techniqueId);

        // Assert
        assertFalse(techniqueRepo.findById(techniqueId).isPresent());
    }
}
