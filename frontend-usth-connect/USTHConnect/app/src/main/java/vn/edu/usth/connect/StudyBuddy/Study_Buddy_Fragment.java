package vn.edu.usth.connect.StudyBuddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.edu.usth.connect.Models.StudyBuddy.Connection;
import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddy;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudyBuddyService;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.Schedule.Schedule_Activity;
import vn.edu.usth.connect.StudyBuddy.SB_RecyclerView.Rcm_UserAdapter;
import vn.edu.usth.connect.StudyBuddy.SB_RecyclerView.Rcm_UserItem;

public class Study_Buddy_Fragment extends Fragment {
    private final String TAG = "Study_Buddy_Fragment";
    private Rcm_UserAdapter adapter;
    private List<String> removedItems = new ArrayList<>(); // List to track removed user IDs


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_study_buddy_.xml
        View v = inflater.inflate(R.layout.fragment_study__buddy_, container, false);

        // Setup Horizontal Rcm_User
        setup_Rcm_User(v);

        return v;
    }

    // Setup RecyclerView
    // Folder: SB_Recycler: Rcm_UserAdapter, Rcm_UserItem, Rcm_UserViewHolder
    private void setup_Rcm_User(View v) {
        // RecyclerView point to Detail.UserDetailActivity
        RecyclerView recyclerView = v.findViewById(R.id.horizontal_recyclerview);

        List<Rcm_UserItem> items = new ArrayList<Rcm_UserItem>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Get token and Student ID
        String token = SessionManager.getInstance().getToken();
        String studentId = SessionManager.getInstance().getStudentId();
        String authHeader = "Bearer " + token;

        StudyBuddyService service = RetrofitClient.getInstance().create(StudyBuddyService.class);
        service.getRecommendation(authHeader, studentId).enqueue(new Callback<List<StudyBuddy>>() {
            @Override
            public void onResponse(Call<List<StudyBuddy>> call, Response<List<StudyBuddy>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<StudyBuddy> studyBuddies = response.body();

                    // Convert StudyBuddy objects to Rcm_UserItem
                    List<Rcm_UserItem> items = new ArrayList<>();
                    for (StudyBuddy buddy : studyBuddies) {
                        // Skip already removed users
                        if (removedItems.contains(buddy.getStudentId())) {
                            continue;
                        }
                        items.add(new Rcm_UserItem(
                                buddy.getStudentId(),
                                buddy.getName(),
                                buddy.getGender(),
                                buddy.getMajor(),
                                buddy.getLookingFor(),
                                R.drawable.usthconnect // Replace with actual image URL or placeholder
                        ));
                    }

                    // Set up the adapter with data
                    adapter = new Rcm_UserAdapter(requireContext(), items, new Rcm_UserAdapter.OnConnectListener() {
                        @Override
                        public void onConnect(Rcm_UserItem item) {
                            // Confirm connection inside this listener
                            confirmConnection(item, items, adapter);
                        }
                    });

                    // Attach the adapter to the RecyclerView
                    recyclerView.setAdapter(adapter);
                } else {
                    // Handle the case when the response is not successful or the body is null
                    Log.e("StudyBuddyFragment", "Failed to load recommended list or response body is null");
                    Toast.makeText(getContext(), "Error loading recommended list", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<StudyBuddy>> call, Throwable t) {
                Log.e("StudyBuddyFragment", "Error fetching recommended list", t);
                Toast.makeText(getContext(), "Error loading recommended list", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmConnection(Rcm_UserItem item, List<Rcm_UserItem> items, Rcm_UserAdapter adapter) {
        String token = SessionManager.getInstance().getToken();
        String studentId = SessionManager.getInstance().getStudentId();
        String authHeader = "Bearer " + token;

        StudyBuddyService service = RetrofitClient.getInstance().create(StudyBuddyService.class);

        // Create a connection with the selected user
        service.createConnection(authHeader, studentId, item.getStudentId()).enqueue(new Callback<Connection>() {
            @Override
            public void onResponse(Call<Connection> call, Response<Connection> response) {
                if(response.isSuccessful()) {
                    Connection connection = response.body();
                    Toast.makeText(requireContext(), "Connection request sent to " + item.getName(), Toast.LENGTH_SHORT).show();

                    removedItems.add(item.getStudentId());

                    // Remove the connected StudyBuddy from the list and Notify about the change in the list
                    items.remove(item);
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Failed to create connection: " + response.message());
                    Toast.makeText(requireContext(), "Error sending connection request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Connection> call, Throwable t) {
                Log.e(TAG, "Error creating connection", t);
                Toast.makeText(requireContext(), "Error sending connection request", Toast.LENGTH_SHORT).show();
            }
        });
    }
}