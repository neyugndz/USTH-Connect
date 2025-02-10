package vn.edu.usth.connect.Schedule.TimeTable;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.usth.connect.R;

import vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils;
import vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderAdapter;

import static  vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils.daysInMonthArray;
import static  vn.edu.usth.connect.Schedule.TimeTable.Calender.CalenderUtils.monthYearFromDate;

public class Timetable_Fragment extends Fragment implements CalenderAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timetable_, container, false);

        // Call RecyclerView and TextView
        calendarRecyclerView = v.findViewById(R.id.calendarRecyclerView);

        // Text: Use for setText
        monthYearText = v.findViewById(R.id.monthYearTV);

        // Get Local Time: 2024 - 12 - Day
        CalenderUtils.selectedDate = LocalDate.now();

        // Show days in month in week view(?)
        setMonthView();

        // Button Setup
        setup_function(v);

        return v;
    }

    public void setMonthView() {
        // SetText to the format MMMM - YYYY
        monthYearText.setText(monthYearFromDate(CalenderUtils.selectedDate));

        // Setup and Show day in month
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();

        // Draw
        CalenderAdapter calendarAdapter = new CalenderAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 7);

        // Setup View Week in Month
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    // Press Day in Month
    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalenderUtils.selectedDate = date;

            // Create an Intent to open DailyCalenderActivity
            Intent intent = new Intent(requireContext(), DailyCalenderActivity.class);
            intent.putExtra("selectedDate", date.toString()); // Pass the selected date as a String
            startActivity(intent);
        }
    }

    // Button Week Function
    public void weeklyAction(View view) {
        startActivity(new Intent(requireContext(), WeekViewActivity.class));
    }

    // Button Daily Function
    public void dailyAction(View view) {
        startActivity(new Intent(requireContext(), DailyCalenderActivity.class));
    }

    // Function Previous and Next Month
    public void previousMonthAction(View view) {
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.minusMonths(1);
        setMonthView();
    }
    public void nextMonthAction(View view) {
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    private void setup_function(View v){
        Button prev_month = v.findViewById(R.id.previousMonthAction);
        prev_month.setOnClickListener(view -> {
            previousMonthAction(view);
        });

        Button next_month = v.findViewById(R.id.nextMonthAction);
        next_month.setOnClickListener(view -> {
            nextMonthAction(view);
        });

        Button week_action = v.findViewById(R.id.weeklyAction);
        week_action.setOnClickListener(view -> {
            weeklyAction(view);
        });
//        Button daily_action = v.findViewById(R.id.dailyAction);
//        daily_action.setOnClickListener(view -> {
//            dailyAction(view);
//        });
    }
}