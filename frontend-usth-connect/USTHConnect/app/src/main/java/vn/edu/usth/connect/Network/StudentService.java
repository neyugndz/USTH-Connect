package vn.edu.usth.connect.Network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import vn.edu.usth.connect.Models.Student;
import vn.edu.usth.connect.Models.Dto.StudentDTO;
import vn.edu.usth.connect.Models.Dto.StudentSIPDTO;

public interface StudentService {
    // Endpoint to fetch the Student profile based on their ID
    @GET("/api/v1/students/{id}")
    Call<Student> getStudentProfile(
            @Header("Authorization") String authorizationHeader,
            @Path("id") String id
    );

    // Endpoint to update and edit student information
    @PATCH("/api/v1/students/{id}")
    Call<Student> updateStudentProfile(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Body StudentDTO studentDTO
    );

    // Endpoint to update the student with SIP information
    @PATCH("/api/v1/students/{id}/sip")
    Call<Student> updateSipProfile(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Body StudentSIPDTO studentSIPDTO
    );

}
