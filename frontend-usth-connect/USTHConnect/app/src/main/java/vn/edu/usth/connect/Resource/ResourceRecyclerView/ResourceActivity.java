package vn.edu.usth.connect.Resource.ResourceRecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.Moodle.Resource;
import vn.edu.usth.connect.Network.ResourceService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.R;

public class ResourceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ResourceAdapter resourceAdapter;
    private List<Resource> resourceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_resource2.xml
        setContentView(R.layout.activity_resource2);

        setupRecyclerView();
        setup_text_recyclerview();
        fetchResources();
        setup_function();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.resources_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resourceAdapter = new ResourceAdapter(resourceList);
        recyclerView.setAdapter(resourceAdapter);
    }

    private void fetchResources() {
        String token = SessionManager.getInstance().getToken();

        if (!token.isEmpty()) {
            String authHeader = "Bearer " + token;

            Long courseId = getIntent().getLongExtra("Course ID", -1);
            Long activityId = getIntent().getLongExtra("Activity ID", -1);

            // Log the values of courseId and activityId
            Log.d("ResourceActivity", "Course ID: " + courseId);
            Log.d("ResourceActivity", "Activity ID: " + activityId);


            if (courseId != -1 && activityId != -1) {
                ResourceService resourceService = RetrofitClient.getInstance().create(ResourceService.class);
                Call<List<Resource>> call = resourceService.getResources(authHeader, courseId, activityId);

                call.enqueue(new Callback<List<Resource>>() {
                    @Override
                    public void onResponse(Call<List<Resource>> call, Response<List<Resource>> response) {
                        if (response.isSuccessful()) {
                            List<Resource> resources = response.body();
                            if (resources != null && !resources.isEmpty()) {
                                resourceAdapter.setResources(resources);
                            } else {
                                Toast.makeText(ResourceActivity.this, "No resources found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ResourceActivity.this, "Failed to fetch resources", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Resource>> call, Throwable t) {
                        Toast.makeText(ResourceActivity.this, "Error fetching resources", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Invalid Course or Activity ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    private void setup_text_recyclerview(){
        TextView course_name = findViewById(R.id.task_name);

        String name = getIntent().getStringExtra("Task");

        course_name.setText(name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}