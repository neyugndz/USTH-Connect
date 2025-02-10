package vn.edu.usth.connect.Home.EditProfile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.Student;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudentService;
import vn.edu.usth.connect.R;

public class ProfileFragment extends Fragment {

    private ImageView avatar_profile_image;
    private TextView fullName, dob, studentId, major, studyYear, email, phoneNumber;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        update_picture(v); // Update the profile picture

        setup_function(v); // Set up the Edit button

        fetchUserProfile(v);

        return v;
    }

    private void setup_function(View v){
        Button edit_profile = v.findViewById(R.id.edit_profile_button);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.Home.EditProfile.Edit_Profile_Activity.class);
                startActivity(i);
            }
        });
    }

    private void update_picture(View v){
        avatar_profile_image = v.findViewById(R.id.avatar_profile);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ProfileImage", Context.MODE_PRIVATE);
        String url = sharedPreferences.getString("Image_URL", null);
        if (url != null) {
            new UpdateImage(url).start();
        }
    }

    // Function to fetch the student's information from the database
    private void fetchUserProfile(View v) {
        // Get token and studentId from SessionManager
        String token = SessionManager.getInstance().getToken();
        String studentId = SessionManager.getInstance().getStudentId();

        Log.d("ProfileFragment", "Token: " + token);
        Log.d("ProfileFragment", "Student ID: " + studentId);

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
                        displayUserProfile(student, v);
                    } else {
                        Log.d("ProfileFragment", "Failed to load user data: " + response.message());  // Log error message
                        Toast.makeText(getActivity(), "Failed to load user data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Log.d("ProfileFragment", "Token or studentId is empty");
        }
    }

    // Function to match and display the data based on the ID
    private void displayUserProfile(Student student, View v) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Set student data to the UI
        fullName = v.findViewById(R.id.full_name);
        dob = v.findViewById(R.id.student_dob);
        major = v.findViewById(R.id.major);
        studyYear = v.findViewById(R.id.study_year);
        studentId = v.findViewById(R.id.student_id);
        phoneNumber = v.findViewById(R.id.phone_number);
        email = v.findViewById(R.id.email);

        Log.d("ProfileFragment", "Setting user data: " + student.getFullName() + ", " + student.getMajor());

        // ViewModel Setup
        fullName.setText(student.getFullName());

        // Format the Date object to a String
        String formattedDob = dateFormat.format(student.getDob());
        dob.setText(formattedDob);
        major.setText(student.getMajor());
        studyYear.setText(student.getStudyYear());
        studentId.setText(student.getId());
        phoneNumber.setText(student.getPhoneNumber());

        // Email:
        email.setText(shortEmail(student.getEmail()));
    }

    private String shortEmail(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex > 3) { // Ensure there is enough room for truncation
            String prefix = email.substring(0, 3); // Take the first 7 characters
            String suffix = email.substring(email.indexOf("@") - 3); // Add the last 3 characters before '@' and everything after '@'
            return prefix + "..." + suffix;
        }
        return email;
    }

    class    UpdateImage extends Thread {
        private String url;
        private Bitmap bitmap;

        public UpdateImage(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            getActivity().runOnUiThread(() -> {
                if (bitmap != null) {
                    avatar_profile_image.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(getActivity(), "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}