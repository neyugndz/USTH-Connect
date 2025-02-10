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

public class InterestFragment extends Fragment {

    private List<String> interests = new ArrayList<>();
    private List<String> selectedInterestList = new ArrayList<>();

    private int selectedCount = 0;
    private final int maxSelection = 5;

    private String[] selectedInterest;

    private Button next_button;

    private StudyBuddyViewModel studyBuddyViewModel;


    // Select Interest of Study Buddy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_interest.xml
        View v = inflater.inflate(R.layout.fragment_interest, container, false);

        // Initialize ViewModel
        studyBuddyViewModel = new ViewModelProvider(requireActivity()).get(StudyBuddyViewModel.class);

        // Load Interest from Assets folder
        loadInterestsfromFile();

        // Call ID Button
        next_button = v.findViewById(R.id.next_button);

        // Flexbox for Interesting
        FlexboxLayout flexboxLayout = v.findViewById(R.id.flexbox_layout);

        for (String interest : interests) {
            TextView tagView = createTagView(interest, flexboxLayout);
            flexboxLayout.addView(tagView);
        }

        // Button Function
        setup_function(v);

        return v;
    }

    private TextView createTagView(String interest, FlexboxLayout flexboxLayout) {
        TextView tag = new TextView(getContext());
        tag.setText(interest);
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

        tag.setOnClickListener(view -> toggleSelection(tag, interest));

        return tag;
    }

    // Choosing Interest
    private void toggleSelection(TextView tag, String interest) {
        if (tag.isSelected()) {
            tag.setSelected(false);
            tag.setBackgroundResource(R.drawable.rounded_border);
            selectedCount --;
            studyBuddyViewModel.getInterests().getValue().remove(interest);
//            selectedInterestList.remove(interest);
        } else if (selectedCount < maxSelection) {
            tag.setSelected(true);
            tag.setBackgroundResource(R.drawable.rounded_border_selected);
            selectedCount ++;
            studyBuddyViewModel.getInterests().getValue().add(interest);
//            selectedInterestList.add(interest);
        }
        updateButton();
    }

    // Update number of Interest Choosing
    private void updateButton() {
        Button continueButton = getView() != null ? getView().findViewById(R.id.next_button) : null;
        if (continueButton != null) {
            continueButton.setText("Next (" + selectedCount + "/" + maxSelection + ")");
            continueButton.setEnabled(selectedCount > 0);
        }
    }

    private void loadInterestsfromFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("interests.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                interests.add(line.trim());
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
//            selectedInterest = selectedInterestList.toArray(new String[0]);
            navigatorToNextFragment();
        });
    }

    // Move to another Fragment
    private void navigatorToNextFragment() {
        Fragment lookingForFragment = new LookingForFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, lookingForFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}