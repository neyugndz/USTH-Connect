package vn.edu.usth.connect.Schedule.Course.Event_RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;

public class List_Class_in_Course_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_list_class_in_course.xml
        setContentView(R.layout.activity_list_class_in_course);

        // Button Function
        setup_function();

        // Set text for RecyclerView
        setup_text_recycler();

    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setup_text_recycler(){
        TextView course_name = findViewById(R.id.course_name_timetable);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_events);
        TextView noEventsText = findViewById(R.id.no_events_text);
        String name = getIntent().getStringExtra("Course Name");

        course_name.setText(name);

        progressBar.setVisibility(View.VISIBLE);

        // Post a Runnable on the main thread to execute event filtering without thread
        Runnable filterEventsRunnable = new Runnable() {
            @Override
            public void run() {
                ArrayList<Event> filteredEvents = Event.eventsForCourse(name);

                // Sort events by their start time, with upcoming events first
                filteredEvents.sort((event1, event2) -> {
                    if (event1.getEventStartDateTime().isBefore(LocalDateTime.now()) &&
                            event2.getEventStartDateTime().isAfter(LocalDateTime.now())) {
                        return 1; // Passed event goes after the upcoming event
                    }
                    if (event1.getEventStartDateTime().isAfter(LocalDateTime.now()) &&
                            event2.getEventStartDateTime().isBefore(LocalDateTime.now())) {
                        return -1; // Upcoming event goes before the passed event
                    }
                    return event1.getEventStartDateTime().compareTo(event2.getEventStartDateTime());
                });

                // Update the UI on the main thread
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(List_Class_in_Course_Activity.this));
                    recyclerView.setAdapter(new EventForCourseAdapter(filteredEvents));
                });
            }
        };

        // Execute the filtering task on a background thread using Handler
        Handler handler = new Handler();
        handler.post(filterEventsRunnable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}