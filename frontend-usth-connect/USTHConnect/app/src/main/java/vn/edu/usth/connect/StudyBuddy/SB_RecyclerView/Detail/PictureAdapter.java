package vn.edu.usth.connect.StudyBuddy.SB_RecyclerView.Detail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PictureAdapter extends FragmentStateAdapter {
    public PictureAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Picture1Fragment();
            case 1:
                return new Picture2Fragment();
            case 2:
                return new Picture1Fragment();
            default:
                return new Picture1Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;

    }
}
