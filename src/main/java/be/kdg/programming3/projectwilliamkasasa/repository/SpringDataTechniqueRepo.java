package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for managing {@link Technique} entities using Spring Data JPA.
 * This interface extends {@link JpaRepository}, providing basic CRUD operations out of the box.
 *
 * @see JpaRepository
 */
@Profile("spring-data")
public interface SpringDataTechniqueRepo extends JpaRepository<Technique, Integer> {

    /**
     * Custom query to find a technique by type and name.
     *
     * @param type The type of the technique.
     * @param name The name of the technique.
     * @return The found technique, or null if not found.
     * @throws DataAccessException If there is an issue with data access.
     */
    @Query("SELECT t FROM Technique t WHERE t.name = :name AND t.type = :type")
    Technique findByTypeAndName(@Param("type") Type type, @Param("name") String name) throws DataAccessException;

    /**
     * Custom query to find techniques by type.
     *
     * @param type The type of techniques to search for.
     * @return A list of techniques with the specified type.
     * @throws DataAccessException If there is an issue with data access.
     */
    @Query("SELECT t FROM Technique t WHERE t.type = :type")
    List<Technique> findByType(@Param("type") Type type) throws DataAccessException;

    /**
     * Custom query to find techniques by an instructor's ID.
     *
     * @param instructorId The ID of the instructor to search for.
     * @return A list of techniques associated with the specified instructor.
     * @throws DataAccessException If there is an issue with data access.
     */
//    @Query("SELECT t FROM Technique t WHERE t.instructor.id = :instructorId")
//    List<Technique> findByInstructorId(@Param("instructorId") Integer instructorId) throws DataAccessException;
}
