package vn.edu.usth.connect.Resource;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.CourseRecyclerView.CourseActivity;

public class YearActivity extends AppCompatActivity {

    private int subCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_year.xml
        setContentView(R.layout.activity_year);

        // Retrieve the sub_category_id from the Intent
        subCategoryId = getIntent().getIntExtra("subCategoryId", -1);
        Log.d("Year_Activity", "Received sub_category_id: " + subCategoryId);

        // Set text from RecyclerView to Header
        setup_text_recyclerview();

        // Button Function
        setupClickHandlers();
    }

    private void setup_text_recyclerview(){
        TextView courseName = findViewById(R.id.course_name);

        String programName = getIntent().getStringExtra("Program Name");
        // Set the Program Name in the TextView
        if (programName != null && !programName.isEmpty()) {
            courseName.setText(programName);
        } else {
            courseName.setText("Program Name Not Available");
        }
    }

    private void setupProgramme() {
        TextView courseName = findViewById(R.id.course_name);
        String programName = getIntent().getStringExtra("Program Name");
        courseName.setText(programName);
    }

    private void setupClickHandlers() {
        // Back Button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> onBackPressed());

        // Second Year Button
        LinearLayout secondYear = findViewById(R.id.second_year);
        secondYear.setOnClickListener(view -> {
            navigateToCourseYearActivity("Second Year");
        });

        // Third Year Button
        LinearLayout thirdYear = findViewById(R.id.third_year);
        thirdYear.setOnClickListener(view -> {
            navigateToCourseYearActivity("Third Year");
        });
    }

    private void navigateToCourseYearActivity(String year) {
        Intent intent = new Intent(this, CourseActivity.class);
        intent.putExtra("Year", year);
        intent.putExtra("subCategoryId", subCategoryId);
        intent.putExtra("Program Name", getIntent().getStringExtra("Program Name")); // Pass the Program Name
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}