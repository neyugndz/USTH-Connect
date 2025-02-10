package vn.edu.usth.connect.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import vn.edu.usth.connect.Models.Notification;

public interface NotificationService {
    @GET("/api/v1/notifications")
    Call<List<Notification>> getNotifications(@Header("Authorization") String authHeader );

    @GET("/api/v1/notifications/organizer")
    Call<List<Notification>> getNotificationsForOrganizerId(
            @Header("Authorization") String authHeader,
            @Query("studyYear") String studyYear,
            @Query("major") String major);

}
