package vn.edu.usth.connect.Resource.First_Year;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageButton;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.ActivityRecyclerView.ActivityActivity;
import vn.edu.usth.connect.Resource.CourseRecyclerView.Sty_Adapter;
import vn.edu.usth.connect.Resource.CourseRecyclerView.Sty_Item;

public class Course_Semester_Activity extends AppCompatActivity {

    private List<Sty_Item> items;
    private Sty_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_semester);

        // Set text from RecyclerView to Header
        setup_text_recyclerview();

        // Setup Recyclerview for Course and Searchview
        setup_recyclerview_function();

        // Button Function
        setup_function();

    };

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setup_text_recyclerview(){
        TextView course_name = findViewById(R.id.program_name);
        String name = getIntent().getStringExtra("Semester");
        course_name.setText(name);
    }

    // SetUp RecyclerView and SearchView
    // Folder: RecyclerView: First_year_course_Adapter, First_year_course_Item, First_year_course_ViewHolder
    private void setup_recyclerview_function(){
        // RecyclerView point to Course_Resource_Activity
        RecyclerView recyclerView = findViewById(R.id.course_semester_recyclerview);

        items = new ArrayList<Sty_Item>();

        items.add(new Sty_Item("Introduction to Infomatics", "Dr. Nguyen Le Dung, Dr. Nguyen Duc Dung, Dr. Pham Duc Binh, Dr. Pham Hien", 3L));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Sty_Adapter(this, items);
        recyclerView.setAdapter(adapter);

        // SearchView
        SearchView searchView = findViewById(R.id.course_semester_searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        adapter.setOnItemClickListener(position -> {
            Sty_Item selectedItem = items.get(position);
            navigateToYearCourseResourceActivity(selectedItem.getHeading(), selectedItem.getSubhead(), selectedItem.getId());
        });
    }

    private void navigateToYearCourseResourceActivity(String courseName, String courseProf, Long courseId) {
        Intent intent = new Intent(this, ActivityActivity.class);
        intent.putExtra("Course Name", courseName);
        intent.putExtra("Course Instructor", courseProf);
        intent.putExtra("Course ID", courseId);
        startActivity(intent);
    }
    // Filter for SearchView
    private void filterList(String text) {
        List<Sty_Item> filteredItems = new ArrayList<>();

        for (Sty_Item item : items) {
            if (item.getHeading().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.setFilter(filteredItems);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}