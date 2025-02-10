package vn.edu.usth.connect.Schedule.Favorite_RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Course.Course_RecyclerView.CourseItem;

public class Favorite_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private Favorite_course_Adapter favoriteCourseAdapter;
    private List<Favorite_course_Item> favoriteCourseItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_favorite_.xml
        View v = inflater.inflate(R.layout.fragment_favorite_, container, false);
        recyclerView = v.findViewById(R.id.favorite_course_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        favoriteCourseItems = loadAllFavouriteCourses();

        favoriteCourseAdapter = new Favorite_course_Adapter(getContext(), favoriteCourseItems);
        recyclerView.setAdapter(favoriteCourseAdapter);
        favoriteCourseAdapter.notifyDataSetChanged();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload favorite courses and update the RecyclerView
        favoriteCourseItems.clear();
        favoriteCourseItems.addAll(loadAllFavouriteCourses());
        favoriteCourseAdapter.notifyDataSetChanged();
    }

    private List<Favorite_course_Item> loadAllFavouriteCourses() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        Gson gson = new Gson();
        Type type = new TypeToken<List<CourseItem>>() {}.getType();

        // Load favorites from all activities
        List<CourseItem> favourites = new ArrayList<>();
        String[] keys = {"favourite_courses_first", "favourite_courses_second", "favourite_courses_third"};

        for (String key : keys) {
            String json = sharedPreferences.getString(key, "[]");
            List<CourseItem> courseList = gson.fromJson(json, type);
            favourites.addAll(courseList);
        }

        // Remove duplicates
        Set<CourseItem> uniqueFavourites = new HashSet<>(favourites);
        List<Favorite_course_Item> favoriteItems = new ArrayList<>();
        for (CourseItem courseItem : uniqueFavourites) {
            favoriteItems.add(convertToFavoriteCourseItem(courseItem));
        }

        return favoriteItems;
    }

    private Favorite_course_Item convertToFavoriteCourseItem(CourseItem courseItem) {
        return new Favorite_course_Item(
                courseItem.getHeading(),
                courseItem.getSubhead()
        );
    }
}