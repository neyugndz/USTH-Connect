package vn.edu.usth.connect.Utils;

import static androidx.core.app.ServiceCompat.startForeground;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import vn.edu.usth.connect.MainActivity;
import vn.edu.usth.connect.Models.Notification;
import vn.edu.usth.connect.R;

public class NotificationUtils {

    // Notification Channel to help with the Displaying Notification
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "default_channel";
            CharSequence name = "Default Channel";
            String description = "Channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
                Log.d("NotificationUtils", "Notification channel created successfully.");
            } else {
                Log.e("NotificationUtils", "NotificationManager is null, failed to create notification channel.");
            }
        } else {
            Log.d("NotificationUtils", "Device version is lower than Android 8.0, no need to create channel.");
        }
    }

    // Method to show notification
    public static void showNotification(Context context, String title, String message) {
        Log.d("NotificationUtils", "Preparing to show notification with title: " + title + " and message: " + message);

        // Create an Intent to open the NotificationFragment
        Intent intent = new Intent(context, MainActivity.class);  // Change MainActivity to the activity where the fragment is hosted
        intent.putExtra("open_fragment", "notification");  // pass data to specify notification fragment to open

        // Create a PendingIntent for launching the activity
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default_channel")
                .setSmallIcon(R.drawable.b3) // todo : Add your notification icon
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            Log.e("NotificationUtils", "NotificationManager is null, failed to show notification.");
        } else {
            Log.d("NotificationUtils", "NotificationManager initialized successfully.");
            notificationManager.notify(0, builder.build());
            Log.d("NotificationUtils", "Notification displayed successfully.");
        }
    }

}
