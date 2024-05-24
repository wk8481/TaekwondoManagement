package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
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


    @Query("SELECT i FROM Instructor i JOIN i.students s WHERE s.id = :studentId AND i.id = :instructorId")
    Optional<Instructor> findByStudentIdAndInstructorId(@Param("studentId") int studentId, @Param("instructorId") int instructorId);

    // Method to find an instructor by the ID of a student associated with them
    @Query("SELECT i FROM Instructor i JOIN i.students s WHERE s.id = :studentId")
    Optional<Instructor> findByStudentId(@Param("studentId") int studentId);

    }