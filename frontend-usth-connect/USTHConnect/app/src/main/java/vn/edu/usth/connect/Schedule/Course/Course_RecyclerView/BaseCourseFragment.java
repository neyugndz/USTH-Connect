package vn.edu.usth.connect.Schedule.Course.Course_RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Course.Event_RecyclerView.List_Class_in_Course_Activity;

public abstract class BaseCourseFragment extends Fragment {
    protected List<CourseItem> items;
    protected CourseAdapter adapter;
    protected List<CourseItem> favouriteCourses;
    protected String major;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_course_fragment, container, false);

        setupRecyclerView(view);
        setupSearchView(view);


        favouriteCourses = loadFavouriteCourses();
        syncFavoritesWithItems();
        return view;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    protected abstract int getLayoutId();

    protected abstract List<CourseItem> getCourseData();

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.course_recycler_view);
        items = getCourseData();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CourseAdapter(getContext(), items, courseItem -> {
            if (courseItem.isFavourite()) {
                if (!favouriteCourses.contains(courseItem)) favouriteCourses.add(courseItem);
            } else {
                favouriteCourses.remove(courseItem);
            }
            saveFavouriteCourses();
        });
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView(View view) {
        SearchView searchView = view.findViewById(R.id.course_search_view);
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

    private void filterList(String text) {
        List<CourseItem> filteredItems = new ArrayList<>();
        for (CourseItem item : items) {
            if (item.getHeading().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilter(filteredItems);
        }
    }

    private void syncFavoritesWithItems() {
        for (CourseItem item : items) {
            for (CourseItem favorite : favouriteCourses) {
                if (item.getHeading().equals(favorite.getHeading())) {
                    item.setFavourite(true);
                    break;
                }
            }
        }
    }

    private List<CourseItem> loadFavouriteCourses() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String json = sharedPreferences.getString(getFavouriteKey(), "[]");
        Type type = new TypeToken<List<CourseItem>>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    private void saveFavouriteCourses() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(favouriteCourses);
        editor.putString(getFavouriteKey(), json);
        editor.apply();
    }

    protected abstract String getFavouriteKey();

    protected void navigateToCourseDetails(CourseItem course){
        Intent intent = new Intent(getContext(), List_Class_in_Course_Activity.class);
        intent.putExtra("Course Name", course.getHeading());
        intent.putExtra("Course Lecturer", course.getSubhead());
        startActivity(intent);
    }
}
