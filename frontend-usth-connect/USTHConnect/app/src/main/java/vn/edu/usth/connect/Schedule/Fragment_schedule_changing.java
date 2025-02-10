package vn.edu.usth.connect.Schedule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.connect.Schedule.Course.Course_RecyclerView.FirstCourseFragment;
import vn.edu.usth.connect.Schedule.Course.Course_RecyclerView.SecondCourseFragment;
import vn.edu.usth.connect.Schedule.Course.Course_RecyclerView.ThirdCourseFragment;
import vn.edu.usth.connect.Schedule.ScheduleCourse_RecyclerView.ScheduleCourseFragment;
import vn.edu.usth.connect.Schedule.Favorite_RecyclerView.Favorite_Fragment;
import vn.edu.usth.connect.Schedule.TimeTable.Timetable_Fragment;

public class Fragment_schedule_changing extends FragmentStateAdapter {

    private final String studyYear;
    private final String major;

    public Fragment_schedule_changing(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String studyYear, String major) {
        super(fragmentManager, lifecycle);
        this.studyYear = studyYear;
        this.major = major;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Timetable_Fragment();
            case 1:
                return new Favorite_Fragment();
            case 2:
                if("B1".equals(studyYear)) {
                    return new FirstCourseFragment();
                } else if("B2".equals(studyYear)) {
                    SecondCourseFragment secondCourseFragment = new SecondCourseFragment();
                    Bundle args = new Bundle();
                    args.putString("major", major);  // Pass the major value
                    secondCourseFragment.setArguments(args);
                    return secondCourseFragment;
                } else if("B3".equals(studyYear)) {
                    ThirdCourseFragment thirdCourseFragment = new ThirdCourseFragment();
                    Bundle args = new Bundle();
                    args.putString("major", major);  // Pass the major value
                    thirdCourseFragment.setArguments(args);
                    return thirdCourseFragment;
                } else {
                    return new ScheduleCourseFragment();
                }
            default:
                return new Timetable_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}