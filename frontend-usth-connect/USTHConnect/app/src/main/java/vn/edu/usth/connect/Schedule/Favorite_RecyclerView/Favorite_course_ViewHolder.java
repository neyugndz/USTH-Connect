package vn.edu.usth.connect.Schedule.Favorite_RecyclerView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class Favorite_course_ViewHolder extends RecyclerView.ViewHolder{

    TextView heading, subhead;
    ImageButton favouriteButton;

    public Favorite_course_ViewHolder(@NonNull View itemView){
        super(itemView);
        heading = itemView.findViewById(R.id.first_text);
        subhead = itemView.findViewById(R.id.second_text);
        favouriteButton = itemView.findViewById(R.id.favourite_course_button);
    }
}
