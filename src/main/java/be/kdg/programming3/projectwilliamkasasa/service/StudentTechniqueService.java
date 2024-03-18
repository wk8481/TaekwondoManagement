package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.repository.StudentTechnqiueRepo;

public class StudentTechniqueService {
    private final StudentTechnqiueRepo studentTechnqiueRepo;

    public StudentTechniqueService(StudentTechnqiueRepo studentTechnqiueRepo) {
        this.studentTechnqiueRepo = studentTechnqiueRepo;
    }

    public boolean isTechniqueLearntByStudent(int studentId, int techniqueId){
        return studentTechnqiueRepo
                .findByStudentIdAndTechniqueId(studentId, techniqueId)
                .isPresent();
    }
}
