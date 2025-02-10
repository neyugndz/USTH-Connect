package vn.edu.usth.connect.Schedule.Course.Course_RecyclerView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class CourseViewHolder extends RecyclerView.ViewHolder{

    TextView heading, subhead;
    ImageButton favorite_course_button;

    public CourseViewHolder(@NonNull View itemView){
        super(itemView);
        heading = itemView.findViewById(R.id.first_text);
        subhead = itemView.findViewById(R.id.second_text);
        favorite_course_button = itemView.findViewById(R.id.favourite_course_button);
    }
}
