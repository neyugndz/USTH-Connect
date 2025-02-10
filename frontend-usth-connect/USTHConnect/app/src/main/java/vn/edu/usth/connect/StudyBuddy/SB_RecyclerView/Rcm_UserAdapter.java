package vn.edu.usth.connect.StudyBuddy.SB_RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.SB_RecyclerView.Detail.UserDetailActivity;

public class Rcm_UserAdapter extends RecyclerView.Adapter<Rcm_UserViewHolder> {

    Context context;
    List<Rcm_UserItem> items;
    private OnConnectListener onConnectListener;

    public Rcm_UserAdapter(Context context, List<Rcm_UserItem> item,  OnConnectListener onConnectListener) {
        this.context = context;
        this.items = item;
        this.onConnectListener = onConnectListener;
    }

    // Method to update the items list
    public void setItems(List<Rcm_UserItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();  // Notify RecyclerView about the change
    }

    @NonNull
    @Override
    public Rcm_UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Rcm_UserViewHolder(LayoutInflater.from(context).inflate(R.layout.rcm_user_frame, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull Rcm_UserViewHolder holder, int position) {
        Rcm_UserItem item = items.get(position);

        holder.name.setText(item.getName());
        holder.gender.setText(item.getGender());
        holder.major.setText(item.getMajor());
        holder.looking_for.setText(item.getLooking_for());
        holder.image.setImageResource(item.getImage());
        // Handle connect button click
        holder.connectButton.setOnClickListener(v -> {
            if (onConnectListener != null) {
                onConnectListener.onConnect(item);
            }
        });

        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(context, UserDetailActivity.class);
            i.putExtra("StudentId", item.getStudentId());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnConnectListener {
        void onConnect(Rcm_UserItem item);
    }

}
