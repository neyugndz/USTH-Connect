package vn.edu.usth.connect.StudyBuddy.Message_RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;

public class BoxChatAdapter extends RecyclerView.Adapter<BoxChatViewHolder>{

    Context context;
    List<BoxChatItem> items;

    public BoxChatAdapter(Context context, List<BoxChatItem> items){
        this.context = context;
        this.items = items == null ? new ArrayList<>() : items;
    }

    @NonNull
    @Override
    public BoxChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoxChatViewHolder(LayoutInflater.from(context).inflate(R.layout.boxchat_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoxChatViewHolder holder, int position) {
        BoxChatItem item = items.get(position);

        holder.boxchat_name.setText(item.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra("BoxChat_Name", item.getName()); // BoxChat name: Move from MessageFragment to ChatActivity
            i.putExtra("sip_username", item.getUsername()); // Username Sip Account: Move from MessageFragment to ChatActivity
            i.putExtra("sip_password", item.getPassword()); // Password Sip Account: Move from MessageFragment to ChatActivity
            i.putExtra("connectionId", item.getConnectionId()); // Connection ID
            i.putExtra("senderId", item.getSenderId()); // Sender ID
            i.putExtra("receiverId", item.getBuddyId()); // Receiver ID
            context.startActivity(i);
        });
    }

    public void setFilter(List<BoxChatItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<BoxChatItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }
}
