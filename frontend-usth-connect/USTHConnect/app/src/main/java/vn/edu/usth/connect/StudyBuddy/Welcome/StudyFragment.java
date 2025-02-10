package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
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
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddyViewModel;
import vn.edu.usth.connect.R;


public class StudyFragment extends Fragment {

    // Study Locate
    private List<String> study_locate = new ArrayList<>();
    private List<String> selectedStudyLocate = new ArrayList<>();

    private int selectedLocateCount = 0;
    private final int maxLocateSelection = 5;

    private String[] locationStudy;

    private String hintLocation = "Favourite location to study";

    // Study Time
    private String[] timeStudy;

    private String hintTime = "Favourite time to study";

    private String selectedTime = "";

    // Common Button
    private Button next_button;

    private StudyBuddyViewModel studyBuddyViewModel;


    // Get Local and Time to study from Study Buddy
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_study.xml
        View v = inflater.inflate(R.layout.fragment_study, container, false);

        // Initialize ViewModel
        studyBuddyViewModel = new ViewModelProvider(requireActivity()).get(StudyBuddyViewModel.class);

        // Load Study Locate from Assets folder
        loadLocatefromFile();

        // Call ID Button
        next_button = v.findViewById(R.id.next_button);

        timeStudy = getResources().getStringArray(R.array.time_study);

        // Flexbox for Study Locate
        FlexboxLayout flexboxLayout = v.findViewById(R.id.flexbox_layout);

        for (String locate : study_locate){
            TextView tagView = createTagView(locate, flexboxLayout);
            flexboxLayout.addView(tagView);
        }

        // Spinner Function
        SpinnerSelect(v);

        // Button Function
        setup_function(v);

        return v;
    }

    // Choose Locate Time using Spinner
    private void SpinnerSelect(View v){
        Spinner spinner = v.findViewById(R.id.select_when_study);

        List<String> majorList = Arrays.asList(timeStudy);
        SpinnerAdapter adapter = new SpinnerAdapter(requireContext(), majorList, hintTime);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTime = adapterView.getItemAtPosition(i).toString();
                studyBuddyViewModel.getPreferredTimes().setValue(Arrays.asList(selectedTime));  // Store selected time
                checkSelect();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedTime = hintTime;
                studyBuddyViewModel.getPreferredTimes().setValue(new ArrayList<>());  // Store selected time
                checkSelect();
            }
        });
    }

    private TextView createTagView(String locate, FlexboxLayout flexboxLayout) {
        TextView tag = new TextView(getContext());
        tag.setText(locate);
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

        tag.setOnClickListener(view -> toggleSelection(tag, locate));

        return tag;
    }

    // Choosing Locate
    private void toggleSelection(TextView tag, String locate) {
        if (tag.isSelected()) {
            tag.setSelected(false);
            tag.setBackgroundResource(R.drawable.rounded_border);
            selectedLocateCount --;
            selectedStudyLocate.remove(locate);
        } else if (selectedLocateCount < maxLocateSelection) {
            tag.setSelected(true);
            tag.setBackgroundResource(R.drawable.rounded_border_selected);
            selectedLocateCount ++;
            selectedStudyLocate.add(locate);
        }
        studyBuddyViewModel.getPreferredPlaces().setValue(selectedStudyLocate);  // Store selected locations
        checkSelect();
    }

    private void loadLocatefromFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("location.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                study_locate.add(line.trim());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Enable Button
    private void checkSelect(){
        if (!selectedTime.equals(hintTime)) {
            next_button.setEnabled(true);
        } else {
            next_button.setEnabled(false);
        }
    }

    private void setup_function(View v) {
        ImageButton back_button = v.findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        next_button.setEnabled(false);
        next_button.setOnClickListener(view -> {
//            locationStudy = selectedStudyLocate.toArray(new String[0]);
            navigatorToNextFragment();
        });

    }

    // Move to another Fragment
    private void navigatorToNextFragment() {
        Fragment addPictureFragment = new AddPictureFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, addPictureFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}