package vn.edu.usth.connect.Schedule.TimeTable;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static  vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils.daysInWeekArray;
import static  vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils.monthYearFromDate;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderAdapter;
import vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils;
import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;

public class WeekViewActivity extends AppCompatActivity implements CalenderAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_week_view);

        // Setup RecyclerView
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);

        // Setup for getText
        monthYearText = findViewById(R.id.monthYearTV);

        // List Event happen in day
        eventListView = findViewById(R.id.eventListView);

        // Show week
        setWeekView();

        // Button Function
        setup_function();
    }

    // 4 sure show the week
    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalenderUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalenderUtils.selectedDate);

        CalenderAdapter calendarAdapter = new CalenderAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);

        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        // Setup Event
        setEventAdpater();
    }

    // Button move previous week
    public void previousWeekAction(View view) {
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    // Button move next week
    public void nextWeekAction(View view) {
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalenderUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdpater();
    }

    // Daily Event ArrayList
    private void setEventAdpater() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalenderUtils.selectedDate);

        // Use WeeklyEventAdapter instead of EventAdapter
        WeeklyEventAdapter weeklyEventAdapter = new WeeklyEventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(weeklyEventAdapter);
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