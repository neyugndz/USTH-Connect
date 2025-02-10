package vn.edu.usth.connect.Network;

import android.content.Context;
import android.content.SharedPreferences;

import vn.edu.usth.connect.StudyBuddy.SB_RecyclerView.Rcm_UserItem;

public class SessionManager {

    private static SessionManager instance;
    private String token;
    private String studentId;
    private String studyYear;
    private String major;

    // Private constructor to prevent instantiation
    private SessionManager() {}

    // Get the singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Store token and student ID
    public void saveSession(String token, String studentId) {
        this.token = token;
        this.studentId = studentId;
    }

    public void saveSession(String token, String studentId, String studyYear, String major) {
        this.token = token;
        this.studentId = studentId;
        this.studyYear = studyYear;
        this.major = major;
    }

    // Get the token
    public String getToken() {
        return token;
    }

    // Get the student ID
    public String getStudentId() {
        return studentId;
    }

    // Get the study year
    public String getStudyYear() {
        return studyYear;
    }

    // Get the major
    public String getMajor() {
        return major;
    }


    // Clear session data (for logout)
    public void clearSession() {
        this.token = null;
        this.studentId = null;
        this.studyYear = null;
        this.major = null;
    }

    // Check if user is logged in (token is not null)
    public boolean isLoggedIn() {
        return token != null && !token.isEmpty();
    }

    // Store connected buddy information
    public void storeConnectedBuddy(Rcm_UserItem buddy, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("connected_buddy_id", buddy.getStudentId());
        editor.putString("connected_buddy_name", buddy.getName());
        editor.apply();
    }

    // Get the connected buddy's ID from SharedPreferences
    public String getConnectedBuddyId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return preferences.getString("connected_buddy_id", null); // Return null if no buddy is connected
    }

    // Get the connected buddy's name from SharedPreferences
    public String getConnectedBuddyName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return preferences.getString("connected_buddy_name", null); // Return null if no buddy is connected
    }
}

