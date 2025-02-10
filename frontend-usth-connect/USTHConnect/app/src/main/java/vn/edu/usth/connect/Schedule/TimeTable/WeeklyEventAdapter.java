package vn.edu.usth.connect.Schedule.TimeTable;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;

public class WeeklyEventAdapter extends ArrayAdapter<Event> {
    private Context context;
    private ArrayList<Event> events;

    public WeeklyEventAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hour_frame, parent, false);
        }

        ArrayList<Event> dailyEvents = new ArrayList<>();
        // In this case, we'll get a subset of events for this specific time slot
        if (position < events.size()) {
            dailyEvents.add(events.get(position));
        }

        // Call setEvents to display the events for this slot
        setEvents(convertView, dailyEvents);

        return convertView;
    }

    private void setEvents(View convertView, ArrayList<Event> events) {
        LinearLayout eventsContainer = convertView.findViewById(R.id.eventsContainer);
        eventsContainer.removeAllViews();

        if(events.isEmpty()) {
            return;
        }

        // Add event TextViews dynamically
        for(int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            TextView eventTextView = createEventTextView(convertView.getContext());
            setEvent(eventTextView, event);
            eventsContainer.addView(eventTextView);
        }
    }

    private void setEvent(TextView textView, Event event) {
        String eventDetails = event.getEventName() + "\n" +
                formatEventTime(event) + "\n" + event.getLocation();
        textView.setText(eventDetails);
        textView.setVisibility(View.VISIBLE);
    }

    private String formatEventTime(Event event) {
        // Format ISO_LOCAL_DATE_TIME format to our custom format
        String startTime = event.getEventStartDateTime().toLocalTime().toString();
        String endTime = event.getEventEndDateTime().toLocalTime().toString();
        return startTime + " - " + endTime;
    }

    // Helper method to create a TextView dynamically
    private TextView createEventTextView(Context context) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 8, 8, 8);
        textView.setLayoutParams(layoutParams);
        textView.setPadding(8, 8, 8, 8);
        textView.setBackgroundResource(R.color.blue);
        textView.setTextColor(context.getResources().getColor(R.color.white));
        textView.setTextSize(18);
        textView.setGravity(Gravity.START);
        return textView;
    }
}
