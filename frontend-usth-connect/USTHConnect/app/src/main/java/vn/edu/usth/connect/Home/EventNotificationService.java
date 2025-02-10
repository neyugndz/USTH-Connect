package vn.edu.usth.connect.Home;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import vn.edu.usth.connect.Home.NotificationRecyclerView.NotificationFragment;

import vn.edu.usth.connect.MainActivity;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Schedule_Activity;
import vn.edu.usth.connect.StudyBuddy.Audio.PushNoti.MyApplication;

public class EventNotificationService extends Service {
    public static final String CHANNEL_ID = MyApplication.EVENT_CHANNEL_ID;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_NOT_STICKY;

        // Extract the notification's message and created_at from the intent
        String notificationMessage = intent.getStringExtra("notification_message");
        String createdAt = intent.getStringExtra("created_at");

        // Create an intent to open ScheduleActivity
        Intent notificationIntent = new Intent(this, Schedule_Activity.class);
        notificationIntent.putExtra("created_at", createdAt);  // Pass createdAt if needed in NotificationFragment

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Build and display the notification using the message and created_at
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("New Event Notification")
                .setContentText(notificationMessage)
                .setSmallIcon(R.mipmap.usth_logo)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setSubText("Created at: " + createdAt)  // Add created_at as a subtext
                .build();

        startForeground(2, notification);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
