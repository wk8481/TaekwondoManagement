package be.kdg.programming3.projectwilliamkasasa.presentation.api;

import be.kdg.programming3.projectwilliamkasasa.domain.Student;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.TechniqueDto;
import be.kdg.programming3.projectwilliamkasasa.service.StudentService;
import be.kdg.programming3.projectwilliamkasasa.service.TechniqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class StudentsController {
    private final StudentService studentService;
    private final TechniqueService techniqueService;

    public StudentsController(StudentService studentService, TechniqueService techniqueService) {
        this.studentService = studentService;
        this.techniqueService = techniqueService;
    }

//    @GetMapping("{id}") //
//    Student getOneStudent(@PathVariable("id") int id) { //TOdo dto
//        //TODO: return status codes!
//        return studentService.getStudentById(id);
//
//    }
    @GetMapping("{id}")
    public ResponseEntity<Student> getOneStudent(@PathVariable("id") int id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("{id}/techniques") // "/api/students/1/techniques"
    List<TechniqueDto> getTechniquesOfStudent(@PathVariable("id") int id) {
        //TODO: return status codes!
        return techniqueService
                .getTechniquesOfStudent(id)
                .stream()
                .map(tech -> new TechniqueDto(
                        tech.getId(),
                        tech.getName(),
                        tech.getType(),
                        tech.getDescription()
                )).toList();
    }

//    @GetMapping("{id}/techniques")
//    public ResponseEntity<List<TechniqueDto>> getTechniquesOfStudent(@PathVariable("id") int id) {
//        List<TechniqueDto> techniques = techniqueService.getTechniquesOfStudent(id);
//        return ResponseEntity.ok(techniques);
//    }


    @GetMapping
    ResponseEntity<List<TechniqueDto>> searchTechniques() {

    }
