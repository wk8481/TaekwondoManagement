package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.repository.StudentTechniqueRepo;
import org.springframework.stereotype.Service;

@Service
public class StudentTechniqueService {
    private final StudentTechniqueRepo studentTechniqueRepo;

    public StudentTechniqueService(StudentTechniqueRepo studentTechniqueRepo) {
        this.studentTechniqueRepo = studentTechniqueRepo;

    }

    public boolean isTechniqueLearntByStudent(int studentId, int techniqueId){
        return studentTechniqueRepo
                .findByStudentIdAndTechniqueId(studentId, techniqueId)
                .isEmpty();
    }
}
