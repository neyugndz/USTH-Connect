package vn.edu.usth.connect.Workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import vn.edu.usth.connect.StudyBuddy.Audio.PushNoti.MyApplication;

public class NotificationWorker extends Worker {

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Call your fetchNotifications()
        MyApplication app = (MyApplication) getApplicationContext();
        app.fetchNotifications();

        // Indicate success or retry
        return Result.success();
    }
}
