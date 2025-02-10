package vn.edu.usth.connect.Schedule.TimeTable.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils;

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(@NonNull Context context, List<Event> events)
    {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Event event = getItem(position);

        if (event == null) {
            return convertView;  // Return empty view if the event is null
        }

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_frame, parent, false);

        TextView eventNameTV = convertView.findViewById(R.id.eventNameTV);
        TextView eventTimeTV = convertView.findViewById(R.id.eventTimeTV);
        TextView eventLocationTV = convertView.findViewById(R.id.eventLocationTV);

        // Format the event's start and end time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String startTime = event.getEventStartDateTime().format(timeFormatter);
        String endTime = event.getEventEndDateTime().format(timeFormatter);

        // setText Event title
        eventNameTV.setText(event.getEventName());
        eventTimeTV.setText(String.format("%s - %s", startTime, endTime));
        eventLocationTV.setText(event.getLocation());

        return convertView;
    }

}
