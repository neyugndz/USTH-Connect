package vn.edu.usth.connect.Utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Login.LoginFragment;
import vn.edu.usth.connect.Network.AuthService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;

public class LogoutUtils {
    private static LogoutUtils instance;

    private LogoutUtils(){}

    public static LogoutUtils getInstance() {
        if(instance == null) {
            instance = new LogoutUtils();
        }
        return instance;
    }

    public void logoutUser(Context context) {
        // Get the current token from SessionManager
        String token = SessionManager.getInstance().getToken();

        if (token != null && !token.isEmpty()) {
            // Call backend logout endpoint
            AuthService authService = RetrofitClient.getInstance().create(AuthService.class);
            authService.logout("Bearer " + token).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Invalidate session locally after successful backend logout
                        SessionManager.getInstance().clearSession();

                        // Ensure context is an instance of Activity
                        if (context instanceof FragmentActivity) {
                            FragmentActivity activity = (FragmentActivity) context;

                            // Create the LoginFragment and replace the current fragment with it
                            Fragment loginFragment = new vn.edu.usth.connect.Login.LoginFragment();
                            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                            transaction.replace(android.R.id.content, loginFragment);
                            transaction.commit();
                        } else {
                            // If context is not an activity, show an error
                            Toast.makeText(context, "Context is not an activity!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, "Failed to logout from backend", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Error logging out", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // If there's no token in the session, simply clear the session and redirect
            SessionManager.getInstance().clearSession();
            Intent intent = new Intent(context, LoginFragment.class);
            context.startActivity(intent);
        }
    }
}
