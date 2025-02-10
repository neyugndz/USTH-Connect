package vn.edu.usth.connect.Schedule.TimeTable.Hour;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils;
import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;

public class HourAdapter extends ArrayAdapter<HourEvent> {

    // 4 sure t chưa vào đây :))))
    public HourAdapter(@NonNull Context context, List<HourEvent> hourEvents)
    {
        super(context, 0, hourEvents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        HourEvent event = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_frame, parent, false);

        ListView eventListView = convertView.findViewById(R.id.eventListView);

        convertView.setTag(event.getStartTime());
        setEvents(convertView, event.events);

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


    private void setEvent(TextView textView, Event event)
    {
        String eventDetails = event.getEventName() + "\n" +
                formatEventTime(event) + "\n" + event.getLocation();
        textView.setText(eventDetails);
        textView.setVisibility(View.VISIBLE);
    }

    private void hideEvent(TextView tv)
    {
        tv.setVisibility(View.INVISIBLE);
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
