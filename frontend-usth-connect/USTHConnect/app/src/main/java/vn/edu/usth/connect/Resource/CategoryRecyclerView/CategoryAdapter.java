package vn.edu.usth.connect.Resource.CategoryRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.First_Year.Semester_Activity;
import vn.edu.usth.connect.Resource.SubCategoryRecyclerView.SubCategoryActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{

    private Context context;
//    private List<Course> courses = new ArrayList<>();
    private List<CategoryItem> items;

//    public Resource_course_Adapter(Context context, List<Course> courses){
//        this.context = context;
//        this.courses = courses;
//    }
    public CategoryAdapter(Context context, List<CategoryItem> items){
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.one_text_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem item = items.get(position);


        holder.heading.setText(item.getHeading());

        holder.itemView.setOnClickListener(v -> {
            if(item.getHeading().equals("First Year - Sciences (three-year program)")){
                Intent i = new Intent(context, Semester_Activity.class);
                context.startActivity(i);
            } else {
                Intent i = new Intent(context, SubCategoryActivity.class);
                i.putExtra("Program Name", item.getHeading());
                i.putExtra("categoryId", item.getCategoryId());
                Log.d("CategoryActivity", "CategoryId: " + item.getCategoryId());

                context.startActivity(i);
            }
        });
    }

    public void setFilter(List<CategoryItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

//    public void setFilter(List<Course> courses){
//        this.courses = courses;
//        notifyDataSetChanged();
//    }
//
//    // Setter to update courses
//    public void setCourses(List<Course> courses) {
//        this.courses = courses != null ? courses : new ArrayList<>();
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemCount() {
//        return courses.size();
//    }

}
