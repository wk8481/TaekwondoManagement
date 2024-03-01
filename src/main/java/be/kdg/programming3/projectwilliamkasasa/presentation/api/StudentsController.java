package be.kdg.programming3.projectwilliamkasasa.presentation.api;

import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.StudentDto;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.TechniqueDto;
import be.kdg.programming3.projectwilliamkasasa.service.StudentService;
import be.kdg.programming3.projectwilliamkasasa.service.TechniqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentsController {
    private final StudentService studentService;
    private final TechniqueService techniqueService;

    public StudentsController(StudentService studentService, TechniqueService techniqueService) {
        this.studentService = studentService;
        this.techniqueService = techniqueService;
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentDto> getOneStudent(@PathVariable("id") int id) {
        var student = studentService.getStudentById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(
                new StudentDto(
                        student.getId(),
                        student.getName(),
                        student.getStartDate()
                ));
    }

    @GetMapping("{id}/techniques")
    ResponseEntity<List<TechniqueDto>> getTechniquesOfStudent(@PathVariable("id") int id) {
        var student = studentService.getStudentById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (student.getTechniques().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(student.getTechniques()

                .stream()
                .map(StudentTechnique::getTechnique)
                .map(tech -> new TechniqueDto(
                        tech.getId(),
                        tech.getName(),
                        tech.getType(),
                        tech.getDescription()
                )).toList());
    }

    // "/api/students/search"
    @GetMapping
    ResponseEntity<List<StudentDto>> searchStudents(@RequestParam(required = false) String search) {
        if (search == null) {
            return ResponseEntity.ok(studentService.getStudents()
                    .stream()
                    .map(student -> new StudentDto(
                            student.getId(),
                            student.getName(),
                            student.getStartDate()))

                    .toList());
        } else {
            var searchResult = studentService.searchStudentsNameLikeOrStartLike(search);
            if (searchResult.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.ok(searchResult
                        .stream()
                        .map(student -> new StudentDto(
                                student.getId(),
                                student.getName(),
                                student.getStartDate()))
                        .toList());
            }
        }
    }

    // "/api/students/{id}
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteStudent(@PathVariable("id") int id) {
        if (studentService.deleteStudent(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
