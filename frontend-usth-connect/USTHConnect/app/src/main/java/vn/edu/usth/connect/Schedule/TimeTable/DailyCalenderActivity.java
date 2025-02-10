package vn.edu.usth.connect.Schedule.TimeTable;

import static vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils.selectedDate;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils;
import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;
import vn.edu.usth.connect.Schedule.TimeTable.Hour.HourAdapter;
import vn.edu.usth.connect.Schedule.TimeTable.Hour.HourEvent;

public class  DailyCalenderActivity extends AppCompatActivity {

    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calender);

        // Retrieve the selected date from the Intent
        String selectedDate = getIntent().getStringExtra("selectedDate");
        if (selectedDate != null) {
            CalenderUtils.selectedDate = LocalDate.parse(selectedDate);
        }

        // Setup TextView
        monthDayText = findViewById(R.id.monthDayText);

        // Setup TextView
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);

        // List Hour for Event
        hourListView = findViewById(R.id.hourListView);

        // Button Function
        setup_function();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDayView();
    }

    // setText for TextView
    private void setDayView() {
        monthDayText.setText(CalenderUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setHourAdapter();
    }

    // Setup Adapter for ListView for task in hour
    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    private ArrayList<HourEvent> hourEventList() {
        ArrayList<HourEvent> list = new ArrayList<>();

        // Iterate through each hour of the day
        for (int hour = 0; hour < 24; hour++) {
            LocalTime hourStartTime = LocalTime.of(hour, 0); // Start of the hour
            LocalTime hourEndTime = hourStartTime.plusHours(1); // End of the hour

            ArrayList<Event> eventsInHour = new ArrayList<>();

            // Get events for this specific hour range
            for (Event event : Event.eventsForDate(selectedDate)) {
                LocalTime eventStart = event.getEventStartDateTime().toLocalTime();
                LocalTime eventEnd = event.getEventEndDateTime().toLocalTime();

                // Check if the event overlaps with this hour
                if ((eventStart.isBefore(hourEndTime) && eventEnd.isAfter(hourStartTime))) {
                    // Check if the event is already added to any previous hour in the same day
                    boolean eventAlreadyAdded = false;
                    for (HourEvent hourEvent : list) {
                        for (Event addedEvent : hourEvent.getEvents()) {
                            if (addedEvent.equals(event)) {
                                eventAlreadyAdded = true;
                                break;
                            }
                        }
                        if (eventAlreadyAdded) break;
                    }

                    if (!eventAlreadyAdded) {
                        eventsInHour.add(event);
                    }
                }
            }
            // Add events for this hour to the list
            if (!eventsInHour.isEmpty()) {
                list.add(new HourEvent(hourStartTime, hourEndTime, eventsInHour));
            }
        }
        return list;
    }

    // To previous day
    public void previousDayAction(View view) {
        selectedDate = selectedDate.minusDays(1);
        setDayView();
    }

    // To next day
    public void nextDayAction(View view) {
        selectedDate = selectedDate.plusDays(1);
        setDayView();
    }

    private void setup_function(){
        ImageButton back = findViewById(R.id.back_button);
        back.setOnClickListener(view -> {
                onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}