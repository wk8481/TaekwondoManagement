package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.exception.CustomDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Student} entities using Spring Data JPA.
 * This interface extends {@link JpaRepository}, providing basic CRUD operations out of the box.
 *
 * @see JpaRepository
 */
//@Profile("spring-data")
public interface StudentRepo extends JpaRepository<Student, Integer> {

    /**
     * Custom query to find a student by name.
     *
     * @param name The name of the student to search for.
     * @return The found student, or null if not found.
     * @throws CustomDataAccessException If there is an issue with data access.
     */
    @Query("SELECT s FROM Student s WHERE s.name = :name")
    Student findByName(@Param("name") String name) throws CustomDataAccessException;

    @Query("""
    select student from Student student
            left join fetch student.techniques studentTechniques
            left join fetch studentTechniques.technique
            where student.id = :studentId
    """)
    Optional<Student> findByIdWithTechniques(Integer studentId);

    @Query("""
    select student from Student student
             left join fetch student.techniques studentTechniques
             where student.id = :studentId
    """)
    Optional<Student> findByIdWithStudentTechniques(Integer studentId);



    @Query("""
    select student from Student student
            left join fetch student.techniques studentTechniques
            left join fetch studentTechniques.technique
    """)

    List<Student> findAllWithTechniques();

//        List<Student> getStudentsByNameContainingIgnoreCaseOrStartWith(String searchTerm1, LocalDate searchTerm2);
//
//
//    List<Student> getStudentsByNameLike(String searchTerm);


        List<Student> getStudentsByNameLike(String searchTerm);
        List<Student> getStudentsByStartDate(LocalDate startDate);
    }







