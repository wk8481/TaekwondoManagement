//package be.kdg.programming3.projectwilliamkasasa.presentation.api;
//
//import be.kdg.programming3.projectwilliamkasasa.domain.Role;
//import be.kdg.programming3.projectwilliamkasasa.security.CustomUserDetails;
//import be.kdg.programming3.projectwilliamkasasa.service.StudentService;
//import be.kdg.programming3.projectwilliamkasasa.service.StudentTechniqueService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@AutoConfigureMockMvc
//class StudentsControllerUnitTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private StudentService studentService;
//
//    @MockBean
//    private StudentTechniqueService studentTechniqueService;
//
//    @Test
//    public void deleteStudentShouldBeUnauthorizedIfNotLoggedIn() throws Exception {
//       mockMvc.perform(delete("/api/students/{id}", 1000)
//               .with(csrf()))
//               .andExpect(status().isUnauthorized());
//       verify(studentService, never()).deleteStudent(1000);
//    }
//
//    @Test
//    public void deleteStudentShouldBeAllowedIfInstructorIsAssigned () throws Exception {
//        int studentId = 1000;
//        int instructorId = 1555;
//        var userDetails = new CustomUserDetails("instructor", "password", List.of(), instructorId);
//        given(studentTechniqueService.isTechniqueLearntByStudent(studentId, instructorId))
//                .willReturn(true);
//        given(studentService.deleteStudent(studentId)).willReturn(true);
//
//        mockMvc.perform(delete("/api/students/{id}", studentId)
//                .with(user(userDetails))
//                .with(csrf()))
//                .andExpect(status().isNoContent());
//        verify(studentService).deleteStudent(studentId);
//    }
//
//    @Test
//    public void deleteStudentShouldBeAllowedIfAdmin() throws Exception {
//        int studentId = 1000;
//        int instructorId = 1555;
//        var userDetails = new CustomUserDetails("master", "password", List.of(new SimpleGrantedAuthority(Role.ADMIN.getCode())),
//                instructorId);
//        given(studentTechniqueService.isTechniqueLearntByStudent(studentId, instructorId))
//                .willReturn(false);
//        given(studentService.deleteStudent(studentId)).willReturn(true);
//
//        mockMvc.perform(delete("/api/students/{id}", studentId)
//                        .with(user(userDetails))
//                        .with(csrf()))
//                .andExpect(status().isNoContent());
//        verify(studentService).deleteStudent(studentId);
//    }
//
//    @Test
//    public void deleteStudentShouldBeAllowedIfNotAssigned() throws Exception {
//        int studentId = 1000;
//        int instructorId = 1555;
//        var userDetails = new CustomUserDetails("master", "password", List.of(),
//                instructorId);
//        given(studentTechniqueService.isTechniqueLearntByStudent(studentId, instructorId))
//                .willReturn(false);
//
//
//
//        mockMvc.perform(delete("/api/students/{id}", studentId)
//                        .with(user(userDetails))
//                        .with(csrf()))
//                .andExpect(status().isForbidden());
//
//        verify(studentService, never()).deleteStudent(studentId);
//    }
//
////    @Test
////    public void deleteStudentShouldBeNotFoundIfStudentNotFound() throws Exception {
////        int studentId = 1000;
////        int instructorId = 1555;
////        var userDetails = new CustomUserDetails("master", "password", List.of(new SimpleGrantedAuthority(Role.ADMIN.getCode()),
////                instructorId), ;
////        given(studentTechniqueService.isTechniqueLearntByStudent(studentId, instructorId))
////                .willReturn(false);
////        given(studentService.deleteStudent(studentId)).willReturn(false);
////
////
////
////        mockMvc.perform(delete("/api/students/{id}", studentId)
////                        .with(user(userDetails))
////                        .with(csrf()))
////                .andExpect(status().isNotFound());
////
////        verify(studentService).deleteStudent(studentId);
////    }
//}