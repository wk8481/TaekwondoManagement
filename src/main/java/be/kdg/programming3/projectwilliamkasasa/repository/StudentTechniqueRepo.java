package be.kdg.programming3.projectwilliamkasasa.repository;

import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentTechniqueRepo extends JpaRepository<StudentTechnique, Integer> {
}
