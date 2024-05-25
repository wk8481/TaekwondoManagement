package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentTechniqueRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.TechniqueRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Profile("spring-data")

@Service
public class TechniqueService {

    private final TechniqueRepo techniqueRepo;
    private final StudentTechniqueRepo studentTechniqueRepo;

    private final Logger logger = LoggerFactory.getLogger(TechniqueService.class);

    @Autowired
    public TechniqueService(TechniqueRepo techniqueRepo, StudentTechniqueRepo studentTechniqueRepo) {
        this.techniqueRepo = techniqueRepo;
        this.studentTechniqueRepo = studentTechniqueRepo;
    }


    public List<Technique> getTechniques() {
        logger.info("Getting techniques...");
        return techniqueRepo.findAll();
    }


    public Technique getTechniquesByTypeAndName(Type type, String name) {
       logger.info("Getting techniques by type and name...");
        return techniqueRepo.findByTypeAndName(type, name);
    }


    public Technique addTechnique(int id, String name, Type type, String description) {
        logger.info("Adding technique with id {}, name {}, type {}, and description {}", id, name, type, description);
        Technique technique = new Technique(id, name, type, description);
        return techniqueRepo.save(technique);
    }


    public Optional<Technique> getTechniqueById(int id) {
        logger.info("Getting technique by id...");
        return techniqueRepo.findById(id);
    }


    public Technique updateTechnique(Technique technique) {
        logger.info("Updating technique with id {}, name {}, type {}, and description {}", technique.getId(), technique.getName(), technique.getType(), technique.getDescription());
        return techniqueRepo.save(technique);
    }

    public void updateTechniqueType(int id, Type type) {
        logger.info("Updating technique type with id {} to {}", id, type);
        techniqueRepo.findById(id).ifPresent(technique -> {
            technique.setType(type);
            techniqueRepo.save(technique);
        });
    }

    public void updateTechniqueDescription(int id, String description) {
        logger.info("Updating technique description with id {} to {}", id, description);
        techniqueRepo.findById(id).ifPresent(technique -> {
            technique.setDescription(description);
            techniqueRepo.save(technique);
        });
    }


    @Transactional
    public boolean deleteTechnique(int id) {
        var technique = techniqueRepo.findByIdWithStudents(id);
        if (technique.isEmpty()) {
            return false;
        }

        // First, remove any references to the technique from the student_techniques table
        studentTechniqueRepo.deleteAll(technique.get().getStudents());

        // Then, delete the technique
        techniqueRepo.deleteById(id);
        return true;
    }






    public List<Technique> getTechniqueByType(Type type) {
        logger.info("Getting technique by type...");
        return techniqueRepo.findByType(type);
    }



    public Technique getTechniqueWithStudents(int id) {
        return techniqueRepo.findByIdWithStudents(id).orElse(null);
    }


    public List<Technique> getTechniquesOfStudent(int id){
        return techniqueRepo.findByStudentId(id);
    }


    public List<Technique> searchTechniquesByNameOrDescription(
            String searchTerm) {
            return techniqueRepo
            .getTechniquesByNameLikeOrDescriptionLike(
                    "%" + searchTerm + "%", "%" + searchTerm + "%");
    }




}
