package vn.edu.usth.connect.Home.NotificationRecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.Models.Notification;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Utils.TimeUtils;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;
    private TimeUtils timeUtils;

    public NotificationAdapter() {
        this.notifications = new ArrayList<>();
        this.timeUtils = new TimeUtils();
    }


    public void updateNotifications(List<Notification> notifications) {
        this.notifications = notifications != null ? notifications : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.messageTextView.setText(notification.getMessage());

        // Convert String to LocalDateTime and format it
        String formattedTime = timeUtils.getTimeAgo(notification.getCreatedAt());
        holder.timestampTextView.setText(formattedTime);
    }

    @Override
    public int getItemCount() {
        return notifications != null ? notifications.size() : 0;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView, timestampTextView;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.notificationMessage);
            timestampTextView = itemView.findViewById(R.id.notificationTimestamp);
        }
    }
}

