package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.TechniqueDto;
import be.kdg.programming3.projectwilliamkasasa.repository.TechniqueRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Profile("spring-data")

@Service
public class TechniqueServiceImpl implements TechniqueService {

    private TechniqueRepo techniqueRepo;

    private Logger logger = LoggerFactory.getLogger(TechniqueServiceImpl.class);

    @Autowired
    public TechniqueServiceImpl(TechniqueRepo techniqueRepo) {
        this.techniqueRepo = techniqueRepo;
    }

    @Override
    public List<Technique> getTechniques() {
        logger.info("Getting techniques...");
        return techniqueRepo.findAll();
    }

    @Override
    public Technique getTechniquesByTypeAndName(Type type, String name) {
       logger.info("Getting techniques by type and name...");
        return techniqueRepo.findByTypeAndName(type, name);
    }

    @Override
    public Technique addTechnique(int id, String name, Type type, String description) {
        logger.info("Adding technique with id {}, name {}, type {}, and description {}", id, name, type, description);
        Technique technique = new Technique(id, name, type, description);
        return techniqueRepo.save(technique);
    }

    @Override
    public Optional<Technique> getTechniqueById(int id) {
        logger.info("Getting technique by id...");
        return techniqueRepo.findById(id);
    }

    @Override
    public Technique updateTechnique(Technique technique) {
        logger.info("Updating technique with id {}, name {}, type {}, and description {}", technique.getId(), technique.getName(), technique.getType(), technique.getDescription());
        return techniqueRepo.save(technique);
    }

    @Override
    public void deleteTechnique(int id) {
        logger.info("Deleting technique with id {} ", id);
        techniqueRepo.deleteById(id);
    }


    @Override
    public Optional<TechniqueDto> getTechniqueDtoById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Technique> getTechniqueByType(Type type) {
        logger.info("Getting technique by type...");
        return techniqueRepo.findByType(type);
    }

    @Override
    public List<Technique> getTechniquesByInstructorId(int instructorId) {
        return null;
    }

    @Override
    public List<Technique> getTechniquesOfStudent(int id) {
        return techniqueRepo.findByStudentId(id);
    }

    @Override
    public List<Technique> searchTechniquesByNameOrDescription(
            String searchTerm) {
            return techniqueRepo
            .getTechniquesByNameLikeOrDescriptionLike(
                    "%" + searchTerm + "%", "%" + searchTerm + "%");
    }




}
