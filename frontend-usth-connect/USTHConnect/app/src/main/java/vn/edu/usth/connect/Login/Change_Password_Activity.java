package vn.edu.usth.connect.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.usth.connect.R;

public class Change_Password_Activity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button change_password_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_change_password.xml
        setContentView(R.layout.activity_change_password);

        editTextEmail = findViewById(R.id.editTextReEmail);
        editTextPassword = findViewById(R.id.editTextRePassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmRePassword);

        change_password_button = findViewById(R.id.change_password);
        change_password_button.setOnClickListener(view -> {
            // Get String form EditText
            String email = editTextEmail.getText().toString();
            String pass = editTextPassword.getText().toString();
            String confirm = editTextConfirmPassword.getText().toString();

            // Check if EditText empty or not
            if(validatechangepass(email, pass, confirm)){
                // Check new password and confirm equal
                if(pass.equals(confirm)){
                    Toast.makeText(this, "Change Password complete!", Toast.LENGTH_SHORT).show();

                    Fragment loginFragment = new LoginFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(android.R.id.content, loginFragment);
                    transaction.commit();
                }
               else{
                    Toast.makeText(this, "Password are not the same!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Please enter email to change password!", Toast.LENGTH_SHORT).show();
            }
        });

        button_function();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    // Check EditText empty
    private boolean validatechangepass(String email, String pass, String confirm) {
        return !email.isEmpty() && !pass.isEmpty() && !confirm.isEmpty();
    }

    private void button_function(){
        // Back to Login, nothing change
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });

        // Move back to Login, chưa safe password(?)
        Button change_password = findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(android.R.id.content, loginFragment);
                transaction.commit();

                // Execute method
                finish();
            }
        });
    }
}