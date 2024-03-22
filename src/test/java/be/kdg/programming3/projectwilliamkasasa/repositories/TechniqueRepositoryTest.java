package be.kdg.programming3.projectwilliamkasasa.repositories;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import be.kdg.programming3.projectwilliamkasasa.repository.TechniqueRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TechniqueRepositoryTest {
    @Autowired
    private TechniqueRepo techniqueRepo;

    /**
     * This is just a dummy test!!!
     * Real tests are listed below ...
     */

    @Test
    public void repoShouldContainTwoRecordsTotal() {
        var techniques = techniqueRepo.findAll();
        assertEquals(2, techniques.size());

        // Imagine I'm trying to test something related to updating techniques.
        // var technique = techniqueRepo.findById(1L).orElse(null);
        // technique.setDescription("blablabla");
        // techniqueRepo.save(technique);
        // This is NOT a real test.
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
    public void  testSaveTechniqueWithNullName() {
        var technique = new Technique();
        technique.setName(null);
        technique.setDescription("This is a test description");
        technique.setType(Type.valueOf("Test type"));
        Executable saveTechnique = () -> techniqueRepo.save(technique);
        var exception = assertThrows(DataIntegrityViolationException.class, saveTechnique);
        assertTrue(exception.getMessage().contains("not-null"));
    }


}
