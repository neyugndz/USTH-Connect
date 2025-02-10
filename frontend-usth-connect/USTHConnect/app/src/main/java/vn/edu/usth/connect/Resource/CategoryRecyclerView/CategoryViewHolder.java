package vn.edu.usth.connect.Resource.CategoryRecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder{

    TextView heading;

    public CategoryViewHolder(@NonNull View itemView){
        super(itemView);
        heading = itemView.findViewById(R.id.first_text);
    }

}
