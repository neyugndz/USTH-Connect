package vn.edu.usth.connect.Schedule.ScheduleCourse_RecyclerView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;

public class ScheduleCourseFragment extends Fragment {

    private List<ScheduleCourseItem> items;
    private ScheduleCourseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_course_.xml
        View v = inflater.inflate(R.layout.fragment_course_, container, false);

        // Setup Recyclerview for Course and SearchView
        setup_recyclerview_function(v);

        return v;
    }

    // SetUp RecyclerView and SearchView
    // Folder: Course_RecyclerView: Course_course_Item, Course_course_Adapter, Course_course_ViewHolder
    private void setup_recyclerview_function(View v){
        // RecyclerView point to Course.First_Course_Activity
        // or
        // Course.Program_Activity
        RecyclerView recyclerView = v.findViewById(R.id.course_course_recyclerview);

        items = new ArrayList<ScheduleCourseItem>();

        items.add(new ScheduleCourseItem("First Year - Sciences (three-year program)"));
        items.add(new ScheduleCourseItem("Information and Communication Technology"));
        items.add(new ScheduleCourseItem("Pharmacological Medical and Agronomical Biotechnology"));
        items.add(new ScheduleCourseItem("Advanced Materials Science and Nanotechnology"));
        items.add(new ScheduleCourseItem("Applied Engineering and Technology (AET)"));
        items.add(new ScheduleCourseItem("Applied Environmental Sciences"));
        items.add(new ScheduleCourseItem("Space and Applications"));
        items.add(new ScheduleCourseItem("Medical Science and Technology (MST)"));
        items.add(new ScheduleCourseItem("Food Science and Technology (FST)"));
        items.add(new ScheduleCourseItem("Aeronautical Maintenance and Engineering"));
        items.add(new ScheduleCourseItem("Chemistry"));
        items.add(new ScheduleCourseItem("Applied Mathematics"));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ScheduleCourseAdapter(requireContext(), items);
        recyclerView.setAdapter(adapter);

        // SearchView
        SearchView searchView = v.findViewById(R.id.fav_course_searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

    }

    // Filter for SearchView
    private void filterList(String text) {
        List<ScheduleCourseItem> filteredItems = new ArrayList<>();

        for (ScheduleCourseItem item : items) {
            if (item.getHeading().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.setFilter(filteredItems);
        }

    }

}