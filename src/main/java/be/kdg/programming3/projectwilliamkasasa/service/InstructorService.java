package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.InstructorDto;
import be.kdg.programming3.projectwilliamkasasa.repository.InstructorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Implementation of the {@link InstructorService} using Spring Data JPA.
 * This service provides CRUD operations for {@link Instructor} entities.
 *
 */

@Service
public class InstructorService {

    private final InstructorRepo instructorRepo;
    private final Logger logger = LoggerFactory.getLogger(InstructorService.class);

    /**
     * Constructs a new {@code JPAInstructorServiceImpl} instance.
     *
     * @param instructorRepo The repository for handling {@link Instructor} entities.
     */
    @Autowired
    public InstructorService(InstructorRepo instructorRepo) {
        logger.info("Creating SpringDataInstructorRepo");
        this.instructorRepo = instructorRepo;
    }

    public boolean isInstructorAssignedToStudent(int studentId, int instructorId) {
        logger.info("Checking if instructor is assigned to student");
        return instructorRepo.findByStudentIdAndInstructorId(studentId, instructorId)
                .isEmpty();
    }


    /**
     * Converts an {@link Instructor} entity to an {@link InstructorDto}.
     *
     * @param instructor The instructor entity to convert.
     * @return The corresponding instructor DTO.
     */
    private InstructorDto convertToDto(Instructor instructor) {
        return new InstructorDto(instructor.getId(), instructor.getName(), instructor.getTitle());
    }

    public Instructor getUserByName(String username) {
        logger.info("Getting user by name");
        return instructorRepo.findByUsername(username);
    }

    public Optional<Instructor> getInstructorByStudentId(int studentId) {
        logger.info("Getting instructor by student id");
        return instructorRepo.findByStudentId(studentId);
    }
}
