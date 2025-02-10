package vn.edu.usth.connect.StudyBuddy.Audio;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.usth.connect.R;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    TextView contact_name;

    public ContactViewHolder(@NonNull View itemView){
        super(itemView);

        contact_name = itemView.findViewById(R.id.contact_boxchat);
    }
}
