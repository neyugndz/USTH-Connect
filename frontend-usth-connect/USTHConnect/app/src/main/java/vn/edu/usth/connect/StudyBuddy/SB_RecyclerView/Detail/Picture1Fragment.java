package vn.edu.usth.connect.StudyBuddy.SB_RecyclerView.Detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.usth.connect.R;

public class Picture1Fragment extends Fragment {

    // Picture for UserDetail
    // Dynamic
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture1, container, false);
    }
}