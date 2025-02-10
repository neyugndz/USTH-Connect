package vn.edu.usth.connect.Schedule.Course.Course_RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Course.Event_RecyclerView.List_Class_in_Course_Activity;

public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder>{

    Context context;
    List<CourseItem> items;
    private OnFavouriteClickListener favouriteClickListener;

    public interface OnFavouriteClickListener {
        void onFavouriteClicked(CourseItem courseItem);
    }

    public CourseAdapter(Context context, List<CourseItem> items, OnFavouriteClickListener listener){
        this.context = context;
        this.items = items;
        this.favouriteClickListener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseViewHolder(LayoutInflater.from(context).inflate(R.layout.course_fav_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
       CourseItem item = items.get(position);

        holder.heading.setText(item.getHeading());
        holder.subhead.setText(item.getSubhead());
        holder.favorite_course_button.setSelected(item.isFavourite());

        holder.favorite_course_button.setOnClickListener(view -> {
            item.setFavourite(!item.isFavourite());
            notifyItemChanged(position);
            favouriteClickListener.onFavouriteClicked(item);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, List_Class_in_Course_Activity.class);
            i.putExtra("Course Name", item.getHeading());
            i.putExtra("Course Lecturer", item.getSubhead());
            context.startActivity(i);
        });
    }

    public void setFilter(List<CourseItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
