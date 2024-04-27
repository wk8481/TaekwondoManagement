package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.repository.StudentRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

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


}