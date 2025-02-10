package vn.edu.usth.connect.Home.EditProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import vn.edu.usth.connect.R;

public class ChangePicture_Activity extends AppCompatActivity {

    private EditText url_image;
    private ImageView avatar_profile_image;
    private Button save_button, confirm_button;

    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity_change_picture.xml
        setContentView(R.layout.activity_change_picture);

        // Function to load image from url
        // And Save Button to load Image and Refresh
        // again to show Profile's Image
        load_image();

        // Button Function: Cancel Button and Back Button
        setup_function();

        //
        RegisterResult();
    }

    private void load_image() {
        // LoadImage
        // Image Profile ID
        avatar_profile_image = findViewById(R.id.avatar_profile);

        // Button ID
        save_button = findViewById(R.id.save_button); // save button
        confirm_button = findViewById(R.id.load_image_button); // load image button

        confirm_button.setOnClickListener(view -> {
            choose_image();
        });

        Button save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(view -> {
            Intent intent = new Intent(ChangePicture_Activity.this, Edit_Profile_Activity.class);
            startActivity(intent);
            finish();
        });

    }

    private void setup_function() {
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });

        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    // Function choosing image
    private void choose_image() {
        // Open a mini window (1/2 screen) to select Image and set image
        Intent i = new Intent(MediaStore.ACTION_PICK_IMAGES);

        // Open a new window but don't set image
//        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        resultLauncher.launch(i);
    }

    // Handle Image as uri
    private void RegisterResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    try {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri imageUri = result.getData().getData();
                            avatar_profile_image.setImageURI(imageUri);

                            // Save URI as String for navigation
                            SharedPreferences sharedPreferences = getSharedPreferences("ProfileImage", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Image_URI", imageUri.toString());
                            editor.apply();
                        }
                    } catch (Exception e) {
                        Toast.makeText(ChangePicture_Activity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}