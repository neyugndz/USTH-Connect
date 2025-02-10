package vn.edu.usth.connect.Campus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.Student;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudentService;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.CategoryRecyclerView.CategoryActivity;
import vn.edu.usth.connect.Schedule.Schedule_Activity;
import vn.edu.usth.connect.Utils.LogoutUtils;

public class Campus_Activity extends AppCompatActivity {

    private ViewPager2 mviewPager;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout mDrawerLayout;

    private ImageView avatar_profile_image;

    Handler handler;

    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_campus.xml
        setContentView(R.layout.activity_campus);

        // Set Visible and Gone
        findViewById(R.id.loading_layout).setVisibility(View.VISIBLE);
        findViewById(R.id.campus_layout).setVisibility(View.GONE);

        // Delay 10sec
        // Loading
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.loading_layout).setVisibility(View.GONE);
                findViewById(R.id.campus_layout).setVisibility(View.VISIBLE);
            }
        }, 5000);

        // ViewPager2: Change fragments: BuildingFragment and MapFragment
        mviewPager = findViewById(R.id.view_campus_pager);

        // BottomNavigation: Bottom Menu :D
        bottomNavigationView = findViewById(R.id.campus_bottom_navigation);

        // Adapter: Fragment_map_changing
        Fragment_map_changing adapter = new Fragment_map_changing(getSupportFragmentManager(), getLifecycle());
        mviewPager.setAdapter(adapter);
        mviewPager.setUserInputEnabled(false);

        // ViewPager2 setup Function
        mviewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.building_page).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.map_page).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        // BottomNavigation setup Function
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.building_page) {
                    mviewPager.setCurrentItem(0, true); // Switch to the first fragment
                    return true;
                }
                if (item.getItemId() == R.id.map_page) {
                    mviewPager.setCurrentItem(1, true); // Switch to the first fragment
                    return true;
                }
                return false;
            }
        });

        // Setup Side-menu for Activity
        mDrawerLayout= findViewById(R.id.campus_activity);

        // Function to open Side-menu
        ImageButton mImageView = findViewById(R.id.menu_button);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // Side-menu function
        navigator_drawer_function();

        // Load image in the Side-menu
        update_picture();

        // Set Name in Side Menu
        fetchUserProfile();
    }

    private void navigator_drawer_function(){
        LinearLayout to_home_activity = findViewById(R.id.to_home_page);
        to_home_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Campus.Campus_Activity.this, vn.edu.usth.connect.MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        LinearLayout to_schedule_activity = findViewById(R.id.to_schedule_page);
        to_schedule_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Campus.Campus_Activity.this, vn.edu.usth.connect.Schedule.Schedule_Activity.class);
                startActivity(i);
                finish();
            }
        });

        LinearLayout to_campus_activity = findViewById(R.id.to_map_page);
        to_campus_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Campus.Campus_Activity.this, vn.edu.usth.connect.Campus.Campus_Activity.class);
                startActivity(i);
                finish();
            }
        });

        LinearLayout to_resource_activity = findViewById(R.id.to_resource_page);
        to_resource_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Campus.Campus_Activity.this, CategoryActivity.class);
                startActivity(i);
                finish();
            }
        });

        LinearLayout to_study_activity = findViewById(R.id.to_study_page);
        to_study_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.Campus.Campus_Activity.this, vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.class);
                startActivity(i);
                finish();
            }
        });

        LinearLayout logout = findViewById(R.id.to_log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutUtils.getInstance().logoutUser(Campus_Activity.this);
            }
        });

    }

    private void update_picture(){
        avatar_profile_image = findViewById(R.id.avatar_profile);

        // Get SharePreference
        SharedPreferences sharedPreferences = getSharedPreferences("ProfileImage", MODE_PRIVATE);
        String imageUriString = sharedPreferences.getString("Image_URI", null);

        // Load Image and Set Image
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            Glide.with(this).load(imageUri).into(avatar_profile_image);
        }
    }

    // Like Profile Fragment
    private void fetchUserProfile() {
        // Get token and studentId from SessionManager
        String token = SessionManager.getInstance().getToken();
        String studentId = SessionManager.getInstance().getStudentId();

        if(!token.isEmpty() && !studentId.isEmpty()) {
            String authHeader = "Bearer " + token;

            // Create an instance of Retrofit and call the API
            StudentService studentService = RetrofitClient.getInstance().create(StudentService.class);
            Call<Student> call = studentService.getStudentProfile(authHeader, studentId);
            call.enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Student student = response.body();
                        Log.d("ProfileFragment", "Student data: " + student);  // Log student data
                        set_text_menu(student);
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                }
            });

        }
    }

    private void set_text_menu(Student student){
        name = findViewById(R.id.welcome_message);
        name.setText("Hi, " + student.getFullName());
    }
}