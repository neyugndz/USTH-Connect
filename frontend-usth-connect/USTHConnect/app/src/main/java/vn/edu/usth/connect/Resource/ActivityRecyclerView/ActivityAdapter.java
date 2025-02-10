package vn.edu.usth.connect.Resource.ActivityRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.Models.Moodle.Activity;
import vn.edu.usth.connect.R;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<Activity> activities;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Activity activity);
    }

    public ActivityAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities != null ? activities : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_text_frame, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        Activity activity = activities.get(position);
        holder.heading.setText(activity.getActivityName());
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(activity));
    }

    @Override
    public int getItemCount() {
        return activities == null ? 0 : activities.size();
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView heading;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.first_text);
        }
    }

}
