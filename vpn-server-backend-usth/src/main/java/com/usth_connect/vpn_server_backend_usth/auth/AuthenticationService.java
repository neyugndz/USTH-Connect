package com.usth_connect.vpn_server_backend_usth.auth;

import com.usth_connect.vpn_server_backend_usth.Enum.Role;
import com.usth_connect.vpn_server_backend_usth.config.JwtService;
import com.usth_connect.vpn_server_backend_usth.entity.Student;
import com.usth_connect.vpn_server_backend_usth.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

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
            student.setRole(Role.USER); // Set the default role as USER
            studentRepository.save(student);

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
}
