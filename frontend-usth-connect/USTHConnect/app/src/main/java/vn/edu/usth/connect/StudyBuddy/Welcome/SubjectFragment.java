package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddyViewModel;
import vn.edu.usth.connect.R;

public class SubjectFragment extends Fragment {

    private List<String> fav_subjects = new ArrayList<>();
    private List<String> selectedSubjectList = new ArrayList<>();

    private int selectedSubjectCount = 0;
    private final int maxSubjectSelection = 5;

    private String[] selectedSubject;

    private String hintSubject = "Choose your favourite subject";

    private Button next_button;

    private StudyBuddyViewModel studyBuddyViewModel;

    // Get Favorite Subject from Study Buddy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_subject.xml
        View v = inflater.inflate(R.layout.fragment_subject, container, false);

        // Load Subject from Assets folder
        loadSubjectsfromFile();

        // Call ID Button
        next_button = v.findViewById(R.id.next_button);

        // Flexbox for Subject
        FlexboxLayout flexboxLayout = v.findViewById(R.id.flexbox_layout);

        for (String subject : fav_subjects){
            TextView tagView = createTagView(subject, flexboxLayout);
            flexboxLayout.addView(tagView);
        }

        // Initialize ViewModel
        studyBuddyViewModel = new ViewModelProvider(requireActivity()).get(StudyBuddyViewModel.class);

        // Button Function
        setup_function(v);

        return v;
    }
    private TextView createTagView(String subject, FlexboxLayout flexboxLayout) {
        TextView tag = new TextView(getContext());
        tag.setText(subject);
        tag.setTextSize(16f);
        tag.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        tag.setBackgroundResource(R.drawable.rounded_border);
        tag.setPadding(30,30,30,30);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 8, 8, 8);

        tag.setLayoutParams(params);

        tag.setOnClickListener(view -> toggleSelection(tag, subject));

        return tag;
    }

    // Choosing Subject
    private void toggleSelection(TextView tag, String subject) {
        if (tag.isSelected()) {
            tag.setSelected(false);
            tag.setBackgroundResource(R.drawable.rounded_border);
            selectedSubjectCount --;
            studyBuddyViewModel.getFavoriteSubjects().getValue().remove(subject);
//            selectedSubjectList.remove(subject);
        } else if (selectedSubjectCount < maxSubjectSelection) {
            tag.setSelected(true);
            tag.setBackgroundResource(R.drawable.rounded_border_selected);
            selectedSubjectCount ++;
            studyBuddyViewModel.getFavoriteSubjects().getValue().add(subject);

//            selectedSubjectList.add(subject);
        }
        updateButton();
    }

    // Update number of Subject Choosing
    private void updateButton() {
        Button continueButton = getView() != null ? getView().findViewById(R.id.next_button) : null;
        if (continueButton != null) {
            continueButton.setText("Next (" + selectedSubjectCount + "/" + maxSubjectSelection + ")");
            continueButton.setEnabled(selectedSubjectCount > 0);
        }
    }

    private void loadSubjectsfromFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("subjects.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                fav_subjects.add(line.trim());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup_function(View v) {
        ImageButton back_button = v.findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        next_button.setEnabled(false);
        next_button.setOnClickListener(view -> {
//            selectedSubject = selectedSubjectList.toArray(new String[0]);
            navigatorToNextFragment();
        });
    }

    // Move to another Fragment
    private void navigatorToNextFragment() {
        Fragment studyFragment = new StudyFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, studyFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}