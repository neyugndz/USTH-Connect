package vn.edu.usth.connect.Resource.SubCategoryRecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Resource.YearActivity;

public class SubCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubCategoryAdapter subCategoryAdapter;
    private List<SubCategoryItem> subCategoryItemList  = new ArrayList<>();
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        // Retrieve the categoryId from the Intent
        categoryId = getIntent().getIntExtra("categoryId", -1);
        Log.d("SubCategoryActivity", "Received categoryId: " + categoryId);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.subcategory_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Initialize the Adapter
        subCategoryAdapter = new SubCategoryAdapter(this, subCategoryItemList);
        recyclerView.setAdapter(subCategoryAdapter);

        setUpClickHandlers();

        // Load the subcategories based on category Id
        loadSubCategories(categoryId);
    }

    // Method to load subcat based on the categoryId
    private void loadSubCategories(int categoryId){

        if (categoryId == 6 || categoryId == 7 || categoryId == 9 || categoryId == 10 || categoryId == 11 || categoryId == 12) {
            String programName = getCategoryName(categoryId);
            redirectToYearActivity(categoryId, programName);
            return;
        }
        subCategoryItemList = new ArrayList<>();
        switch (categoryId) {
            case 2:
                subCategoryItemList.add(new SubCategoryItem(1, "Information and Communication Technology"));
                subCategoryItemList.add(new SubCategoryItem(2, "Cyber Security"));
                subCategoryItemList.add(new SubCategoryItem(3, "Data Science"));
                break;
            case 3:
                subCategoryItemList.add(new SubCategoryItem(4, "PMAB"));
                break;
            case 4:
                subCategoryItemList.add(new SubCategoryItem(5, "AMS"));
                subCategoryItemList.add(new SubCategoryItem(6, "EPE"));
                break;
            case 5:
                subCategoryItemList.add(new SubCategoryItem(7, "MET"));
                subCategoryItemList.add(new SubCategoryItem(8, "EER"));
                subCategoryItemList.add(new SubCategoryItem(9, "ATE"));
                break;
            case 8:
                subCategoryItemList.add(new SubCategoryItem(10, "BME"));
                subCategoryItemList.add(new SubCategoryItem(11, "BMS"));
                break;
            default:
                subCategoryItemList.add(new SubCategoryItem(-1, "Please select a different category."));
                break;
        }

        // Log the data in subCategoryItemList
        for (SubCategoryItem item : subCategoryItemList) {
            Log.d("SubCategoryActivity", "SubCategoryItem: ID=" + item.getCategoryId() + ", Name=" + item.getName());
        }
        // Update the adapter's dataset
        subCategoryAdapter.setSubCategoryItems(subCategoryItemList);
    }

    // Retrieve the subcategory name for specific IDs
    private String getCategoryName(int categoryId) {
        switch (categoryId) {
            case 6:
                return "Applied Environmental Sciences";
            case 7:
                return "Space and Applications";
            case 9:
                return "Food Science and Technology (FST)";
            case 10:
                return "Aeronautical Maintenance and Engineering";
            case 11:
                return "Chemistry";
            case 12:
                return "Applied Mathematics";
            default:
                return "Unknown";
        }
    }

    private void redirectToYearActivity(int categoryId, String programName) {
        Intent intent = new Intent(SubCategoryActivity.this, YearActivity.class);
        intent.putExtra("categoryId", categoryId); // Pass the categoryId for further use in YearActivity
        intent.putExtra("Program Name", programName);
        startActivity(intent);
        finish();
    }

    private void setUpClickHandlers() {
        // Back button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view ->  onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}