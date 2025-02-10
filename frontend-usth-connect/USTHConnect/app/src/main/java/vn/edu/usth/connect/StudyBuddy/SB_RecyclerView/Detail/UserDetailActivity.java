package vn.edu.usth.connect.StudyBuddy.SB_RecyclerView.Detail;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddy;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudyBuddyService;
import vn.edu.usth.connect.R;

public class UserDetailActivity extends AppCompatActivity {

    // Detail StudyBuddy Profile
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_user)detail.xml
        setContentView(R.layout.activity_user_detail);

        // Initialize ChipGroups
        ChipGroup interestsChipGroup = findViewById(R.id.interestsChipGroup);
        ChipGroup favoriteSubjectsChipGroup = findViewById(R.id.favoriteSubjectsChipGroup);
        ChipGroup studyLocationChipGroup = findViewById(R.id.studyPlaceChipGroup);
        ChipGroup studyPlaceChipGroup = findViewById(R.id.studyTimeChipGroup);

        // Call ID
        TextView name = findViewById(R.id.name);
        TextView profile_name = findViewById(R.id.profile_detail);
        TextView gender = findViewById(R.id.gender);
        TextView major = findViewById(R.id.major);
        TextView looking_for = findViewById(R.id.looking_for);
        TextView communication_style = findViewById(R.id.communication_style);
        TextView personality = findViewById(R.id.personality);

        // SetText
        String studentId = getIntent().getStringExtra("StudentId");

        // Fetch details using the ID
        String token = SessionManager.getInstance().getToken();
        String authHeader = "Bearer " + token;

        StudyBuddyService service = RetrofitClient.getInstance().create(StudyBuddyService.class);
        service.getStudyBuddy(authHeader, studentId).enqueue(new Callback<StudyBuddy>() {
            @Override
            public void onResponse(Call<StudyBuddy> call, Response<StudyBuddy> response) {
                if(response.isSuccessful() && response.body() != null) {
                    StudyBuddy buddy = response.body();

                    // Set details to views
                    name.setText(buddy.getName());
                    profile_name.setText(buddy.getName());
                    gender.setText(buddy.getGender());
                    major.setText(buddy.getMajor());
                    looking_for.setText("Looking For: " + buddy.getLookingFor());
                    personality.setText(buddy.getPersonality());
                    communication_style.setText("Communication Style: " + buddy.getCommunicationStyle());
                    populateChipGroup(interestsChipGroup, buddy.getInterests());
                    populateChipGroup(favoriteSubjectsChipGroup, buddy.getFavoriteSubjects());
                    populateChipGroup(studyLocationChipGroup, buddy.getPreferredPlaces());
                    populateChipGroup(studyPlaceChipGroup, buddy.getPreferredTimes());
                }
            }

            @Override
            public void onFailure(Call<StudyBuddy> call, Throwable t) {
                Log.e("UserDetailActivity", "Error fetching user details", t);
            }
        });

        // TablLayout & ViewPager
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.picture_viewPager2);

        PictureAdapter adapter = new PictureAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText("");
            }
        }).attach();

        // Button Function
        setup_function();
    }

    private void populateChipGroup(ViewGroup chipGroup, List<String> items) {
        chipGroup.removeAllViews();
        if (items != null) {
            for (String item : items) {
                Chip chip = new Chip(this);
                chip.setText(item);
                chip.setChipBackgroundColorResource(R.color.blue_usth);
                chip.setTextColor(getResources().getColor(R.color.white));
                chip.setCloseIconVisible(false); // Disable close icon
                chipGroup.addView(chip);
            }
        }
    }


    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}