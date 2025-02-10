package vn.edu.usth.connect.Schedule.ScheduleCourse_RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class ScheduleCourseViewHolder extends RecyclerView.ViewHolder{

    TextView heading;

    public ScheduleCourseViewHolder(@NonNull View itemView){
        super(itemView);
        heading = itemView.findViewById(R.id.first_text);
    }
}
