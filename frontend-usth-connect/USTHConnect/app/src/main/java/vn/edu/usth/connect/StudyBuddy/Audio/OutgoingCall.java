package vn.edu.usth.connect.StudyBuddy.Audio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.linphone.core.Account;
import org.linphone.core.AccountParams;
import org.linphone.core.Address;
import org.linphone.core.AuthInfo;
import org.linphone.core.Call;
import org.linphone.core.CallParams;
import org.linphone.core.Core;
import org.linphone.core.CoreListenerStub;
import org.linphone.core.Factory;
import org.linphone.core.MediaEncryption;
import org.linphone.core.RegistrationState;
import org.linphone.core.TransportType;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Audio.PushNoti.CallService;
import vn.edu.usth.connect.StudyBuddy.Audio.PushNoti.MyApplication;

public class OutgoingCall extends AppCompatActivity {
    private static final String TAG = "OutgoingCall";
    private Core core;

    private String username; // Username of Sip Account
    private String password; // Password of Sip Account

    private String box_chat;
    private String domain = "sip.linphone.org";

    // Button
    private Button hang_up_button, pause_button, toggle_video_button, toggle_camera_button;

    // Outgoing Call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_outgoing_call.xml
        setContentView(R.layout.activity_outgoing_call);

        // Get username, password, boxChat name
        Intent intent = getIntent();
        username = intent.getStringExtra("sip_username");
        password = intent.getStringExtra("sip_password");
        box_chat = intent.getStringExtra("Contact_Name");

        MyApplication app = (MyApplication) getApplicationContext();
        core = app.getLinphoneCore();

        Log.d(TAG, "Core initialized: " + (core != null));

        // Login Outgoing
        login(username, password);

        // Setup Video Cam
        core.setNativeVideoWindowId(findViewById(R.id.outgoing_remote_video_surface));
        core.setNativePreviewWindowId(findViewById(R.id.outgoing_local_preview_video_surface));
        core.enableVideoCapture(true);
        core.enableVideoDisplay(true);

        core.getVideoActivationPolicy().setAutomaticallyAccept(true);

        // Set Audio Name
        TextView textView = findViewById(R.id.outgoing_remote_address);
        textView.setText(box_chat);

        // Outgoing Call Function
        // Hangup Button
        hang_up_button = findViewById(R.id.outgoing_hang_up);
        hang_up_button.setEnabled(false);;
        hang_up_button.setOnClickListener(view -> {
            Log.d(TAG, "Hang up button clicked");
            hangUp();
        });

        // Pause Button
        pause_button = findViewById(R.id.outgoing_pause);
        pause_button.setEnabled(false);
        pause_button.setOnClickListener(view -> {
            Log.d(TAG, "Pause button clicked");
            pauseOrResume();
        });

        // Toggle Video
        toggle_video_button = findViewById(R.id.outgoing_toggle_video);
        toggle_video_button.setEnabled(false);
        toggle_video_button.setOnClickListener(view -> {
            Log.d(TAG, "Toggle video button clicked");
            toggleVideo();
        });

