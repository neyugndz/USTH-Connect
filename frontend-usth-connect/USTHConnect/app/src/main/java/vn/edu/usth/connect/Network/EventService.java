package vn.edu.usth.connect.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;

public interface EventService {
    @GET("/api/v1/events/organizer")
    Call<List<Event>> getEvents(
            @Header("Authorization") String authorizationHeader,
            @Query("studyYear") String studyYear,
            @Query("major") String major);
}
