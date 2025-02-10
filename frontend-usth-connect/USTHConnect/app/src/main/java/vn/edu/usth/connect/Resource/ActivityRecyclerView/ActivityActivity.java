package vn.edu.usth.connect.Resource.ActivityRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.Moodle.Activity;
import vn.edu.usth.connect.Network.ResourceService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.ResourceRecyclerView.ResourceActivity;

public class ActivityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_year_course_resource.xml
        setContentView(R.layout.activity_year_course_resource);
        setupRecyclerView();
        setup_text_recyclerview();
        fetchActivities();
        setup_function();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.activities_recyclerview);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            activityAdapter = new ActivityAdapter(activity -> {
                // Redirect to ResourceActivity with ActivityId
                Intent intent = new Intent(ActivityActivity.this, ResourceActivity.class);

                // Pass both Activity ID and Course ID
                intent.putExtra("Activity ID", activity.getActivityId());
                Long courseId = getIntent().getLongExtra("Course ID", -1);
                intent.putExtra("Course ID", courseId);
                startActivity(intent);
            });
            recyclerView.setAdapter(activityAdapter);
        } else {
            Log.e("ActivityActivity", "RecyclerView is null");
        }
    }

    private void fetchActivities() {
        String token = SessionManager.getInstance().getToken();

        if (!token.isEmpty()) {
            String authHeader = "Bearer " + token;

            // Get courseId from the Intent that was passed
            Long courseId = getIntent().getLongExtra("Course ID", -1);

            if (courseId != -1) {
                // Create an instance of Retrofit and fetch activities for the specific course
                ResourceService resourceService = RetrofitClient.getInstance().create(ResourceService.class);
                Call<List<Activity>> call = resourceService.getActivities(authHeader, courseId);

                call.enqueue(new Callback<List<Activity>>() {
                    @Override
                    public void onResponse(Call<List<Activity>> call, Response<List<Activity>> response) {
                        if (response.isSuccessful()) {
                            List<Activity> activities = response.body();
                            if (activities != null && !activities.isEmpty()) {
                                updateRecyclerView(activities);
                            } else {
                                Log.e("FetchActivities", "Response body is empty.");
                                Toast.makeText(ActivityActivity.this, "No activities found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (response.code() == 403) {
                                Toast.makeText(ActivityActivity.this, "Access forbidden. Please check permissions.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("FetchActivities", "Response failed with status code: " + response.code());
                                try {
                                    Log.e("FetchActivities", "Error Body: " + response.errorBody().string());
                                } catch (IOException e) {
                                    Log.e("FetchActivities", "Error parsing error body: " + e.getMessage());
                                }
                                Toast.makeText(ActivityActivity.this, "Failed to fetch activities", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Activity>> call, Throwable t) {
                        Log.e("FetchActivities", "Error fetching activities: " + t.getMessage(), t);
                        Toast.makeText(ActivityActivity.this, "Error fetching activities", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Log.e("FetchActivities", "Invalid course ID: " + courseId);
                Toast.makeText(this, "Invalid course ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("FetchActivities", "User not logged in. Token is empty.");
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateRecyclerView(List<Activity> activities) {
        if (activities != null) {
            activityAdapter.setActivities(activities);
        }
    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setupResourceClickListener(int resourceId, String taskType) {
        LinearLayout resourceButton = findViewById(resourceId);
        resourceButton.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityActivity.this, ResourceActivity.class);
            intent.putExtra("Task", taskType);
            startActivity(intent);
        });
    }

    private void setup_text_recyclerview(){

        TextView course_name = findViewById(R.id.course_name);
        String name = getIntent().getStringExtra("Course Name");
        course_name.setText(name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}