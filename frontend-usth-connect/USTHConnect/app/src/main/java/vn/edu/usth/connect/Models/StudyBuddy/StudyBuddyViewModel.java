package vn.edu.usth.connect.Models.StudyBuddy;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class StudyBuddyViewModel extends ViewModel {
    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final MutableLiveData<String> gender = new MutableLiveData<>();
    private final MutableLiveData<String> personality = new MutableLiveData<>();
    private final MutableLiveData<String> communicationStyle = new MutableLiveData<>();
    private final MutableLiveData<String> lookingFor = new MutableLiveData<>();
    private final MutableLiveData<List<String>> interests = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<String>> favoriteSubjects = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<String>> preferredPlaces = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<String>> preferredTimes = new MutableLiveData<>(new ArrayList<>());

    // Getters and Setters

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getGender() {
        return gender;
    }

    public MutableLiveData<String> getPersonality() {
        return personality;
    }

    public MutableLiveData<String> getCommunicationStyle() {
        return communicationStyle;
    }

    public MutableLiveData<String> getLookingFor() {
        return lookingFor;
    }

    public MutableLiveData<List<String>> getInterests() {
        return interests;
    }

    public MutableLiveData<List<String>> getFavoriteSubjects() {
        return favoriteSubjects;
    }

    public MutableLiveData<List<String>> getPreferredPlaces() {
        return preferredPlaces;
    }

    public MutableLiveData<List<String>> getPreferredTimes() {
        return preferredTimes;
    }
}
