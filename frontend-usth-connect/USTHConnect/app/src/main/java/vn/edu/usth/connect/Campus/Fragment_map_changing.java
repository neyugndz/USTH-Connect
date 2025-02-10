package vn.edu.usth.connect.Campus;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Fragment_map_changing extends FragmentStateAdapter {
        public Fragment_map_changing(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);

        }@NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                return new Building_Fragment();
                case 1:
                return new Map_Fragment();
                default:
                return new Building_Fragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
}
