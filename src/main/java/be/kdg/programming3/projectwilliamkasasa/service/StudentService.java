package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.BeltLevel;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


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
    private final TechniqueRepo techniqueRepo;


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
        this.techniqueRepo = techniqueRepo;
    }

    /**
     * Adds a new student.
     *
     * @param student The student to be added.
     * @return The added student.
     */



    /**
     * Retrieves a list of all students.
     *
     * @return The list of students.
     */

    public List<Student> getStudentsWithTechniques() {
        logger.info("Getting students...");
        return studentRepo.findAllWithTechniques();
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

    public List<Student> searchStudentsNameLikeOrStartLike(String searchTerm) {
        LocalDate dateSearchTerm;
        try {
            // Try to parse searchTerm as LocalDate
            dateSearchTerm = LocalDate.parse(searchTerm);
            // If parsing succeeds, search by start date
            return studentRepo.getStudentsByStartDate(dateSearchTerm);
        } catch (DateTimeParseException e) {
            // If parsing fails, search by name
            return studentRepo.getStudentsByNameContainingIgnoreCase("%" + searchTerm + "%");
        }
    }






//    public Student addStudent(String name, LocalDate startDate, int instructorId) {
//        var student = new Student(name, startDate);
//
//        // Fetch the instructor entity along with its students collection
//        Instructor instructor = instructorRepo.findByIdWithStudents(instructorId)
//                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));
//
//        // Set the fetched instructor to the student
//        student.setInstructor(instructor);
//
//        // Save the student entity
//        Student savedStudent = studentRepo.save(student);
//
//        // Fetch the student entity along with its techniques collection
//        return studentRepo.findByIdWithTechniques(savedStudent.getId())
//                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
//    }





    @Transactional
    public Student addStudent(String name, LocalDate startDate, int instructorId) {
        var student = new Student(name, startDate);

        // Fetch the instructor entity along with its students collection
        Instructor instructor = instructorRepo.findByIdWithStudents(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found"));

        // Set the fetched instructor to the student
        student.setInstructor(instructor);

        // Save the student entity
        Student savedStudent = studentRepo.save(student);

        // Initialize techniques with an empty ArrayList if null
        if (savedStudent.getTechniques() == null) {
            savedStudent.setTechniques(new ArrayList<>());
        }

        return savedStudent;
    }



    public Student addStudentList(int id, String name, LocalDate start) {
        logger.info("Adding student with id {} of name {} and start date {}", id, name, start);
        return studentRepo.save(new Student(id, name, start));
    }






    public boolean changeStudentName(int id, String newName) {
        var student = studentRepo.findById(id).orElse(null);
        if (student == null) {
            return false;
        }
        student.setName(newName);
        studentRepo.save(student);
        return true;
    }

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