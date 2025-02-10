package vn.edu.usth.connect.Network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.edu.usth.connect.Models.AuthResponse;
import vn.edu.usth.connect.Models.LoginRequest;

public interface AuthService {
    @POST("/api/v1/auth/authenticate")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/v1/students/logout")
    Call<Void> logout(@Header("Authorization") String authorizationHeader);
}
