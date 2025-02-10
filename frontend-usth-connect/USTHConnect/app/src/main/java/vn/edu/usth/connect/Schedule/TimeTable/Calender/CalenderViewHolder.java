package vn.edu.usth.connect.Schedule.TimeTable.Calender;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.usth.connect.R;

public class CalenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonth;
    private final CalenderAdapter.OnItemListener onItemListener;


    public CalenderViewHolder(@NonNull View itemView, CalenderAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days)
    {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView); // calender_cell
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }

    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }

}
