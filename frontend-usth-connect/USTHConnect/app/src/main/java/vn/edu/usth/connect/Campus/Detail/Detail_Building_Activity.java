package vn.edu.usth.connect.Campus.Detail;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationManager;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

import java.util.Arrays;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;
import vn.edu.usth.connect.Campus.Date_adapter;
import vn.edu.usth.connect.Models.Dto.CoordinatesDTO;
import vn.edu.usth.connect.Network.EventService;
import vn.edu.usth.connect.Network.LocationService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.R;

public class Detail_Building_Activity extends AppCompatActivity {
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PointAnnotationManager pointAnnotationManager;

    private final String TAG = "Detail_Building_Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_detail_building.xml
        setContentView(R.layout.activity_detail_building);

        // Fetch the building name passed via Intent
        String buildingName = getIntent().getStringExtra("Building Name");

        // Find the parent LinearLayout where study room details will be added
        LinearLayout studyRoomLayout = findViewById(R.id.study_room_layout);

        // Check if the building name matches the required value
        if ("A21 - University of Science and Technology of Hanoi".equals(buildingName)) {
            // Dynamically add the study room section
            addStudyRoomSection(studyRoomLayout);
        } else {
            // If building name doesn't match, hide the layout
            studyRoomLayout.setVisibility(LinearLayout.GONE);
        }

        // Set text for RecyclerView
        setup_text_recyclerview();

        // Function for backbutton
        setup_function();
    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void addStudyRoomSection(LinearLayout parentLayout) {
        // Create the main Study Room container (LinearLayout)
        LinearLayout studyRoomContainer = new LinearLayout(this);
        studyRoomContainer.setOrientation(LinearLayout.VERTICAL);
        studyRoomContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView studyRoomTitle = new TextView(this);
        studyRoomTitle.setText("Study Room");
        studyRoomTitle.setTextColor(getResources().getColor(R.color.white));
        studyRoomTitle.setTextSize(25);
        studyRoomTitle.setTypeface(Typeface.DEFAULT_BOLD);
        studyRoomTitle.setPadding(10, 10, 10, 10);
        studyRoomContainer.addView(studyRoomTitle);

        TextView auditoriumText = new TextView(this);
        auditoriumText.setText("Auditorium Floor 8 (330)");
        auditoriumText.setTextColor(getResources().getColor(R.color.white));
        auditoriumText.setTextSize(18);
        auditoriumText.setPadding(10, 10, 10, 10);
        studyRoomContainer.addView(auditoriumText);

        TextView room801Text = new TextView(this);
        room801Text.setText("Room 801 - Floor 8 (100)");
        room801Text.setTextColor(getResources().getColor(R.color.white));
        room801Text.setTextSize(18);
        room801Text.setPadding(10, 10, 10, 10);
        studyRoomContainer.addView(room801Text);

        TextView room802Text = new TextView(this);
        room802Text.setText("Room 802 - Floor 8 (60)");
        room802Text.setTextColor(getResources().getColor(R.color.white));
        room802Text.setTextSize(18);
        room802Text.setPadding(10, 10, 10, 10);
        studyRoomContainer.addView(room802Text);

        TextView room602Text = new TextView(this);
        room602Text.setText("Room 602 - Floor 6 - Computer Room (46)");
        room602Text.setTextColor(getResources().getColor(R.color.white));
        room602Text.setTextSize(18);
        room602Text.setPadding(10, 10, 10, 10);
        studyRoomContainer.addView(room602Text);

        TextView room502Text = new TextView(this);
        room502Text.setText("Room 502 - Floor 5 - Computer Room (46)");
        room502Text.setTextColor(getResources().getColor(R.color.white));
        room502Text.setTextSize(18);
        room502Text.setPadding(10, 10, 10, 10);
        studyRoomContainer.addView(room502Text);

        TextView room504Text = new TextView(this);
        room504Text.setText("Room 504 - Floor 5 - Computer Room (46)");
        room504Text.setTextColor(getResources().getColor(R.color.white));
        room504Text.setTextSize(18);
        room504Text.setPadding(10, 10, 10, 10);
        studyRoomContainer.addView(room504Text);

        parentLayout.addView(studyRoomContainer);
    }

    // Set text building's name in Header and building's location
    private void setup_text_recyclerview(){
        TextView building_name = findViewById(R.id.detail_building_name);
        TextView building_locate = findViewById(R.id.detail_building_locate);

        String name = getIntent().getStringExtra("Building Name");
        String locate = getIntent().getStringExtra("Building Locate");

        building_name.setText(name);
        building_locate.setText(locate);

        fetchCoordinatesFromDatabase(name);
    }

    private void fetchCoordinatesFromDatabase(String buildingName) {
        String token = SessionManager.getInstance().getToken();

        if (token.isEmpty()) {
            Log.e(TAG, "Token, StudyYear, or Major is missing.");
            return;
        }

        String authHeader =  "Bearer " + token;

        LocationService locationService = RetrofitClient.getInstance().create(LocationService.class);

        locationService.getCoordinates(authHeader, buildingName).enqueue(new Callback<CoordinatesDTO>() {
            @Override
            public void onResponse(Call<CoordinatesDTO> call, retrofit2.Response<CoordinatesDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Double latitude = response.body().getLatitude();
                    Double longitude = response.body().getLongitude();

                    setStaticMap(latitude, longitude);
                } else {
                    // Handle case when coordinates are not found
                    setStaticMapFallback();
                }
            }

            @Override
            public void onFailure(Call<CoordinatesDTO> call, Throwable t) {
                // Handle failure (e.g., network error)
                setStaticMapFallback();
            }
        });
    }

    private void setStaticMap(Double latitude, Double longitude) {
        ImageView mapImageView = findViewById(R.id.mapImage);
        String mapboxUrl = "https://api.mapbox.com/styles/v1/mapbox/streets-v12/static/" +
                "pin-s+ff0000(" + longitude + "," + latitude + ")/" +  // Red marker
                longitude + "," + latitude + ",17,0/800x600?access_token=" + getString(R.string.mapbox_access_token);

        Glide.with(this)
                .load(mapboxUrl)
                .into(mapImageView);

        // Set click listener for zooming
        mapImageView.setOnClickListener(view -> showZoomableImage(mapboxUrl));
    }

    private void setStaticMapFallback() {
        // Fallback if coordinates are not found
        ImageView mapImageView = findViewById(R.id.mapImage);
        String fallbackMapUrl = "https://api.mapbox.com/styles/v1/mapbox/streets-v12/static/" +
                "pin-s+ff0000(105.8011776,21.048152)/105.8011776,21.048152,17,0/800x600?access_token=" + getString(R.string.mapbox_access_token);

        Glide.with(this)
                .load(fallbackMapUrl)
                .into(mapImageView);

        showZoomableImage(fallbackMapUrl);
    }

    private void showZoomableImage(String imageUrl) {
        // Create a dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_zoomable_image);

        // Set the dialog to full screen
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Find PhotoView in the dialog and load the image
        ImageView photoView = dialog.findViewById(R.id.zoomableImageView);
        Glide.with(this).load(imageUrl).into(photoView);

        // Close the dialog when clicked
        photoView.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
//     BackPress: backto BuildingFragment
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}