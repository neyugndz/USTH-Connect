package vn.edu.usth.connect.StudyBuddy.SB_RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class Rcm_UserViewHolder extends RecyclerView.ViewHolder{

    TextView name, gender, major, looking_for;
    ImageView image;
    Button connectButton;


    public Rcm_UserViewHolder(View itemView){
        super(itemView);

        name = itemView.findViewById(R.id.name);
        gender = itemView.findViewById(R.id.gender);
        major = itemView.findViewById(R.id.major);
        looking_for = itemView.findViewById(R.id.looking_for);
        image = itemView.findViewById(R.id.image_view);
        connectButton = itemView.findViewById(R.id.btn_connect);
    }
}
