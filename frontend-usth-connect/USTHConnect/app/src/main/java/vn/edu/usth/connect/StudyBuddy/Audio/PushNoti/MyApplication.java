package vn.edu.usth.connect.StudyBuddy.Audio.PushNoti;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import org.linphone.core.*;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Home.EventNotificationService;
import vn.edu.usth.connect.Models.Notification;
import vn.edu.usth.connect.Network.NotificationService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;

public class MyApplication extends Application {

    public static final String CHANNEL_ID = "call_notification_id";
    public static final String EVENT_CHANNEL_ID = "event_notification_id";
    private Core core;

    private List<Notification> previousNotificationList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        Factory factory = Factory.instance();
        factory.setDebugMode(true, "Hello Linphone");

        core = factory.createCore(null, null, this);
        core.addListener(new CoreListenerStub());
        core.start();

        createChannelNoti();

        // Start checking for event notifications
        fetchNotifications();
    }

    public Core getLinphoneCore() {
        return core;
    }

    private void createChannelNoti() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "CallNotification",
//                    NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
            // Channel for calls
            NotificationChannel callChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Call Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            callChannel.setDescription("Notifications for incoming calls");

            // Channel for events
            NotificationChannel eventChannel = new NotificationChannel(
                    EVENT_CHANNEL_ID,
                    "Event Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            eventChannel.setDescription("Notifications for new events");

            // Register channels with the system
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(callChannel);
                manager.createNotificationChannel(eventChannel);
            }
        }
    }

//    private void startEventNotificationService() {
//        Intent serviceIntent = new Intent(this, EventNotificationService.class);
//        startService(serviceIntent);
//    }

    public void fetchNotifications() {
        // Retrieve token, studentId, studyYear, and major from SessionManager
        String token = SessionManager.getInstance().getToken();
        String studentId = SessionManager.getInstance().getStudentId();
        String studyYear = SessionManager.getInstance().getStudyYear();
        String major = SessionManager.getInstance().getMajor();

        if (token != null && !token.isEmpty() &&
                studentId != null && !studentId.isEmpty() &&
                studyYear != null && !studyYear.isEmpty() &&
                major != null && !major.isEmpty()) {
            String authHeader = "Bearer " + token;

            // Fetch notifications using the studyYear and major
            NotificationService notificationService = RetrofitClient.getInstance().create(NotificationService.class);
            notificationService.getNotificationsForOrganizerId(authHeader, studyYear, major).enqueue(new Callback<List<Notification>>() {
                @Override
                public void onResponse(retrofit2.Call<List<Notification>> call, Response<List<Notification>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Notification> notificationList = response.body();

                        // Sort notifications by descending createdAt
                        notificationList.sort((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()));

                        // Check if the notifications have changed
                        if (notificationsHaveChanged(notificationList, previousNotificationList)) {
                            Log.d("MyApplication", "New notifications detected. Triggering service...");

                            // Start the service with the latest notification details
                            if (!notificationList.isEmpty()) {
                                Notification latestNotification = notificationList.get(0);
                                Intent serviceIntent = new Intent(MyApplication.this, EventNotificationService.class);
                                serviceIntent.putExtra("notification_message", latestNotification.getMessage());
                                serviceIntent.putExtra("created_at", latestNotification.getCreatedAt());
                                startService(serviceIntent);
                            }

                            // Update the previous notification list
                            previousNotificationList = new ArrayList<>(notificationList);
                        } else {
                            Log.d("MyApplication", "No new notifications.");
                        }
                    } else {
                        Log.e("MyApplication", "Failed to fetch notifications, response code: " + response.code());
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<List<Notification>> call, Throwable t) {
                    Log.e("MyApplication", "Error fetching notifications: " + t.getMessage());
                }
            });
        } else {
            Log.e("MyApplication", "Token, studentId, studyYear, or major is missing.");
        }
    }


    private boolean notificationsHaveChanged(List<Notification> currentList, List<Notification> previousList) {
        if (previousList == null || currentList.size() != previousList.size()) {
            return true;
        }
        for (int i = 0; i < currentList.size(); i++) {
            if (!currentList.get(i).equals(previousList.get(i))) {
                return true;
            }
        }
        return false;
    }
}

