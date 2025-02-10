package vn.edu.usth.connect.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.usth.connect.Models.StudyBuddy.Message;

public interface MessageService {
    @POST("/api/v1/connections/{connectionId}/messages/send")
    Call<Message> sendMessage(
            @Header("Authorization") String authHeader,
            @Path("connectionId") Long connectionId,
            @Query("senderId") String senderId,
            @Query("receiverId") String receiverId,
            @Query("content") String content
    );

    @GET("/api/v1/connections/{connectionId}/messages")
    Call<List<Message>> getMessages(
            @Header("Authorization") String authHeader,
            @Path("connectionId") Long connectionId
    );

}
