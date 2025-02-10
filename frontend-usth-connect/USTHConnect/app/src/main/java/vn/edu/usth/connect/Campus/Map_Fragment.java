package vn.edu.usth.connect.Campus;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mapbox.maps.MapView;

import vn.edu.usth.connect.R;

public class Map_Fragment extends Fragment {

    private static final double CAMPUS_LATITUDE = 21.048152;
    private static final double CAMPUS_LONGITUDE = 105.8011776;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_map.xml
        View v = inflater.inflate(R.layout.fragment_map_, container, false);

        // Initialize the ImageView
        ImageView campusMapImage = v.findViewById(R.id.campus_map_image);

        // Set click listener to open zoomable image
        campusMapImage.setOnClickListener(view -> showZoomableImageDrawable(R.drawable.campus_map));

        // Set the static map with campus coordinates
        setStaticMap(v);

        return v;
    }

    private void setStaticMap(View v) {
        // Static map URL for Mapbox
        String mapboxUrl = "https://api.mapbox.com/styles/v1/mapbox/streets-v12/static/" +
                CAMPUS_LONGITUDE + "," + CAMPUS_LATITUDE + ",16,5/800x600?access_token=" + getString(R.string.mapbox_access_token);

        ImageView mapImageView = v.findViewById(R.id.mapImage);

        // Use Glide to load the static map into ImageView
        Glide.with(this)
                .load(mapboxUrl)
                .into(mapImageView);

        // Set click listener for zooming in
        mapImageView.setOnClickListener(view -> showZoomableImageUrl(mapboxUrl));
    }

    private void showZoomableImageDrawable(int drawableResId) {
        // Create a dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_zoomable_image);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView photoView = dialog.findViewById(R.id.zoomableImageView);

        // Load the drawable resource into the ImageView
        Glide.with(this).load(drawableResId).into(photoView);

        // Dismiss the dialog when the image is tapped
        photoView.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }


    private void showZoomableImageUrl(String imageUrl) {
        // Create a dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_zoomable_image);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView photoView = dialog.findViewById(R.id.zoomableImageView);
        Glide.with(this).load(imageUrl).into(photoView);

        photoView.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
}