package vn.edu.usth.connect.StudyBuddy.EditProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Study_Buddy_Activity;

public class Edit_SB_Profile_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_sb_profile);

        setup_function();

    }

    private void setup_function(){
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });

        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(view -> {
            onBackPressed();
        });

        Button save_button = findViewById(R.id.update_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vn.edu.usth.connect.StudyBuddy.EditProfile.Edit_SB_Profile_Activity.this, Study_Buddy_Activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}