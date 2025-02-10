package vn.edu.usth.connect.Schedule.Course.Event_RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;

public class EventForCourseAdapter extends RecyclerView.Adapter<EventForCourseViewHolder> {

    private ArrayList<Event> events = new ArrayList<>();

    public EventForCourseAdapter(ArrayList<Event> events) {
        this.events = events;
    }

    public void updateEvents(ArrayList<Event> updatedEvents) {
        this.events.clear();
        this.events.addAll(updatedEvents);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventForCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_for_course, parent, false);
        return new EventForCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventForCourseViewHolder holder, int position) {
        Event event = events.get(position);
        holder.eventName.setText(event.getEventName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm (dd/MM/yyyy)");

        // Format event start and end times
        String formattedStartTime = event.getEventStartDateTime().format(formatter);
        String formattedEndTime = event.getEventEndDateTime().format(formatter);

        // Set formatted start and end times
        holder.eventTime.setText(formattedStartTime + " - " + formattedEndTime);
        holder.eventLocation.setText(event.getLocation());

        // Change text color based on event's time
        if (event.getEventStartDateTime().isBefore(LocalDateTime.now())) {
            // Darker color for passed events (text color)
            holder.eventName.setTextColor(Color.DKGRAY);
            holder.eventTime.setTextColor(Color.DKGRAY);
            holder.eventLocation.setTextColor(Color.DKGRAY);
        } else {
            // Lighter color for upcoming events (text color)
            holder.eventName.setTextColor(Color.LTGRAY);
            holder.eventTime.setTextColor(Color.LTGRAY);
            holder.eventLocation.setTextColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
