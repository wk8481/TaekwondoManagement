package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Instructor} entities using Spring Data JPA.
 * This interface extends {@link JpaRepository}, providing basic CRUD operations out of the box.
 *
 * @see JpaRepository
 */
//@Profile("spring-data")
public interface InstructorRepo extends JpaRepository<Instructor, Integer> {
// No additional methods are required for basic CRUD operations,
// as they are provided by JpaRepository.
}