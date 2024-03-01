package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.StudentDto;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentTechniqueRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Implementation of the {@link StudentService} using Spring Data JPA.
 * This service provides CRUD operations for {@link Student} entities.
 *
 * @author William Kasasa
 * @version 1.0
 */

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final StudentTechniqueRepo studentTechniqueRepo;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    /**
     * Constructs a new {@code JPAStudentServiceImpl} instance.
     *
     * @param studentRepo          The repository for handling {@link Student} entities.
     * @param studentTechniqueRepo The repository for handling {@link StudentTechnique} entities.
     */

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo, StudentTechniqueRepo studentTechniqueRepo) {
        this.studentRepo = studentRepo;
        this.studentTechniqueRepo = studentTechniqueRepo;
    }

    /**
     * Adds a new student.
     *
     * @param student The student to be added.
     * @return The added student.
     */
    @Override
    public Student addStudent(Student student) {
        logger.info("Adding student with id {}, name {}, and start date {}", student.getId(), student.getName(), student.getStart());
        return studentRepo.save(student);
    }

    /**
     * Retrieves a list of all students.
     *
     * @return The list of students.
     */
    @Override
    public List<Student> getStudents() {
        logger.info("Getting students...");
        return studentRepo.findAll();
    }

    /**
     * Retrieves a student by their ID.
     *
     * @param id The ID of the student to retrieve.
     * @return An optional containing the student if found, or an empty optional.
     */
    @Override
    public Student getStudentById(int id) {
        logger.info("Getting student by id...");
        return studentRepo.findById(id).orElse(null);
    }

    /**
     * Updates an existing student.
     *
     * @param student The updated student information.
     * @return The updated student.
     */
    @Override
    public Student updateStudent(Student student) {
        logger.info("Updating student with id {}, name {}, and start date {}", student.getId(), student.getName(), student.getStart());
        return studentRepo.save(student);
    }

    /**
     * Deletes a student by their ID.
     *
     * @param id The ID of the student to delete.
     */

    @Transactional
    @Override
    public boolean deleteStudent(int id) {
        var student = studentRepo.findByIdWithTechniques(id);
        if (student.isEmpty()) {
            return false;
        }
        // These additional delete statements can be avoided, for example,
        // by adding a true `ON DELETE CASCADE` at the database level,
        // which is _very_ different from `cascade = CascadeType.REMOVE`.
        // To create such a `ON DELETE CASCADE`, you can add this
        // to the association in the StudentTechnique class:
        //     @OnDelete(action = OnDeleteAction.CASCADE)
        studentTechniqueRepo.deleteAll(student.get().getTechniques());

        studentRepo.deleteById(id);
        return true;


    }







    @Override
    public Student addStudentList(int id, String name, LocalDate start) {
        logger.info("Adding student with id {} of name {} and start date {}", id, name, start);
        return studentRepo.save(new Student(id, name, start));
    }

    @Override
    public Student addStudentListEM(int id, String name, LocalDate start) {
        return null;
    }

    @Override
    public void addTechniquesToStudent(int studentId, List<Technique> techniques) {

    }

    @Override
    public void removeTechniquesFromStudent(int studentId, List<Technique> techniques) {

    }

    @Override
    public List<Technique> getTechniquesByStudentId(int studentId) {
        return null;
    }

    @Override
    public List<Student> getStudentsByInstructorId(int instructorId) {
        return null;
    }
    /**
     * Retrieves a list of students by the instructor ID.
     *
     * @param instructorId The ID of the instructor to filter by.
     * @return The list of students with the specified instructor ID.
     */
//    @Override
//    public List<Student> getStudentsByInstructorId(int instructorId) {
//        return springDataStudentRepo.findByInstructorId(instructorId);
//    }

    /**
     * Retrieves a student by their name.
     *
     * @param name The name of the student to retrieve.
     * @return The student with the specified name.
     */
    @Override
    public Student getStudentByName(String name) {
        return studentRepo.findByName(name);
    }

    /**
     * Retrieves a student DTO by their ID.
     *
     * @param id The ID of the student to retrieve.
     * @return An optional containing the student DTO if found, or an empty optional.
     */
    @Override
    public Optional<StudentDto> getStudentDtoById(int id) {
        return Optional.empty();
    }

    @Override
    public Student getStudentWithTechniques(int id) {
        return studentRepo.findByIdWithTechniques(id).orElse(null);
    }
}