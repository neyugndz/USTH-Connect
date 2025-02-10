package vn.edu.usth.connect.Schedule.Favorite_RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Course.Event_RecyclerView.List_Class_in_Course_Activity;
import vn.edu.usth.connect.Schedule.Course.Course_RecyclerView.CourseItem;


public class Favorite_course_Adapter extends RecyclerView.Adapter<Favorite_course_ViewHolder> {

    Context context;
    List<Favorite_course_Item> items;
    SharedPreferences sharedPreferences;
    Gson gson;

    public Favorite_course_Adapter(Context context, List<Favorite_course_Item> items){
        this.context = context;
        this.items = items;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public Favorite_course_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Favorite_course_ViewHolder(LayoutInflater.from(context).inflate(R.layout.double_text_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Favorite_course_ViewHolder holder, int position) {
        Favorite_course_Item item = items.get(position);

        holder.heading.setText(item.getHeading());
        holder.subhead.setText(item.getSubhead());
        holder.favouriteButton.setOnClickListener(view -> {
            items.remove(position);
            notifyItemRemoved(position);
            
            removeFromFavourites(item);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, List_Class_in_Course_Activity.class);
            i.putExtra("Course Name", item.getHeading());
            i.putExtra("Course Lecturer", item.getSubhead());
            context.startActivity(i);
        });
    }

    private void removeFromFavourites(Favorite_course_Item item) {
        String[] keys = {"favourite_courses_first", "favourite_courses_second", "favourite_courses_third"};
        for (String key : keys) {
            String json = sharedPreferences.getString(key, "[]");
            Type type = new TypeToken<List<CourseItem>>() {}.getType();
            List<CourseItem> courses = gson.fromJson(json, type);

            courses.removeIf(course ->
                    Objects.equals(course.getHeading(), item.getHeading()) &&
                            Objects.equals(course.getSubhead(), item.getSubhead())
            );

            sharedPreferences.edit().putString(key, gson.toJson(courses)).apply();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
