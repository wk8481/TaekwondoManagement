package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for managing {@link Instructor} entities using Spring Data JPA.
 * This interface extends {@link JpaRepository}, providing basic CRUD operations out of the box.
 *
 * @see JpaRepository
 */
//@Profile("spring-data")
public interface InstructorRepo extends JpaRepository<Instructor, Integer> {
Instructor findByUsername(String username);

    @Query("""
    select instructor from Instructor instructor
             left join fetch instructor.students
             where instructor.id = :instructorId
    """)
    Optional<Instructor> findByIdWithStudents(Integer instructorId);
}