package vn.edu.usth.connect.Workers;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Network.EventService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.Schedule.TimeTable.Event.Event;

public class FetchEventsWorker extends Worker {

    private final String TAG = "FetchEventsWorker";

    public FetchEventsWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("FetchEventsWorker", "Starting to fetch events...");
        String token = SessionManager.getInstance().getToken();
        String studyYear = SessionManager.getInstance().getStudyYear();
        String major = SessionManager.getInstance().getMajor();


        if (token.isEmpty()|| studyYear.isEmpty() || major.isEmpty()) {
            Log.e(TAG, "Token, StudyYear, or Major is missing.");
            return Result.failure();
        }

        String authHeader = "Bearer " + token;

        // Now directly fetch events with the stored studyYear and major
        fetchEvents(authHeader, studyYear, major);
        return Result.success();
    }

    private void fetchEvents(String token, String studyYear, String major) {
        EventService eventService = RetrofitClient.getInstance().create(EventService.class);

        // Call the getEvents API with Authorization header, studyYear, and major
        eventService.getEvents(token, studyYear, major).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Event> events = response.body();
                    Log.d(TAG, "Fetched " + events.size() + " events");

                    Event.eventsList.clear();
                    Event.eventsList.addAll(events);

                    // You can now update your UI with the fetched events, e.g., notify a RecyclerView adapter
                } else {
                    Log.e(TAG, "Failed to fetch events: " + response.message());
                    Toast.makeText(FetchEventsWorker.this.getApplicationContext(), "Unable to load events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e(TAG, "Error fetching events", t);
                Toast.makeText(FetchEventsWorker.this.getApplicationContext(), "Error loading events", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
