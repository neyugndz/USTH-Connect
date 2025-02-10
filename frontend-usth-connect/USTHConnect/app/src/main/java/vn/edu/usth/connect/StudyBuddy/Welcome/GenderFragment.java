package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddyViewModel;
import vn.edu.usth.connect.R;


public class GenderFragment extends Fragment {

    private Button male, female, other, next_button;

    private String selectGender = "";

    private StudyBuddyViewModel studyBuddyViewModel;

    // Select Gender of Study Buddy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_gender.xml
        View v = inflater.inflate(R.layout.fragment_gender, container, false);

        // Call ID Button
        male = v.findViewById(R.id.male);
        female = v.findViewById(R.id.female);
        other = v.findViewById(R.id.other_gender);
        next_button = v.findViewById(R.id.next_button);

        // Initialize ViewModel
        studyBuddyViewModel = new ViewModelProvider(requireActivity()).get(StudyBuddyViewModel.class);

        // Button Function
        setup_function(v);

        return v;
    }

    private void genderSelect(String gender) {
        male.setBackgroundResource(R.drawable.rounded_border);
        female.setBackgroundResource(R.drawable.rounded_border);
        other.setBackgroundResource(R.drawable.rounded_border);

        switch (gender) {
            case "male":
                male.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "female":
                female.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "prefer not to say":
                other.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
        }

        selectGender = gender;
        next_button.setEnabled(true);
    }

    private void setup_function(View v) {
        ImageButton back_button = v.findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        next_button.setEnabled(false);
        next_button.setOnClickListener(view -> {
            studyBuddyViewModel.getGender().setValue(selectGender); // Save gender in ViewModel
            navigatorToNextFragment();
        });

        // Select Gender Button
        male.setOnClickListener(view -> {
            genderSelect("male");
        });

        female.setOnClickListener(view -> {
            genderSelect("female");
        });

        other.setOnClickListener(view -> {
            genderSelect("prefer not to say");
        });

    }

    // Move to next Fragment
    private void navigatorToNextFragment() {
        Fragment personalityFragment = new PersonalityFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, personalityFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}