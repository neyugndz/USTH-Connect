package vn.edu.usth.connect.Schedule.ScheduleCourse_RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Course.Course_RecyclerView.FirstCourseFragment;


public class ScheduleCourseAdapter extends RecyclerView.Adapter<ScheduleCourseViewHolder>{

    Context context;
    List<ScheduleCourseItem> items;

    public ScheduleCourseAdapter(Context context, List<ScheduleCourseItem> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ScheduleCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleCourseViewHolder(LayoutInflater.from(context).inflate(R.layout.one_text_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleCourseViewHolder holder, int position) {
        ScheduleCourseItem item = items.get(position);

        holder.heading.setText(item.getHeading());

        holder.itemView.setOnClickListener(v -> {
            if(item.getHeading().equals("First Year - Sciences (three-year program)")){
                Intent i = new Intent(context, FirstCourseFragment.class);
                i.putExtra("Program Name", item.getHeading());
                context.startActivity(i);
            }
//            else{
//                Intent i = new Intent(context, Program_Activity.class);
//                i.putExtra("Program Name", item.getHeading());
//                context.startActivity(i);
//            }
        });
    }

    public void setFilter(List<ScheduleCourseItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
