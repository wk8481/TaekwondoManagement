package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentTechniqueRepo;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class StudentServiceUnitTest {

    @MockBean
    private StudentRepo studentRepo;

    @MockBean
    private StudentTechniqueRepo studentTechniqueRepo;

    @Autowired
    private StudentService studentService;

    @Test
    void updateStartDateWhenStudentDoesntExist() {
        // Arrange
        given(studentRepo.findById(1000)).willReturn(Optional.empty());

        // Act
        var updateSucceeded = studentService.changeStudentStartDate(1000, LocalDate.of(2021, 1, 1));

        // Assert
        assertFalse(updateSucceeded);
        verify(studentRepo, never()).save(any());

    }

    @Test
    void updateStartDateWhenStudentExists(){
        // Arrange
        var student = new Student("William", LocalDate.of(2024, 1, 10));
        student.setId(1000);
        given(studentRepo.findById(1000)).willReturn(Optional.of(student));

        // Act
        var updateSucceeded = studentService.changeStudentStartDate(1000, LocalDate.of(2021, 1, 1));

        // Assert
        assertTrue(updateSucceeded);
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepo/*, times(1)*/).save(studentCaptor.capture());
        assertEquals(LocalDate.of(2021, 1, 1), student.getStartDate());
    }


    @Test
    void deleteStudentWhenStudentDoesntExist() {
        // Arrange
        given(studentRepo.findById(1000)).willReturn(Optional.empty());

        // Act
        var deleteSucceeded = studentService.deleteStudent(1000);

        // Assert
        assertFalse(deleteSucceeded);
        verify(studentRepo, never()).deleteById(1000);
    }

    @Test
    void deleteStudentWhenStudentExists() {
        // Arrange
        var student = new Student("William", LocalDate.of(2024, 1, 10));
        student.setId(1000);
        var technique = new StudentTechnique(); // Create a technique instance
        var techniques = Collections.singletonList(technique); // Put the technique into a list
        student.setTechniques(techniques); // Set the list of techniques to the student

        given(studentRepo.findByIdWithStudentTechniques(1000)).willReturn(Optional.of(student));

        // Act
        var deleteSucceeded = studentService.deleteStudent(1000);

        // Assert
        assertTrue(deleteSucceeded);
        verify(studentTechniqueRepo).deleteAll(techniques); // Ensure techniques are deleted
        verify(studentRepo).deleteById(1000);
    }





}