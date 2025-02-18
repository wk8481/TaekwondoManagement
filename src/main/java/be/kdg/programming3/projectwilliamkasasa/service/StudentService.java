package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Instructor;
import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import be.kdg.programming3.projectwilliamkasasa.repository.InstructorRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.StudentTechniqueRepo;
import be.kdg.programming3.projectwilliamkasasa.repository.TechniqueRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Implementation of the {@link StudentService} using Spring Data JPA.
 * This service provides CRUD operations for {@link Student} entities.
 *
 * @author William Kasasa
 * @version 1.0
 */

@Service
public class StudentService {

    private final StudentRepo studentRepo;
    private final InstructorRepo instructorRepo;
    private final StudentTechniqueRepo studentTechniqueRepo;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);



    /**
     * Constructs a new {@code JPAStudentServiceImpl} instance.
     *
     * @param studentRepo          The repository for handling {@link Student} entities.
     * @param studentTechniqueRepo The repository for handling {@link StudentTechnique} entities.
     */

    @Autowired
    public StudentService(StudentRepo studentRepo, InstructorRepo instructorRepo, StudentTechniqueRepo studentTechniqueRepo, TechniqueRepo techniqueRepo) {
        this.studentRepo = studentRepo;
        this.instructorRepo = instructorRepo;

        this.studentTechniqueRepo = studentTechniqueRepo;
    }




    /**
     * Retrieves a list of all students.
     *
     * @return The list of students.
     */

    public List<Student> getStudentsWithTechniques() {
        logger.info("Getting students...");
        return studentRepo.findAllWithTechniquesAndInstructor();
    }

    /**
     * Retrieves a student by their ID.
     *
     * @param id The ID of the student to retrieve.
     * @return An optional containing the student if found, or an empty optional.
     */

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

    public Student updateStudent(Student student) {
        logger.info("Updating student with id {}, name {}, and start date {}", student.getId(), student.getName(), student.getStartDate());
        return studentRepo.save(student);
    }


    public Student getStudentWithTechniques(int id) {
        return studentRepo.findByIdWithTechniques(id).orElse(null);
    }

    /**
     * Deletes a student by their ID.
     *
     * @param id The ID of the student to delete.
     */

    @Transactional
    @CacheEvict(value = "search-students", allEntries = true)
    public boolean deleteStudent(int id) {
        var student = studentRepo.findByIdWithStudentTechniques(id);
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




    @Cacheable("search-students")
    public List<Student> searchStudentsByNameLike(String searchTerm) {
        return studentRepo.getStudentsByNameLike("%" + searchTerm + "%");
    }




    @Transactional
    @CacheEvict(value = "search-students", allEntries = true)
    public Student addStudent(String name, LocalDate startDate, Integer instructorId) { // Use Integer instead of int
        var student = new Student(name, startDate);

        // Check if instructorId is not null
        if (instructorId != null) {
            // Fetch the instructor entity along with its students collection
            Instructor instructor = instructorRepo.findByIdWithStudents(instructorId)
                    .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

            // Set the fetched instructor to the student
            student.setInstructor(instructor);
        }

        // Save the student entity
        Student savedStudent = studentRepo.save(student);

        // Initialize techniques with an empty ArrayList if null
        if (savedStudent.getTechniques() == null) {
            savedStudent.setTechniques(new ArrayList<>());
        }

        return savedStudent;
    }






    @CacheEvict(value = "search-students", allEntries = true)
    public boolean changeStudentName(int id, String newName) {
        var student = studentRepo.findById(id).orElse(null);
        if (student == null) {
            return false;
        }
        student.setName(newName);
        studentRepo.save(student);
        return true;
    }

    @CacheEvict(value = "search-students", allEntries = true)
    public boolean changeStudentStartDate(int id, LocalDate newStartDate) {
        var student = studentRepo.findById(id).orElse(null);
        if (student == null) {
            return false;
        }
        student.setStartDate(newStartDate);
        studentRepo.save(student);
        return true;
    }


}