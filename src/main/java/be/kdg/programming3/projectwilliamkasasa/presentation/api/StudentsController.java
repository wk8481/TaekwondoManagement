package be.kdg.programming3.projectwilliamkasasa.presentation.api;

import be.kdg.programming3.projectwilliamkasasa.domain.StudentTechnique;
import be.kdg.programming3.projectwilliamkasasa.presentation.api.dto.*;
import be.kdg.programming3.projectwilliamkasasa.security.CustomUserDetails;
import be.kdg.programming3.projectwilliamkasasa.service.StudentService;
import be.kdg.programming3.projectwilliamkasasa.service.StudentTechniqueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static be.kdg.programming3.projectwilliamkasasa.domain.Role.ADMIN;

@RestController
@RequestMapping("/api/students")
public class StudentsController {
    private final StudentService studentService;

    private final StudentTechniqueService studentTechniqueService;

    private final ModelMapper modelMapper;


    public StudentsController(StudentService studentService, StudentTechniqueService studentTechniqueService, ModelMapper modelMapper) {
        this.studentService = studentService;
        this.studentTechniqueService = studentTechniqueService;
        this.modelMapper = modelMapper;



    }

    // POST endpoint for adding a new student
    @PostMapping
    public ResponseEntity<StudentDto> addStudent(@Valid @RequestBody NewStudentDto newStudentDto,
                                                                 @AuthenticationPrincipal CustomUserDetails user) {
        var createdStudent = studentService.addStudent(
                newStudentDto.getName(), newStudentDto.getStartDate(), user.getInstructorId());


        return new ResponseEntity<>(
                modelMapper.map(createdStudent, StudentDto.class),
                HttpStatus.CREATED
       );
    }


    // GET endpoint for getting one student by ID
    @GetMapping("{id}")
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

//    // "/api/students/search"
//    @GetMapping("/api/students/search")
//    ResponseEntity<List<StudentDto>> searchStudents(@RequestParam(required = false) String search) {
//        if (search == null || search.isEmpty()) {
//            // Handle the case when search parameter is not provided or empty
//            return ResponseEntity.ok(studentService.getStudentsWithTechniques()
//                    .stream()
//                    .map(student -> modelMapper.map(student, StudentDto.class))
//                    .toList());
//        } else {
//            var searchResult = studentService.searchStudentsNameLikeOrStartLike(search);
//            if (searchResult.isEmpty()) {
//                // Handle the case when no search results are found
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            } else {
//                // Return the search results
//                return ResponseEntity.ok(searchResult
//                        .stream()
//                        .map(student -> modelMapper.map(student, StudentDto.class))
//                        .toList());
//            }
//        }
//    }
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





//@GetMapping("/api/students/search")
//ResponseEntity<List<StudentDto>> searchStudents(@RequestParam(required = false) String query) {
//    if (query == null) {
//        return ResponseEntity.ok(studentService.getStudentsWithTechniques()
//                .stream()
//                .map(student -> modelMapper.map(student, StudentDto.class))
//                .toList());
//    } else {
//        var searchResult = studentService.searchStudentsByNameLike(query);
//        if (searchResult.isEmpty()) {
//            try {
//                // Try to parse query as a LocalDate
//                LocalDate dateSearchTerm = LocalDate.parse(query);
//                searchResult = studentService.searchStudentsByStartDate(dateSearchTerm);
//            } catch (DateTimeParseException e) {
//                // If parsing fails, return an empty list
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//        }
//        return ResponseEntity.ok(searchResult
//                .stream()
//                .map(student -> modelMapper.map(student, StudentDto.class))
//                .toList());
//    }
//}




    // "/api/students/{id}
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteStudent(@PathVariable("id") int id,
                                       @AuthenticationPrincipal CustomUserDetails user,
                                       HttpServletRequest request) {
        // Instead of using `HttpServletRequest`, you can also do this:
        //   user.getAuthorities().stream().anyMatch(aut -> aut.getAuthority().equals("ROLE_ADMIN"))
        if (studentTechniqueService.isTechniqueLearntByStudent(id, user.getInstructorId())
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
        if (studentTechniqueService.isTechniqueLearntByStudent(id, user.getInstructorId())
                && !request.isUserInRole(ADMIN.getCode())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (studentService.changeStudentStartDate(id, updateStudentStartDateDto.getStartDate())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//
//    @PatchMapping("{id}")
//    public ResponseEntity<StudentDto> changeStudent(@PathVariable("id") int id,
//                                                    @Valid @RequestBody UpdateStudentStartDateDto updateStudentStartDateDto,
//                                                    @AuthenticationPrincipal CustomUserDetails user,
//                                                    HttpServletRequest request) {
//        if (!user.isAdmin() && !request.isUserInRole("ROLE_ADMIN")) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        if (studentService.changeStudentStartDate(id, updateStudentStartDateDto.getStartDate())) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


//    // PATCH endpoint for updating a student by ID
//// "/api/students/{id}"
//    @PatchMapping("{id}")
//    public ResponseEntity<StudentDto> changeStudent(@PathVariable("id") int id,
//                                                    @Valid @RequestBody UpdateStudentStartDateDto updateStudentStartDateDto,
//                                                    @AuthenticationPrincipal CustomUserDetails user,
//                                                    HttpServletRequest request) {
//        // Check if the authenticated user is an admin using CustomUserDetails
//        if (!user.getRole().equals(Role.ADMIN)) {
//            // If not an admin, check if the user is in a specific role using HttpServletRequest
//            if (!request.isUserInRole(ADMIN.getCode())) {
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//            }
//        }
//
//        // Attempt to change the start date of the student
//        if (studentService.changeStudentStartDate(id, updateStudentStartDateDto.getStartDate())) {
//            // Return 204 No Content if the start date was successfully updated
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            // Return 404 Not Found if the student with the given ID was not found
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }




}


