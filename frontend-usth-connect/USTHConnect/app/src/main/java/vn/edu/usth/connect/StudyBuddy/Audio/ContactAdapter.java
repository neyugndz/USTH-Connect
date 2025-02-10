package vn.edu.usth.connect.StudyBuddy.Audio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Message_RecyclerView.BoxChatItem;

public class ContactAdapter  extends RecyclerView.Adapter<ContactViewHolder> {

    Context context;
    List<ContactItem> items;

    public ContactAdapter(Context context, List<ContactItem> items){
        this.context = context;
        this.items = items == null ? new ArrayList<>() : items;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactItem item = items.get(position);

        holder.contact_name.setText(item.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, OutgoingCall.class);
            i.putExtra("Contact_Name", item.getName()); // Contact name: Move from AudioFragment to OutgoingCall
            i.putExtra("sip_username", item.getUsername()); // Username Sip Account: Move from MessageFragment to OutgoingCall
            i.putExtra("sip_password", item.getPassword()); // Password Sip Account: Move from MessageFragment to OutgoingCall
            i.putExtra("connectionId", item.getConnectionId()); // Connection ID
            i.putExtra("senderId", item.getSenderId()); // Sender ID
            i.putExtra("receiverId", item.getBuddyId()); // Receiver ID
            context.startActivity(i);
        });
    }

    public void setFilter(List<ContactItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<ContactItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }
}
