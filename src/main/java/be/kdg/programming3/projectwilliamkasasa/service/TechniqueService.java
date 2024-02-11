package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels.TechniqueDto;

import java.util.List;
import java.util.Optional;

public interface TechniqueService {
    List<Technique> getTechniques();

    Technique getTechniquesByTypeAndName(Type type, String name);

    Technique addTechnique(int id, String name, Type type, String description);

   Optional<Technique> getTechniqueById(int id);

        Technique updateTechnique(Technique technique);

        void deleteTechnique(int id);

    List<Student> getStudentsByTechniqueId(int id);

    Optional<TechniqueDto> getTechniqueDtoById(int id);

    List <Technique> getTechniqueByType(Type type);

        List<Technique> getTechniquesByInstructorId(int instructorId);




}




//    void updateTechnique(Technique technique);
//
//    void deleteTechnique(int techniqueId);
//}
