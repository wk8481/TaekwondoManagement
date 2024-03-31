package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentTechniqueRepo extends JpaRepository<StudentTechnique, Long> {
    Optional<StudentTechnique> findByStudentIdAndTechniqueId(int studentId, int techniqueId);
}