        // Toggle Camera
        toggle_camera_button = findViewById(R.id.outgoing_toggle_camera);
        toggle_camera_button.setEnabled(false);
        toggle_camera_button.setOnClickListener(view -> {
            Log.d(TAG, "Toggle camera button clicked");
            toggleCamera();
        });
    }

    // Outgoing CoreListener
    private final CoreListenerStub outgoingCallCoreListener = new CoreListenerStub() {
        @Override
        public void onAccountRegistrationStateChanged(Core core, Account account, RegistrationState state, String message) {
            Log.d(TAG, "Account registration state: " + state + ", message: " + message);
            if (state == RegistrationState.Ok) {
                outgoingCall();
                hang_up_button.setEnabled(true);
            }
        }

        // Call to another User
        // When connected
        @Override
        public void onCallStateChanged(Core core, Call call, Call.State state, String message) {
            if (state == Call.State.OutgoingInit) {
            } else if (state == Call.State.IncomingReceived) {
                Intent serviceIntent = new Intent(OutgoingCall.this, CallService.class);
                serviceIntent.putExtra("username", username);
                serviceIntent.putExtra("password", password);
                serviceIntent.putExtra("domain", domain);
                serviceIntent.putExtra("transport_type", TransportType.Tls);
                serviceIntent.putExtra("remote user", call.getRemoteAddress().getUsername());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent);
                } else {
                    startService(serviceIntent);
                }
            }
            else if (state == Call.State.OutgoingProgress) {
            } else if (state == Call.State.OutgoingRinging) {
            } else if (state == Call.State.Connected) {
            } else if (state == Call.State.StreamsRunning) {
                // While Call
                // Enable to pause call
                pause_button.setEnabled(true);
                pause_button.setText("Pause");

                // Enable to toggle video
                toggle_video_button.setVisibility(View.VISIBLE);
                toggle_video_button.setEnabled(true);

                // Enable to toggle camera
                toggle_camera_button.setVisibility(View.VISIBLE);
                toggle_camera_button.setEnabled(core.getVideoDevicesList().length > 2 && call.getCurrentParams().videoEnabled());
            } else if (state == Call.State.Paused) {
                // While Pause Call
                pause_button.setText("Resume");

                toggle_video_button.setEnabled(false);
            } else if (state == Call.State.Released) {
                // Call hangup
                // Clear text
                TextView textView = findViewById(R.id.outgoing_remote_address);
                textView.setText("");

                // Diasble Button
                pause_button.setEnabled(false);
                toggle_video_button.setEnabled(false);
                hang_up_button.setEnabled(false);
                toggle_camera_button.setEnabled(false);

                pause_button.setText("Pause");
                finish();

                // Test: Hangup from incoming call
//                onBackPressed();
//                finish();
            }
        }
    };

    // OutgoingCall
    private void outgoingCall() {
        // Connect to other Account
        String remoteSipUri = String.format("sip:%s@sip.linphone.org", box_chat);
        Address remoteAddress = Factory.instance().createAddress(remoteSipUri);
        if (remoteAddress == null) return;

        CallParams params = core.createCallParams(null);
        if (params == null) return;

        params.setMediaEncryption(MediaEncryption.None);
        core.inviteAddressWithParams(remoteAddress, params);
    }

    // Outgoing Hangup Function
    private void hangUp() {
        if (core.getCallsNb() == 0) return;

        // Get the current call or fallback to the first call in the list
        Call call = core.getCurrentCall() != null ? core.getCurrentCall() : core.getCalls()[0];
        if (call == null) return;

        // Terminate the call
        call.terminate();

        // Finish OutgoingCall
        finish();
    }

    // Outgoing ToggleVideo Function
    private void toggleVideo() {
        if (core.getCallsNb() == 0) return;

        Call call = core.getCurrentCall() != null ? core.getCurrentCall() : core.getCalls()[0];
        if (call == null) return;

        // Check for CAMERA permission
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
            return;
        }

        // Create new call parameters from the call object
        CallParams params = core.createCallParams(call);
        if (params != null) {
            // Toggle video state
            params.enableVideo(!call.getCurrentParams().videoEnabled());
            // Request the call update
            call.update(params);
        }
    }

    // Outgoing toggleCamera Function
    private void toggleCamera() {
        String currentDevice = core.getVideoDevice();

        for (String camera : core.getVideoDevicesList()) {
            if (!camera.equals(currentDevice) && !camera.equals("StaticImage: Static picture")) {
                core.setVideoDevice(camera);
                break;
            }
        }
    }

    // Outgoing Pause/Resume Function
    private void pauseOrResume() {
        if (core.getCallsNb() == 0) return;

        Call call = core.getCurrentCall() != null ? core.getCurrentCall() : core.getCalls()[0];
        if (call == null) return;

        // Pause or resume the call based on its state
        if (call.getState() != Call.State.Paused && call.getState() != Call.State.Pausing) {
            // Pause
            call.pause();
        } else if (call.getState() != Call.State.Resuming) {
            // Resume
            call.resume();
        }
    }

    // Login Button
    private void login(String username, String password) {
        Log.d(TAG, "Logging in with username: " + username);
        AuthInfo authInfo = Factory.instance().createAuthInfo(username, null, password, null, null, domain, null);

        Address identity = Factory.instance().createAddress("sip:" + username + "@" + domain);

        Address address = Factory.instance().createAddress("sip:" + domain);


        if (address != null) {
            address.setTransport(TransportType.Tls);
        }

        // OutgoingCall
        AccountParams outgoing_param = core.createAccountParams();

        outgoing_param.setIdentityAddress(identity);

        outgoing_param.setServerAddress(address);

        outgoing_param.setRegisterEnabled(true);

        Account outgoing_account = core.createAccount(outgoing_param);
        core.addAuthInfo(authInfo);
        core.addAccount(outgoing_account);
        // Outgoing Video
        core.getConfig().setBool("video", "auto_resize_preview_to_keep_ratio", true);
        core.setDefaultAccount(outgoing_account);
        core.addListener(outgoingCallCoreListener);
        core.start();
        Log.d(TAG, "Login complete");

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}