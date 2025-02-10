package vn.edu.usth.connect.Resource.SubCategoryRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.YearActivity;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    private Context context;
    private List<SubCategoryItem> subCategoryItems;

    public SubCategoryAdapter(Context context, List<SubCategoryItem> subCategoryItems) {
        this.context = context;
        this.subCategoryItems = subCategoryItems;
    }

    // Method to update the dataset
    public void setSubCategoryItems(List<SubCategoryItem> items) {
        this.subCategoryItems.clear();
        this.subCategoryItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_text_frame, parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        SubCategoryItem subCategory = subCategoryItems.get(position);
        Log.d("SubCategoryAdapter", "Binding SubCategory: " + subCategory.getName());

        holder.heading.setText(subCategory.getName());

        // Set click listener on the itemView
        holder.itemView.setOnClickListener(view -> {
            // Redirect to YearActivity with the subCategoryId
            Intent intent = new Intent(context, YearActivity.class);
            intent.putExtra("subCategoryId", subCategory.getCategoryId()); // Pass subCategoryId
            intent.putExtra("Program Name", subCategory.getName()); // pass the subcategory name as programName
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryItems.size();
    }

    public static class SubCategoryViewHolder extends RecyclerView.ViewHolder {

        TextView heading;

        public SubCategoryViewHolder(View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.first_text);
        }
    }
}
