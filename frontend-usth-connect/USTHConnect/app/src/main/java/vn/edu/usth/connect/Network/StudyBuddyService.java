package vn.edu.usth.connect.Network;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.usth.connect.Models.StudyBuddy.Connection;
import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddy;

public interface StudyBuddyService {
    @POST("/api/v1/studyBuddy/save")
    Call<Void> saveStudyBuddy(
            @Header("Authorization") String authHeader,
            @Body StudyBuddy studyBuddy);

    @GET("/api/v1/students/sipCredentials")
    Call<Map<String, String>> getSipCredentials(@Header("Authorization") String token);

    @GET("/api/v1/students/isRegistered")
    Call<Map<String, Boolean>> isRegistered(@Header("Authorization") String token);

    @GET("/api/v1/studyBuddy/{studentId}")
    Call<StudyBuddy> getStudyBuddy(
            @Header("Authorization") String token,
            @Path("studentId") String studentId
    );

    @GET("/api/v1/studyBuddy/{studentId}/recommendations/details")
    Call<List<StudyBuddy>> getRecommendation(
            @Header("Authorization") String token,
            @Path("studentId") String studentId
    );

    @POST("/api/v1/connections/create")
    Call<Connection> createConnection(
            @Header("Authorization") String authHeader,
            @Query("studyBuddy1Id") String studyBuddy1Id,
            @Query("studyBuddy2Id") String studyBuddy2Id
    );

    @GET("/api/v1/connections/{studyBuddyId}")
    Call<List<Connection>> getConnections(
            @Header("Authorization") String authHeader,
            @Path("studyBuddyId") String studyBuddyId
    );
}
