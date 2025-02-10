package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.config.JwtService;
import com.usth_connect.vpn_server_backend_usth.dto.StudentDTO;
import com.usth_connect.vpn_server_backend_usth.dto.StudentSIPDTO;
import com.usth_connect.vpn_server_backend_usth.entity.Student;
import com.usth_connect.vpn_server_backend_usth.repository.StudentRepository;
import org.bouncycastle.pqc.jcajce.provider.rainbow.BCRainbowPrivateKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(student -> new StudentDTO(
                        student.getId(),
                        student.getFullName(),
                        student.getPassword(),
                        student.getEmail(),
                        student.getDob(),
                        student.getMajor(),
                        student.getGender(),
                        student.getPhoneNumber(),
                        student.getStudyYear()
                ))
                .toList();
    }

    public Optional<Student> updateStudent(String id, StudentDTO studentDTO) {
        return studentRepository.findById(id).map(existingStudent -> {
            existingStudent.setFullName(studentDTO.getFullName());
            existingStudent.setEmail(studentDTO.getEmail());
            existingStudent.setDob(studentDTO.getDob());
            existingStudent.setMajor(studentDTO.getMajor());
            existingStudent.setGender(studentDTO.getGender());
            existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());
            existingStudent.setStudyYear(studentDTO.getStudyYear());
            return studentRepository.save(existingStudent);
        });
    }

    public Optional<Student> updateStudentPartially(String id, StudentDTO studentDTO) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();

            // Update only provided fields
            if (studentDTO.getPassword() != null) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                student.setPassword(encoder.encode(studentDTO.getPassword()));
            }
            if (studentDTO.getPhoneNumber() != null) {
                student.setPhoneNumber(studentDTO.getPhoneNumber());
            }
            // Update other fields as needed
            studentRepository.save(student);
            return Optional.of(student);
        }
        return Optional.empty();
    }


    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

    public Optional<Student> updateStudentSIP(String id, StudentSIPDTO studentSIPDTO) {
        return studentRepository.findById(id).map(existingStudent -> {
            existingStudent.setSipUsername(studentSIPDTO.getSipUsername());
            existingStudent.setSipPassword(studentSIPDTO.getSipPassword());
            return studentRepository.save(existingStudent);
        });
    }

    // Method to retrieve SIP credential through token
    public Map<String, String> getSipCredentials(String token) {
        // Extract the studentid from the token
        String studentId = jwtService.extractStudentId(token);

        if(studentId == null) {
            return null;
        }

        // Retrieve the user from the database
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();


            // Prepare the response mapping
            Map<String, String> sipCredentials = new HashMap<>();
            sipCredentials.put("sip_username", student.getSipUsername());
            sipCredentials.put("sip_password", student.getSipPassword());

            return sipCredentials;
        } else {
            return null;
        }
    }

    // Method to check the register status of the student account
    public boolean checkSipRegistrationStatus(String studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return student.getSipUsername() != null && student.getSipPassword() != null;
        }
        return false;
    }
}
