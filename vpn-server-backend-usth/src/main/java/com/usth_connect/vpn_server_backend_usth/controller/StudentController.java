package com.usth_connect.vpn_server_backend_usth.controller;

import com.usth_connect.vpn_server_backend_usth.config.JwtService;
import com.usth_connect.vpn_server_backend_usth.dto.StudentDTO;
import com.usth_connect.vpn_server_backend_usth.dto.StudentSIPDTO;
import com.usth_connect.vpn_server_backend_usth.entity.Student;
import com.usth_connect.vpn_server_backend_usth.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody StudentDTO studentDTO ) {
        Optional<Student> updatedStudent = studentService.updateStudent(id, studentDTO);
        return updatedStudent
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> updateStudentPartially(@PathVariable String id, @RequestBody StudentDTO studentDTO) {
        Optional<Student> updatedStudent = studentService.updateStudentPartially(id, studentDTO);
        return updatedStudent
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/sip")
    public ResponseEntity<Student> updateStudentSIP(@PathVariable String id, @RequestBody StudentSIPDTO studentSIPDTO) {
        Optional<Student> updatedStudent = studentService.updateStudentSIP(id, studentSIPDTO);
        return updatedStudent
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to retrieve sip credentials along with the token
    @GetMapping("/sipCredentials")
    public ResponseEntity<Map<String, String>> getSipCredentials(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid Authorization header format"));
        }

        // Ensure the Authorization header follows the format Bearer <token>
        String token = authorizationHeader.replace("Bearer ", "").trim();
        Map<String, String> credentials = studentService.getSipCredentials(token);

        if (credentials == null) {
            // Return 404 if no credentials are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Return the credentials in the response
        return ResponseEntity.ok(credentials);
    }

    @GetMapping("/isRegistered")
    public ResponseEntity<Map<String, Boolean>> isRegistered(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String actualToken = token.replace("Bearer ", "").trim();

        // Extract studentId using JwtService
        String studentId = jwtService.extractStudentId(actualToken);

        if (studentId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Check if the user has SIP credentials
        boolean hasSipCredentials = studentService.checkSipRegistrationStatus(studentId);
        return ResponseEntity.ok(Collections.singletonMap("isRegistered", hasSipCredentials));
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String actualToken = token.replace("Bearer ", "").trim();
        jwtService.invalidateToken(actualToken);
        return ResponseEntity.ok().build();
    }
}
