package vn.edu.usth.connect.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.usth.connect.R;

public class DashboardFragment extends Fragment {

    // Function todo1: Show Today's class
    // Function todo2: Show Last Visited Course
    // Function todo3: Show Last Sb Contact
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_dashboard.xml
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        return v;
    }

}