package vn.edu.usth.connect.StudyBuddy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.connect.StudyBuddy.Profile.Study_Buddy_Profile_Fragment;

public class Fragment_Study_Buddy_changeing extends FragmentStateAdapter {
    public Fragment_Study_Buddy_changeing(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Study_Buddy_Fragment();
            case 1:
                return new Message_Fragment();
            case 2:
                return new AudioFragment();
            case 3:
                return new Study_Buddy_Profile_Fragment();
            default:
                return new Study_Buddy_Fragment();
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}