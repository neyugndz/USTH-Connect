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

public class NameFragment extends Fragment {

    private StudyBuddyViewModel studyBuddyViewModel;

    // Get Student Name to show in Study Buddy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_name.xml
        View v = inflater.inflate(R.layout.fragment_name, container, false);

        // Initialize ViewModel
        studyBuddyViewModel = new ViewModelProvider(requireActivity()).get(StudyBuddyViewModel.class);

        // Button and EditText Function
        setup_function(v);

        return v;
    }

    private void setup_function(View v){
        ImageButton back_button = v.findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        Button next_button = v.findViewById(R.id.next_button);
        next_button.setEnabled(false);
        next_button.setOnClickListener(view -> {
            navigatorToNextFragment();
        });


        EditText editName = v.findViewById(R.id.input_name);
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = editName.getText().toString();
                if(name.isEmpty()){
                    next_button.setEnabled(false);
                }else{
                    next_button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        next_button.setOnClickListener(view -> {
            String name = editName.getText().toString();
            studyBuddyViewModel.getName().setValue(name); // Save Name in ViewModel
//            saveToBackend(name);
            navigatorToNextFragment();
        });

    }

//     Save the name to the backend
//    private void saveToBackend(String name) {
//        // Retrieve the token and studentId from SharedPreferences
//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("ToLogin", Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString("Token", "");
//        String studentId = sharedPreferences.getString("StudentId", "");
//
//        // Check if token or studentId is missing
//        if (token.isEmpty() || studentId.isEmpty()) {
//            Toast.makeText(getContext(), "Authentication error: Token or Student ID is missing.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String authHeader = "Bearer " + token;
//
//        // Prepare the StudyBuddy object
//        StudyBuddy studyBuddy = new StudyBuddy(studentId, name);
//        StudyBuddyService studyBuddyService = RetrofitClient.getInstance().create(StudyBuddyService.class);
//
//        studyBuddyService.saveStudyBuddy(authHeader, studyBuddy).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(getContext(), "Name saved successfully!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), "Failed to save name: " + response.code(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    // Move to next Fragment
    private void navigatorToNextFragment(){
        Fragment genderFragment = new GenderFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, genderFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}