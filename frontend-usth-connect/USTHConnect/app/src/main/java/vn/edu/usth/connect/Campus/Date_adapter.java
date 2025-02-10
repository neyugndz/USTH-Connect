package vn.edu.usth.connect.Campus;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.connect.Campus.TabLayout_Fragment.day1_fragment;
import vn.edu.usth.connect.Campus.TabLayout_Fragment.day2_fragment;
import vn.edu.usth.connect.Campus.TabLayout_Fragment.day3_fragment;
import vn.edu.usth.connect.Campus.TabLayout_Fragment.day4_fragment;
import vn.edu.usth.connect.Campus.TabLayout_Fragment.day5_fragment;
import vn.edu.usth.connect.Campus.TabLayout_Fragment.day6_fragment;
import vn.edu.usth.connect.Campus.TabLayout_Fragment.day7_fragment;

public class Date_adapter extends FragmentStateAdapter {
    public Date_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new day1_fragment();
            case 1:
                return new day2_fragment();
            case 2:
                return new day3_fragment();
            case 3:
                return new day4_fragment();
            case 4:
                return new day5_fragment();
            case 5:
                return new day6_fragment();
            case 6:
                return new day7_fragment();
            default:
                return new day1_fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}

