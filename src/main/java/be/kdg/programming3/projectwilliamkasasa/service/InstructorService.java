package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.InstructorDto;

import java.util.List;
import java.util.Optional;

public interface InstructorService {
    Instructor addInstructor(Instructor instructor);
    List<Instructor> getInstructors();
    Optional<Instructor> getInstructorById(int id);

    Optional<InstructorDto> getInstructorDtoById(int id);

    Instructor updateInstructor(Instructor instructor);
    void deleteInstructor(int id);
}
