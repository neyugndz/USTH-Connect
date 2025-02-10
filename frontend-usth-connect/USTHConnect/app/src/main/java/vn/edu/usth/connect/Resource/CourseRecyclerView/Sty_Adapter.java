package vn.edu.usth.connect.Resource.CourseRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.connect.R;

public class Sty_Adapter extends RecyclerView.Adapter<Sty_ViewHolder>{

    Context context;
    List<Sty_Item> items;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public Sty_Adapter(Context context, List<Sty_Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Sty_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Sty_ViewHolder(LayoutInflater.from(context).inflate(R.layout.double_text_frame_no_star, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Sty_ViewHolder holder, int position) {
        Sty_Item item = items.get(position);

        holder.heading.setText(item.getHeading());
        holder.subhead.setText(item.getSubhead());

        holder .itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    public void setFilter(List<Sty_Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

}
