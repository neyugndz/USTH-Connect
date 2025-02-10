package vn.edu.usth.connect.StudyBuddy.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddy;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudyBuddyService;
import vn.edu.usth.connect.R;

public class Study_Buddy_Profile_Fragment extends Fragment {
    private RecyclerView recyclerViewInterests;
    private RecyclerView recyclerViewCommunication;
    private RecyclerView recyclerViewLookingFor;
    private RecyclerView recyclerViewFavSubject;
    private RecyclerView recyclerViewStudyPlace;
    private RecyclerView recyclerViewStudyTime;

    private InterestCardAdapter interestCardAdapter;
    private CommunicationStyleAdapter communicationStyleAdapter;
    private LookingForAdapter lookingForAdapter;
    private FavSubjectAdapter favSubjectAdapter;
    private StudyPlaceAdapter studyPlaceAdapter;
    private StudyTimeAdapter studyTimeAdapter;

    private List<CardItem> interestList = new ArrayList<>();
    private List<CardItem> communicationList = new ArrayList<>();
    private List<CardItem> lookingForList = new ArrayList<>();
    private List<CardItem> favSubjectList = new ArrayList<>();
    private List<CardItem> studyPlaceList = new ArrayList<>();
    private List<CardItem> studyTimeList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_study__buddy__profile_, container, false);

        setUpRecyclerViewInterest(v);
        setUpRecyclerViewCommunicationStyle(v);
        setUpRecyclerViewLookingFor(v);
        setUpRecyclerViewFavSubject(v);
        setUpRecyclerViewStudyPlace(v);
        setUpRecyclerViewStudyTime(v);

        fetchStudyBuddyData();
        // Button Function
        setup_function(v);

        return v;
    }

    private void setUpRecyclerViewInterest(View v) {
        recyclerViewInterests = v.findViewById(R.id.recyclerViewInterests);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);

        recyclerViewInterests.setLayoutManager(layoutManager);
        interestCardAdapter = new InterestCardAdapter(interestList);
        recyclerViewInterests.setAdapter(interestCardAdapter);
    }

    private void setUpRecyclerViewCommunicationStyle(View v) {
        recyclerViewCommunication = v.findViewById(R.id.recyclerViewCommunication);
        recyclerViewCommunication.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        communicationStyleAdapter = new CommunicationStyleAdapter(communicationList);
        recyclerViewCommunication.setAdapter(communicationStyleAdapter);
    }

    private void setUpRecyclerViewLookingFor(View v) {
        recyclerViewLookingFor = v.findViewById(R.id.recyclerViewLookingFor);
        recyclerViewLookingFor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        lookingForAdapter = new LookingForAdapter(lookingForList);
        recyclerViewLookingFor.setAdapter(lookingForAdapter);
    }

    private void setUpRecyclerViewFavSubject(View v) {
        recyclerViewFavSubject = v.findViewById(R.id.recyclerViewFavSubject);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);

        recyclerViewFavSubject.setLayoutManager(layoutManager);
        favSubjectAdapter = new FavSubjectAdapter(favSubjectList);
        recyclerViewFavSubject.setAdapter(favSubjectAdapter);
    }

    private void setUpRecyclerViewStudyPlace(View v) {
        recyclerViewStudyPlace = v.findViewById(R.id.recyclerViewStudyPlace);
        recyclerViewStudyPlace.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        studyPlaceAdapter = new StudyPlaceAdapter(studyPlaceList);
        recyclerViewStudyPlace.setAdapter(studyPlaceAdapter);
    }

    private void setUpRecyclerViewStudyTime(View v) {
        recyclerViewStudyTime = v.findViewById(R.id.recyclerViewStudyTime);
        recyclerViewStudyTime.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        studyTimeAdapter = new StudyTimeAdapter(studyTimeList);
        recyclerViewStudyTime.setAdapter(studyTimeAdapter);
    }


    private void fetchStudyBuddyData() {
        String token = SessionManager.getInstance().getToken();
        String studentId = SessionManager.getInstance().getStudentId();

        String authHeader = "Bearer " + token;

        StudyBuddyService studyBuddyService = RetrofitClient.getInstance().create(StudyBuddyService.class);
        studyBuddyService.getStudyBuddy(authHeader, studentId).enqueue(new Callback<StudyBuddy>() {
            @Override
            public void onResponse(Call<StudyBuddy> call, Response<StudyBuddy> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StudyBuddy studyBuddy = response.body();
                    List<String> interests = studyBuddy.getInterests();

                    // Populate RecyclerView with interests
                    interestList.clear();
                    for (String interest : interests) {
                        interestList.add(new CardItem(interest));
                    }
                    interestCardAdapter.notifyDataSetChanged();

                    // Populate Communication Styles
                    communicationList.clear();
                    communicationList.add(new CardItem(studyBuddy.getCommunicationStyle()));
                    communicationStyleAdapter.notifyDataSetChanged();

                    // Populate Looking For
                    lookingForList.clear();
                    lookingForList.add(new CardItem(studyBuddy.getLookingFor()));
                    lookingForAdapter.notifyDataSetChanged();

                    // Populate Favorite Subjects
                    favSubjectList.clear();
                    for (String subject : studyBuddy.getFavoriteSubjects()) {
                        favSubjectList.add(new CardItem(subject));
                    }
                    favSubjectAdapter.notifyDataSetChanged();

                    // Populate Study Places
                    studyPlaceList.clear();
                    for (String place : studyBuddy.getPreferredPlaces()) {
                        studyPlaceList.add(new CardItem(place));
                    }
                    studyPlaceAdapter.notifyDataSetChanged();

                    // Populate Study Times
                    studyTimeList.clear();
                    for (String time : studyBuddy.getPreferredTimes()) {
                        studyTimeList.add(new CardItem(time));
                    }
                    studyTimeAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StudyBuddy> call, Throwable t) {
                Log.e("StudyBuddy", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setup_function(View v){
        Button edit_button = v.findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.connect.StudyBuddy.EditProfile.Edit_SB_Profile_Activity.class);
                startActivity(i);
            }
        });
    }

}