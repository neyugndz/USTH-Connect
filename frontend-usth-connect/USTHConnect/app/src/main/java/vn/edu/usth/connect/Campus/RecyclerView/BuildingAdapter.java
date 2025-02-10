package vn.edu.usth.connect.Campus.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.Campus.Detail.Detail_Building_Activity;
import vn.edu.usth.connect.R;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingViewHolder> {

    Context context;
    List<BuildingItem> items;

    public BuildingAdapter(Context context, List<BuildingItem> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public BuildingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BuildingViewHolder(LayoutInflater.from(context).inflate(R.layout.building_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingViewHolder holder, int position) {
        BuildingItem item = items.get(position);

        holder.heading.setText(item.getHeading());
        holder.subhead.setText(item.getSubhead());
        holder.imageView.setImageResource(item.getImage());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, Detail_Building_Activity.class);
            i.putExtra("Building Name", item.getHeading());
            i.putExtra("Building Locate", item.getSubhead());
            context.startActivity(i);
        });
    }

    public void setFilter(List<BuildingItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
