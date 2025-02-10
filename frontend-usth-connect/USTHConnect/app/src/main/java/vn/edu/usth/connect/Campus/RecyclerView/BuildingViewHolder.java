package vn.edu.usth.connect.Campus.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class BuildingViewHolder extends RecyclerView.ViewHolder{

    TextView heading, subhead;
    ImageView imageView;

    public BuildingViewHolder(@NonNull View itemView){
        super(itemView);
        imageView = itemView.findViewById(R.id.image_building);
        heading = itemView.findViewById(R.id.building_name);
        subhead = itemView.findViewById(R.id.building_locate);
    }
}
