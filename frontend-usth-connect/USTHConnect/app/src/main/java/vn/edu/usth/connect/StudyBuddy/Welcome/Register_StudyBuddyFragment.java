package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Login.LoginFragment;
import vn.edu.usth.connect.Models.Student;
import vn.edu.usth.connect.Models.Dto.StudentSIPDTO;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudentService;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity;

public class Register_StudyBuddyFragment extends Fragment {

    private DrawerLayout mDrawerLayout;

    // Register SIP Account
    // Login to Study Buddy using SIP account
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_register_study_buddy.xml
        View v = inflater.inflate(R.layout.fragment_register__study_buddy, container, false);

        // Call ID EditText and Button
        EditText username = v.findViewById(R.id.editTextUserName);
        EditText user_password = v.findViewById(R.id.editTextPassword);

        Button login_button = v.findViewById(R.id.login_button);
        login_button.setOnClickListener(view -> {
            String email = username.getText().toString();
            String password = user_password.getText().toString();

            Log.d("RegisterStudyBuddy", "Username: " + email + ", Password: " + password);


            if (!email.isEmpty() && !password.isEmpty()){
                updateSipProfile(email, password);
            }
        });

        mDrawerLayout = v.findViewById(R.id.register_sip_account_fragment);

        ImageButton mImageView = v.findViewById(R.id.menu_button);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // Register SIP Account
        register_sip_browser(v);

        // Side-menu function
        navigator_drawer_function(v);

        return v;
    }

    private void navigator_drawer_function(View v) {
        LinearLayout to_home_activity = v.findViewById(R.id.to_home_page);
        to_home_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.MainActivity.class);
                startActivity(i);
            }
        });

        LinearLayout to_schedule_activity = v.findViewById(R.id.to_schedule_page);
        to_schedule_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.Schedule.Schedule_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_campus_activity = v.findViewById(R.id.to_map_page);
        to_campus_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.Campus.Campus_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_resource_activity = v.findViewById(R.id.to_resource_page);
        to_resource_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.Resource.CategoryRecyclerView.CategoryActivity.class);
                startActivity(i);
            }
        });

        LinearLayout to_study_activity = v.findViewById(R.id.to_study_page);
        to_study_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout logout = v.findViewById(R.id.to_log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment loginFragment = new LoginFragment();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(android.R.id.content, loginFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    private void register_sip_browser(View v){
        TextView register_link = v.findViewById(R.id.register_sip_account);
        register_link.setOnClickListener(view -> {
            String url = "https://subscribe.linphone.org/register/email";
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
            catch (Exception e){
                Toast.makeText(requireContext(), "Can't open browser, check connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSipProfile(String username, String password) {
        String token = SessionManager.getInstance().getToken();
        String studentId = SessionManager.getInstance().getStudentId();

        Log.d("RegisterStudyBuddy", "Token: " + token);
        Log.d("RegisterStudyBuddy", "Student ID: " + studentId);

        if (token.isEmpty() || studentId.isEmpty()) {
            Log.e("RegisterStudyBuddy", "Token or Student ID is missing.");
            return;
        }

        String authHeader = "Bearer " + token;
        // Create Retrofit instance and call the update SIP profile endpoint
        StudentSIPDTO studentSIPDTO = new StudentSIPDTO(username, password);
        StudentService studentService = RetrofitClient.getInstance().create(StudentService.class);

        // Make a PATCH request to update the SIP credentials
        studentService.updateSipProfile(authHeader, studentId, studentSIPDTO)
                .enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response
                            Toast.makeText(getActivity(), "SIP profile registered successfully.", Toast.LENGTH_SHORT).show();
                            // Proceed to the next activity
                            Intent intent = new Intent(getActivity(), Study_Buddy_Activity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            // Handle failure response
                            Toast.makeText(getActivity(), "Failed to update SIP profile.", Toast.LENGTH_SHORT).show();
                            Log.e("RegisterStudyBuddy", "Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        // Handle network failure
                        Toast.makeText(getActivity(), "Network error, please try again.", Toast.LENGTH_SHORT).show();
                        Log.e("RegisterStudyBuddy", "Error: " + t.getMessage());
                    }
                });
    }
}