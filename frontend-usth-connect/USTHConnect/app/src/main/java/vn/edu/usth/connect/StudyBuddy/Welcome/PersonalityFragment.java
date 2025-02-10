package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddyViewModel;
import vn.edu.usth.connect.R;

public class PersonalityFragment extends Fragment {

    private Button next_button;

    private String[] personality;

    private String hint = "Select your personality";
    private String selectedPersonality = "";

    private StudyBuddyViewModel studyBuddyViewModel;

    // Select Personality of Study Buddy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_personality.xml
        View v = inflater.inflate(R.layout.fragment_personality, container, false);

        // Call ID Button
        next_button = v.findViewById(R.id.next_button);

        personality = getResources().getStringArray(R.array.personality);

        // Initialize ViewModel
        studyBuddyViewModel = new ViewModelProvider(requireActivity()).get(StudyBuddyViewModel.class);

        // Button Function
        setup_function(v);

        // Spinner Function
        SpinnerSelect(v);

        return v;
    }

    // Choose Personality using Spinner
    private void SpinnerSelect(View v){
        Spinner spinner = v.findViewById(R.id.select_personality);

        List<String> personalityList = Arrays.asList(personality);
        SpinnerAdapter adapter = new SpinnerAdapter(requireContext(), personalityList, hint);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPersonality = adapterView.getItemAtPosition(i).toString();
                next_button.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedPersonality = hint;
                next_button.setEnabled(false);
            }
        });
    }

    private void setup_function(View v) {
        ImageButton back_button = v.findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        next_button.setEnabled(false);
        next_button.setOnClickListener(view -> {
            studyBuddyViewModel.getPersonality().setValue(selectedPersonality); // Save personality in ViewModel
            navigatorToNextFragment();
        });

    }

    // Move to another Fragment
    private void navigatorToNextFragment() {
        Fragment interestFragment = new InterestFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, interestFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}