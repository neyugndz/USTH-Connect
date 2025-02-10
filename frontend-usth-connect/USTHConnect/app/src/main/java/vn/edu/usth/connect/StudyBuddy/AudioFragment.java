package vn.edu.usth.connect.StudyBuddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import org.linphone.core.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.StudyBuddy.Connection;
import vn.edu.usth.connect.Models.StudyBuddy.StudyBuddy;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Network.StudyBuddyService;
import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Audio.ContactAdapter;
import vn.edu.usth.connect.StudyBuddy.Audio.ContactItem;

public class AudioFragment extends Fragment {

    private String username; // Username of SIP Account
    private String password; // Password of SIP Account
    
    private List<ContactItem> items;
    private ContactAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment_audio.xml
        View v = inflater.inflate(R.layout.fragment_audio, container, false);

        // Get username and password
        // Idea: Get Username and Password after RegisterStudyBuddyFragment to login
//        username = requireActivity().getIntent().getStringExtra("sip_username");
//        password = requireActivity().getIntent().getStringExtra("sip_password");

        // Setup RecyclerView
        setup_recyclerview_function(v);

        fetchConnections();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fetch connections when the fragment becomes visible again
        fetchConnections();
    }

    private void getSipCredentials(String buddyId, String buddyName, Long connectionId) {
        String token = SessionManager.getInstance().getToken();
        String authHeader = "Bearer " + token;
        String studentId = SessionManager.getInstance().getStudentId();

        StudyBuddyService service = RetrofitClient.getInstance().create(StudyBuddyService.class);
        service.getSipCredentials(authHeader).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Extract username and password from the response
                    Map<String, String> credentials = response.body();

                    username = credentials.get("sip_username");
                    password = credentials.get("sip_password");

                    // After fetching SIP credentials, update the contact items
                    ContactItem newItem = new ContactItem(buddyName, buddyId, username, password, connectionId, studentId);

                    // Add the contact to the list if not already added
                    boolean alreadyExists = false;
                    for (ContactItem item : items) {
                        if (item.getBuddyId().equals(buddyId)) {
                            alreadyExists = true;
                            break;
                        }
                    }

                    if (!alreadyExists) {
                        items.add(newItem);
                        adapter.setItems(items);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to fetch SIP credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchConnections() {
        String token = SessionManager.getInstance().getToken();
        String studentId = SessionManager.getInstance().getStudentId();
        String authHeader = "Bearer " + token;

        StudyBuddyService service = RetrofitClient.getInstance().create(StudyBuddyService.class);

        // Fetch connections
        service.getConnections(authHeader, studentId).enqueue(new Callback<List<Connection>>() {
            @Override
            public void onResponse(Call<List<Connection>> call, Response<List<Connection>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Connection> connections = response.body();

                    Log.d("FetchConnections", "Response received: " + connections.size() + " connections found.");

                    items = new ArrayList<>();
                    for (Connection connection : connections) {
                        StudyBuddy buddy1 = connection.getStudyBuddy1();
                        StudyBuddy buddy2 = connection.getStudyBuddy2();

                        // Determine which buddy is the other user
                        String buddyId = buddy1.getStudentId().equals(studentId) ? buddy2.getStudentId() : buddy1.getStudentId();
                        String buddyName = buddy1.getStudentId().equals(studentId) ? buddy2.getName() : buddy1.getName();

                        Long connectionId = connection.getId();
                        // Call to get SIP credentials
                        getSipCredentials(buddyId, buddyName, connectionId);
                    }

                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();

                    Log.d("FetchConnections", "Adapter updated with " + items.size() + " items.");
                } else {
                    Log.e("FetchConnections", "Failed to load connections, Response: " + response.message());
                    Toast.makeText(requireContext(), "Failed to load connections", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Connection>> call, Throwable t) {
                Log.e("FetchConnections", "Error fetching connections: " + t.getMessage(), t);
                Toast.makeText(requireContext(), "Error fetching connections", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // SetUp RecyclerView and SearchView
    // Folder: Audio: ContactItem, ContactAdapter, ContactViewHolder
    private void setup_recyclerview_function (View v){
        // RecyclerView point to Audio.OutgoingCall
        RecyclerView recyclerView = v.findViewById(R.id.contact_recyclerview);

//        items = new ArrayList<ContactItem>();
//
//        items.add(new ContactItem("ducduyvx", username, password)); // Test: ducduyvx - 1234567890
//        items.add(new ContactItem("vietanhngx", username, password)); // Test: vietanhngx - 1234567890
//        items.add(new ContactItem("quangminhdo", username, password)); // Test: quangminhdo - 1234567890
//        items.add(new ContactItem("hoanglanvx", username, password)); // Account don't exist
//        items.add(new ContactItem("hoanganhpham", username, password)); // Account don't exist
//        items.add(new ContactItem("dangnguyennguyen", username, password)); // Account don't exist

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ContactAdapter(requireContext(), items);
        recyclerView.setAdapter(adapter);
        
        // SearchView
        SearchView searchView = v.findViewById(R.id.contact_searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    // Filter for SearchView
    private void filterList(String text) {
        List<ContactItem> filteredItems = new ArrayList<>();

        for (ContactItem item : items) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.setFilter(filteredItems);
        }

    }
}