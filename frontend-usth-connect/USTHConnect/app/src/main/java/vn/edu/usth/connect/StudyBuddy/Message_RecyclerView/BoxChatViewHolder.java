package vn.edu.usth.connect.StudyBuddy.Message_RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class BoxChatViewHolder extends RecyclerView.ViewHolder {

    TextView boxchat_name;

    public BoxChatViewHolder(@NonNull View itemView) {
        super(itemView);

        boxchat_name = itemView.findViewById(R.id.user_boxchat);
    }
}
