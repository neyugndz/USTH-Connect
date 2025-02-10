package vn.edu.usth.connect.StudyBuddy.Audio.PushNoti;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import org.linphone.core.*;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Audio.IncomingActivity;

public class CallService extends Service {
    public static final String CHANNEL_ID = MyApplication.CHANNEL_ID;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String domain = intent.getStringExtra("domain");
        String transportType = intent.getStringExtra("transport_type");
        String remote_user = intent.getStringExtra("remote user");

        Intent notificationIntent = new Intent(this, IncomingActivity.class);
        notificationIntent.putExtra("username", username);
        notificationIntent.putExtra("password", password);
        notificationIntent.putExtra("domain", domain);
        notificationIntent.putExtra("transport_type", transportType);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Incoming Call")
                .setContentText("Call from " + remote_user)
                .setSmallIcon(R.mipmap.usth_logo)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setAutoCancel(true)
                .build();

        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

