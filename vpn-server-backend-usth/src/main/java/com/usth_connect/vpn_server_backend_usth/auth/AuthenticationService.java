package com.usth_connect.vpn_server_backend_usth.auth;

import com.usth_connect.vpn_server_backend_usth.Enum.Role;
import com.usth_connect.vpn_server_backend_usth.config.JwtService;
import com.usth_connect.vpn_server_backend_usth.entity.Student;
import com.usth_connect.vpn_server_backend_usth.repository.StudentRepository;
//import com.usth_connect.vpn_server_backend_usth.service.SipRegistrationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JwtService jwtService;

//    @Autowired
//    private SipRegistrationService service;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request, boolean isAdmin) {

        if (studentRepository.existsById(request.getId())) {
            throw new IllegalArgumentException("Student ID already exists. Registration not allowed.");
        } else {
            Student student = new Student();
            student.setFullName(request.getFullName());
            student.setId(request.getId());
            student.setPassword(passwordEncoder.encode(request.getPassword())); // Ensure password field exists in Student
            student.setGender(request.getGender());
            student.setDob(request.getDob());
            student.setMajor(request.getMajor());
            student.setEmail(request.getEmail());
            student.setStudyYear(request.getStudyYear());
            student.setPhoneNumber(request.getPhoneNumber());

            if (isAdmin) {
                student.setRole(Role.ADMIN); // Set the role as ADMIN if isAdmin is true
            } else {
                student.setRole(Role.USER); // Default role as USER
            }

            studentRepository.save(student);

            // Register SIP account
//            try {
//                service.registerSipAccount(request.getId(), request.getId(), request.getFullName() ,request.getPassword(), request.getEmail() );
//            } catch (Exception e) {
//                throw new RuntimeException("SIP account registration failed: " + e.getMessage());
//            }

            // Generate JWT token
            String jwtToken = jwtService.generateToken(student);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getId(),
                        request.getPassword()
                )
        );

        // Fetch user from repository
        Student student = studentRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(student);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse refreshToken(String expiredJwt) {
        // Validate the expired token
        if (jwtService.isTokenExpired(expiredJwt)) {
            String studentId = jwtService.extractStudentId(expiredJwt);
            if (studentId != null) {
                // Fetch the student from the database using the extracted ID
                Student student = studentRepository.findById(studentId)
                        .orElseThrow(() -> new RuntimeException("Student not found"));

                // Generate a new JWT token
                String newJwtToken = jwtService.generateToken(student);

                // Return the new token as a response
                return AuthenticationResponse.builder()
                        .token(newJwtToken)
                        .build();
            }
        }

        throw new IllegalArgumentException("Invalid or expired token.");
    }

}