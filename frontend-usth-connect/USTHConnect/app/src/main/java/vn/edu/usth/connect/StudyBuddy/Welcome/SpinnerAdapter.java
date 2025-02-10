package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.edu.usth.connect.R;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> items;
    private String hint;

    public SpinnerAdapter(Context context, List<String> items, String hint) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        this.hint = hint;
    }

    @Override
    public int getCount() {
        return items.size() > 0 ? items.size() + 1 : 0;
    }

    @Override
    public String getItem(int position) {
        if (position == 0) {
            return hint;
        } else {
            return items.get(position - 1);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_box, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.hint);

        if (position == 0) {
            textView.setText(hint);
            textView.setTextColor(context.getResources().getColor(R.color.bottom_navigator_before));
        } else {
            textView.setText(items.get(position - 1));
            textView.setTextColor(context.getResources().getColor(R.color.white));
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_items, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.spinner_items);

        if (position == 0) {
            textView.setText(hint);
            textView.setTextColor(context.getResources().getColor(R.color.bottom_navigator_before));
        } else {
            textView.setText(items.get(position - 1));
            textView.setTextColor(context.getResources().getColor(R.color.white));
        }

        return convertView;
    }
}
