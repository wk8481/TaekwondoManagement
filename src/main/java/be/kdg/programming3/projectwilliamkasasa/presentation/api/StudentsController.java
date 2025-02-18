package be.kdg.programming3.projectwilliamkasasa.presentation.api;

import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.*;
import be.kdg.programming3.projectwilliamkasasa.security.CustomUserDetails;
import be.kdg.programming3.projectwilliamkasasa.service.InstructorService;
import be.kdg.programming3.projectwilliamkasasa.service.StudentService;
import be.kdg.programming3.projectwilliamkasasa.service.StudentTechniqueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static be.kdg.programming3.projectwilliamkasasa.domain.Role.ADMIN;

@RestController
@RequestMapping("/api/students")
public class StudentsController {
    private final StudentService studentService;



    private final ModelMapper modelMapper;
    private final InstructorService instructorService;


    public StudentsController(StudentService studentService, StudentTechniqueService studentTechniqueService, ModelMapper modelMapper, InstructorService instructorService) {
        this.studentService = studentService;
        this.modelMapper = modelMapper;
        this.instructorService = instructorService;
    }

    // POST endpoint for adding a new student
    @PostMapping
    public ResponseEntity<StudentDto> addStudent(@Valid @RequestBody NewStudentDto newStudentDto,
                                                                 @AuthenticationPrincipal CustomUserDetails user) {
        var createdStudent = studentService.addStudent(
                newStudentDto.getName(), newStudentDto.getStartDate(), user == null ? null : user.getInstructorId());


        return new ResponseEntity<>(
                modelMapper.map(createdStudent, StudentDto.class),
                HttpStatus.CREATED
       );
    }


    // GET endpoint for getting one student by ID
    @GetMapping(value ="{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<StudentDto> getOneStudent(@PathVariable("id") int id) {
        var student = studentService.getStudentById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelMapper.map(student, StudentDto.class));
    }

    @GetMapping("{id}/techniques")
    ResponseEntity<List<TechniqueDto>> getTechniquesOfStudent(@PathVariable("id") int id) {
        var student = studentService.getStudentWithTechniques(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (student.getTechniques().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(student.getTechniques()

                .stream()
                .map(StudentTechnique::getTechnique)
                .map(tech -> modelMapper.map(tech, TechniqueDto.class))
                .toList());
    }

    // GET endpoint to get Instructor of a student
    @GetMapping("/{id}/instructor")
    public ResponseEntity<InstructorDto> getInstructorOfStudent(@PathVariable("id") int id) {
        var student = studentService.getStudentById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var instructor = instructorService.getInstructorByStudentId(id);
        if (instructor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(modelMapper.map(instructor, InstructorDto.class));
    }


@GetMapping()
ResponseEntity<List<StudentDto>> searchStudents(@RequestParam(required = false) String searchTerm) {
    if (searchTerm == null || searchTerm.isEmpty()) {
        // Handle the case when search parameter is not provided or empty
        return ResponseEntity.ok(studentService.getStudentsWithTechniques()
                .stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList());
    } else {
        // Search students by name
        var searchResult = studentService.searchStudentsByNameLike(searchTerm);
        if (searchResult.isEmpty()) {
            // Handle the case when no search results are found
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // Return the search results
            return ResponseEntity.ok(searchResult
                    .stream()
                    .map(student -> modelMapper.map(student, StudentDto.class))
                    .toList());
        }
    }
}










    // "/api/students/{id}
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteStudent(@PathVariable("id") int id,
                                       @AuthenticationPrincipal CustomUserDetails user,
                                       HttpServletRequest request) {
        // Instead of using `HttpServletRequest`, you can also do this:
        //   user.getAuthorities().stream().anyMatch(aut -> aut.getAuthority().equals("ROLE_ADMIN"))
        if (!instructorService.isInstructorAssignedToStudent(id, user.getInstructorId())
                && !request.isUserInRole(ADMIN.getCode())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (studentService.deleteStudent(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    // PATCH endpoint for updating a student by ID
    // "/api/students/{id}"
    @PatchMapping("{id}")
    public ResponseEntity<StudentDto> changeStudent(@PathVariable("id") int id,
                                                    @Valid @RequestBody UpdateStudentStartDateDto updateStudentStartDateDto,
                                                    @AuthenticationPrincipal CustomUserDetails user,
                                                    HttpServletRequest request) {
        if (!instructorService.isInstructorAssignedToStudent(id, user.getInstructorId())
                && !request.isUserInRole(ADMIN.getCode())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (studentService.changeStudentStartDate(id, updateStudentStartDateDto.getStartDate())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}


