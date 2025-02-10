package vn.edu.usth.connect.StudyBuddy.Welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddyViewModel;
import vn.edu.usth.connect.R;

public class LookingForFragment extends Fragment {

    private Button next_button;

    private Button chit_chat, share_knowledge, study_supporter;

    private Button video_call, phone_call, text_message, face_to_face;

    private String selectedLookingFor = "";
    private String selectedCommunicationStyle = "";

    private StudyBuddyViewModel studyBuddyViewModel;

    // Select LookingFor and Communicate Style
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_looking_for.xml
        View v = inflater.inflate(R.layout.fragment_looking_for, container, false);

        // Call ID Button
        next_button = v.findViewById(R.id.next_button);

        chit_chat = v.findViewById(R.id.chit_chat);
        share_knowledge = v.findViewById(R.id.share_knowledge);
        study_supporter = v.findViewById(R.id.study_supporter);

        video_call = v.findViewById(R.id.video_call);
        phone_call = v.findViewById(R.id.phone_call);
        text_message = v.findViewById(R.id.message_text);
        face_to_face = v.findViewById(R.id.face_to_face);

        // Initialize ViewModel
        studyBuddyViewModel = new ViewModelProvider(requireActivity()).get(StudyBuddyViewModel.class);

        // Button Function
        setup_function(v);

        return v;
    }

    // Select Looking For
    private void LookingForSelect(String looking_for) {
        chit_chat.setBackgroundResource(R.drawable.rounded_border);
        share_knowledge.setBackgroundResource(R.drawable.rounded_border);
        study_supporter.setBackgroundResource(R.drawable.rounded_border);

        switch (looking_for) {
            case "chit chatting":
                chit_chat.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "share knowledge":
                share_knowledge.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "study supporter":
                study_supporter.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
        }

//        selectedLookingFor = looking_for;
        studyBuddyViewModel.getLookingFor().setValue(looking_for);
        checkSelect();
    }

    // Select Communication Style
    private void CommunicationStyleSelect(String communication_style) {
        video_call.setBackgroundResource(R.drawable.rounded_border);
        phone_call.setBackgroundResource(R.drawable.rounded_border);
        text_message.setBackgroundResource(R.drawable.rounded_border);
        face_to_face.setBackgroundResource(R.drawable.rounded_border);

        switch (communication_style) {
            case "video call":
                video_call.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "phone call":
                phone_call.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "text message":
                text_message.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
            case "face-to-face":
                face_to_face.setBackgroundResource(R.drawable.rounded_border_selected);
                break;
        }

//        selectedCommunicationStyle = communication_style;
        studyBuddyViewModel.getCommunicationStyle().setValue(communication_style);
        checkSelect();
    }

    // Enable Button
    private void checkSelect(){
//        if (!selectedLookingFor.isEmpty() && !selectedCommunicationStyle.isEmpty()) {
        if (studyBuddyViewModel.getLookingFor().getValue() != null && !studyBuddyViewModel.getLookingFor().getValue().isEmpty() &&
                studyBuddyViewModel.getCommunicationStyle().getValue() != null && !studyBuddyViewModel.getCommunicationStyle().getValue().isEmpty()) {
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
            navigatorToNextFragment();
        });

        // Select Looking For Button
        chit_chat.setOnClickListener(view ->{
            LookingForSelect("chit chatting");
        });
        share_knowledge.setOnClickListener(view ->{
            LookingForSelect("share knowledge");

        });
        study_supporter.setOnClickListener(view ->{
            LookingForSelect("study supporter");
        });


        // Select Communication Style
        video_call.setOnClickListener(view ->{
            CommunicationStyleSelect("video call");
        });

        phone_call.setOnClickListener(view ->{
            CommunicationStyleSelect("phone call");
        });

        text_message.setOnClickListener(view ->{
            CommunicationStyleSelect("text message");
        });

        face_to_face.setOnClickListener(view ->{
            CommunicationStyleSelect("face-to-face");
        });

    }

    // Move to another Fragment
    private void navigatorToNextFragment() {
        Fragment subjectFragment = new SubjectFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, subjectFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}