package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import vn.edu.usth.connect.Login.LoginFragment;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.CategoryRecyclerView.CategoryActivity;

public class WelcomeFragment extends Fragment {

    private DrawerLayout mDrawerLayout;

    // Welcome - Register SIP Account and
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_welcome.xml
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button next_button = v.findViewById(R.id.create_new_account);
        next_button.setOnClickListener(view -> {
           navigatorToNextFragment(); 
        });

        // Setup Side-menu for Fragment
        mDrawerLayout= v.findViewById(R.id.welcome_fragment_page);

        // Function to open Side-menu
        ImageButton mImageView = v.findViewById(R.id.menu_button);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // Side-menu function
        navigator_drawer_function(v);
        
        return v;
    }

    private void navigator_drawer_function(View v){
        LinearLayout to_home_activity = v.findViewById(R.id.to_home_page);
        to_home_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.MainActivity.class);
                startActivity(i);
            }
        });

        LinearLayout to_schedule_activity = v.findViewById(R.id.to_schedule_page);
        to_schedule_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.Schedule.Schedule_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_campus_activity = v.findViewById(R.id.to_map_page);
        to_campus_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.Campus.Campus_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_resource_activity = v.findViewById(R.id.to_resource_page);
        to_resource_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), CategoryActivity.class);
                startActivity(i);
            }
        });

        LinearLayout to_study_activity = v.findViewById(R.id.to_study_page);
        to_study_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout logout = v.findViewById(R.id.to_log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment logoutFragment = new LoginFragment();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(android.R.id.content, logoutFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
    
    // Move to Next Fragment
    private void navigatorToNextFragment(){
        Fragment nameFragment = new NameFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, nameFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    
}