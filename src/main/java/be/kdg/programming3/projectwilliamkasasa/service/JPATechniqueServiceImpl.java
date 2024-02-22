package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.domain.Type;
import be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels.TechniqueDto;
import be.kdg.programming3.projectwilliamkasasa.repository.SpringDataTechniqueRepo;
import be.kdg.programming3.projectwilliamkasasa.service.TechniqueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Profile("spring-data")

@Service
public class JPATechniqueServiceImpl implements TechniqueService {

    private SpringDataTechniqueRepo springDataTechniqueRepo;

    private Logger logger = LoggerFactory.getLogger(JPATechniqueServiceImpl.class);

    @Autowired
    public JPATechniqueServiceImpl(SpringDataTechniqueRepo springDataTechniqueRepo) {
        this.springDataTechniqueRepo = springDataTechniqueRepo;
    }

    @Override
    public List<Technique> getTechniques() {
        logger.info("Getting techniques...");
        return springDataTechniqueRepo.findAll();
    }

    @Override
    public Technique getTechniquesByTypeAndName(Type type, String name) {
       logger.info("Getting techniques by type and name...");
        return springDataTechniqueRepo.findByTypeAndName(type, name);
    }

    @Override
    public Technique addTechnique(int id, String name, Type type, String description) {
        logger.info("Adding technique with id {}, name {}, type {}, and description {}", id, name, type, description);
        Technique technique = new Technique(id, name, type, description);
        return springDataTechniqueRepo.save(technique);
    }

    @Override
    public Optional<Technique> getTechniqueById(int id) {
        logger.info("Getting technique by id...");
        return springDataTechniqueRepo.findById(id);
    }

    @Override
    public Technique updateTechnique(Technique technique) {
        logger.info("Updating technique with id {}, name {}, type {}, and description {}", technique.getId(), technique.getName(), technique.getType(), technique.getDescription());
        return springDataTechniqueRepo.save(technique);
    }

    @Override
    public void deleteTechnique(int id) {
        logger.info("Deleting technique with id {} ", id);
        springDataTechniqueRepo.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByTechniqueId(int id) {
        return null;
    }

    @Override
    public Optional<TechniqueDto> getTechniqueDtoById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Technique> getTechniqueByType(Type type) {
        logger.info("Getting technique by type...");
        return springDataTechniqueRepo.findByType(type);
    }

    @Override
    public List<Technique> getTechniquesByInstructorId(int instructorId) {
        return null;
    }

//    @Override
//    public List<Technique> getTechniquesByInstructorId(int instructorId) {
//        logger.info("Getting techniques by instructor id...");
//        return springDataTechniqueRepo.findByInstructorId(instructorId);
//    }
//



}
