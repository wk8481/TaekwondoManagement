package be.kdg.programming3.projectwilliamkasasa.service;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.domain.Technique;
import be.kdg.programming3.projectwilliamkasasa.presentation.viewmodels.StudentDto;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student addStudent(Student student);
    List<Student> getStudents();
    Optional<Student> getStudentById(int id);
    Student updateStudent(Student student);
    void deleteStudent(int id);

    List<Student> getStudentsByNameWilliam(String firstName);

    //
    Student addStudentList(int id, String name, LocalDate start);


    @Transactional
    Student addStudentListEM(int id, String name, LocalDate start);

    void addTechniquesToStudent(int studentId, List<Technique> techniques);

    void removeTechniquesFromStudent(int studentId, List<Technique> techniques);

    List<Technique> getTechniquesByStudentId(int studentId);

    List<Student> getStudentsByInstructorId(int instructorId);

    Student getStudentByName(String name);

    Optional<StudentDto> getStudentDtoById(int id);

    Student getStudentWithTechniques(int id);
}