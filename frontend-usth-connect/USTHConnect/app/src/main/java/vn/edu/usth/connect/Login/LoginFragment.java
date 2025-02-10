package vn.edu.usth.connect.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.MainActivity;
import vn.edu.usth.connect.Models.AuthResponse;
import vn.edu.usth.connect.Models.LoginRequest;
import vn.edu.usth.connect.Models.Student;
import vn.edu.usth.connect.Network.AuthService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudentService;
import vn.edu.usth.connect.R;

public class LoginFragment extends Fragment {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;

    private static final String TAG = "LoginFragment";

    Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment_login.xml
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Set Visible and Gone
        view.findViewById(R.id.loading_layout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.login_layout).setVisibility(View.GONE);

        // Delay 10sec
        // Loading
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.findViewById(R.id.loading_layout).setVisibility(View.GONE);
                view.findViewById(R.id.login_layout).setVisibility(View.VISIBLE);
            }
        }, 5000);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.login_button);


        // Remember Login using SharePreference
        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                authenticateUser(email, password);
            }
        });

        button_function(view);

        return view;
    }

    private void authenticateUser(String username, String password) {
        AuthService authService = RetrofitClient.getInstance().create(AuthService.class);
        LoginRequest loginRequest = new LoginRequest(username, password);

        authService.login(loginRequest).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    Log.d(TAG, "Login successful, token received: " + token);

                    // Save token and update session before navigating
                    SessionManager.getInstance().saveSession(token, username);

                    fetchStudentDetails(token, username);

                    // Redirect to MainActivity
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    try {
                        // Log the server's error response
                        String errorBody = response.errorBody().string();
                        Log.e("LoginError", "Error response: " + errorBody);
                        Toast.makeText(getActivity(), "Invalid credentials: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading error response", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchStudentDetails(String token, String username) {
        StudentService studentService = RetrofitClient.getInstance().create(StudentService.class);
        String authHeader = "Bearer " + token;
        Call<Student> call = studentService.getStudentProfile(authHeader, username);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Student student = response.body();
                    // Save studyYear and major
                    SessionManager.getInstance().saveSession(token, username, student.getStudyYear(), student.getMajor());

                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.e(TAG, "Failed to fetch student details", t);
            }
        });
    }

    private void button_function(View view) {
        // Change Password
        LinearLayout forgot_password = view.findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), Change_Password_Activity.class);
                startActivity(i);
            }
        });
    }

}