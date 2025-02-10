package vn.edu.usth.connect.Schedule.Course.Event_RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class EventForCourseViewHolder extends RecyclerView.ViewHolder {

    TextView eventName, eventTime, eventLocation;
    public EventForCourseViewHolder(@NonNull View itemView) {
        super(itemView);
        eventName = itemView.findViewById(R.id.event_name);
        eventTime = itemView.findViewById(R.id.event_time);
        eventLocation = itemView.findViewById(R.id.event_location);
    }

}
