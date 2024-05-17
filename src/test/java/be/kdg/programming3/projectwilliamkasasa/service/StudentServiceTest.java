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

    @Autowired
    private InstructorRepo instructorRepo;

    private int testStudentsId;

    @BeforeAll
    void setup() {
        // Create a student to be used in this test class.
        // Such a student (BeforeAll) should not be modified from within tests.
        // NOTE: I'm NOT actually using this record. This is just a demo.
        var testStudent = studentRepo.save(new Student("William", LocalDate.of(2024, 1, 10)));
        testStudentsId = testStudent.getId();
    }

    @AfterAll
    void tearDown() {
        studentRepo.deleteById(testStudentsId);
    }

    // Make NO assumption about the order of execution of these tests
    @Test
    void changeStudentNameShouldReturnTrueForExistingStudentAndUpdateSaidStudent() {
        // Arrange
        var createdStudent = studentRepo.save(new Student("William", LocalDate.of(2024, 1, 10)));

        // Act
        var result = studentService.changeStudentName(
                createdStudent.getId(), "New name");

        // Assert
        assertTrue(result);
        assertEquals("New name",
                studentRepo.findById(createdStudent.getId()).get().getName());

        // (cleanup)
        studentRepo.deleteById(createdStudent.getId());
    }

    @Test
    void changeStudentNameShouldReturnFalseForNonExistingStudent() {
        // Arrange


        // Act
        var result = studentService.changeStudentName(
                9999, "New name");

        // Assert
        assertFalse(result);
        // This is a bit of a weird test, because the student is deleted.
        // I'm just testing the return value of the service method.
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