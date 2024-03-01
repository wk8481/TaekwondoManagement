package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.InstructorDto;
import be.kdg.programming3.projectwilliamkasasa.repository.InstructorRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Implementation of the {@link InstructorService} using Spring Data JPA.
 * This service provides CRUD operations for {@link Instructor} entities.
 *
 */

@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepo instructorRepo;
    private final Logger logger = LoggerFactory.getLogger(InstructorServiceImpl.class);

    /**
     * Constructs a new {@code JPAInstructorServiceImpl} instance.
     *
     * @param instructorRepo The repository for handling {@link Instructor} entities.
     */
    @Autowired
    public InstructorServiceImpl(InstructorRepo instructorRepo) {
        logger.info("Creating SpringDataInstructorRepo");
        this.instructorRepo = instructorRepo;
    }

    /**
     * Adds a new instructor.
     *
     * @param instructor The instructor to be added.
     * @return The added instructor.
     */
    @Override
    public Instructor addInstructor(Instructor instructor) {
        logger.info("Adding instructor with id {}, name {}, and start date {}", instructor.getId(), instructor.getTitle(), instructor.getName());
        return instructorRepo.save(instructor);
    }

    /**
     * Retrieves a list of all instructors.
     *
     * @return The list of instructors.
     */
    @Transactional
    @Override
    public List<Instructor> getInstructors() {
        logger.info("Getting instructors...");
        return instructorRepo.findAll();
    }

    /**
     * Retrieves an instructor by their ID.
     *
     * @param id The ID of the instructor to retrieve.
     * @return An optional containing the instructor if found, or an empty optional.
     */
    @Override
    public Optional<Instructor> getInstructorById(int id) {
        logger.info("Getting instructor by id...");
        return instructorRepo.findById(id);
    }

    /**
     * Retrieves an instructor DTO by their ID.
     *
     * @param id The ID of the instructor to retrieve.
     * @return An optional containing the instructor DTO if found, or an empty optional.
     */
    @Override
    public Optional<InstructorDto> getInstructorDtoById(int id) {
        logger.info("Getting instructor DTO by id...");
        Optional<Instructor> instructorOptional = instructorRepo.findById(id);
        return instructorOptional.map(this::convertToDto);
    }

    /**
     * Updates an existing instructor.
     *
     * @param instructor The updated instructor information.
     * @return The updated instructor.
     */
    @Override
    public Instructor updateInstructor(Instructor instructor) {
        logger.info("Updating instructor with id {}, name {}, and start date {}", instructor.getId(), instructor.getTitle(), instructor.getName());
        return instructorRepo.save(instructor);
    }

    /**
     * Deletes an instructor by their ID.
     *
     * @param id The ID of the instructor to delete.
     */
    @Override
    public void deleteInstructor(int id) {
        logger.info("Deleting instructor with id {} ", id);
        instructorRepo.deleteById(id);
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
}
