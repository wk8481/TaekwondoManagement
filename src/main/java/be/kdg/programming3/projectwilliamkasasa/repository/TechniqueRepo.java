package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
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
//@Profile("spring-data")
public interface TechniqueRepo extends JpaRepository<Technique, Integer> {

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
     * Custom query to find techniques by student id.
     *
     * @param id The id of the student to search for.
     * @return A list of techniques with the specified student id.
     * @throws DataAccessException If there is an issue with data access.
     */

    @Query("""
    select technique from Technique technique
            left join fetch technique.students studentTechniques
            left join fetch studentTechniques.student student
            where student.id = :id
            """)
    List<Technique> findByStudentId(int id);

    List<Technique> getTechniquesByNameLikeOrDescriptionLike(
            String searchTerm1, String searchTerm2);
}
