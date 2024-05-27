package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.repository.InstructorRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepo studentRepo;


    private int testStudentsId;

    @BeforeAll
    void setup() {
        var testStudent = studentRepo.save(new Student("William", LocalDate.of(2024, 1, 10)));
        testStudentsId = testStudent.getId();
    }

    @AfterAll
    void tearDown() {
        studentRepo.deleteById(testStudentsId);
    }


    @Test
    void changeStudentNameShouldReturnTrueForExistingStudentAndUpdateSaidStudent() {
        // Arrange

        // Act
        var result = studentService.changeStudentName(
                testStudentsId, "New name");

        // Assert
        assertTrue(result);
        assertEquals("New name",
                studentRepo.findById(testStudentsId).get().getName());


    }

    @Test
    void changeStudentNameShouldReturnFalseForNonExistingStudent() {
        // Arrange


        // Act
        var result = studentService.changeStudentName(
                9999, "New name");

        // Assert
        assertFalse(result);

        assertTrue(studentRepo.findById(9999).isEmpty());
    }

    @Test
    void addStudentShouldThrowExceptionWhenInstructorNotFound() {
        // Arrange
        String name = "Test Student";
        LocalDate startDate = LocalDate.now();
        int nonExistentInstructorId = 9999;

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> studentService.addStudent(name, startDate, nonExistentInstructorId));
    }






}