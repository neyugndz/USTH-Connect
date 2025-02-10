package vn.edu.usth.connect.StudyBuddy.Audio;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.usth.connect.R;
import vn.edu.usth.connect.StudyBuddy.Audio.PushNoti.MyApplication;

import org.linphone.core.*;
public class IncomingActivity extends AppCompatActivity {

    private Core core;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_incoming);

        Factory factory = Factory.instance();
        factory.setDebugMode(true, "Hello Linphone");
        core = factory.createCore(null, null, this);

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String domain = getIntent().getStringExtra("domain");

        if (username != null && !username.isEmpty() &&
                password != null && !password.isEmpty() &&
                domain != null && !domain.isEmpty()) {
            login(username, password, domain);
        }

        findViewById(R.id.incoming_hang_up).setEnabled(false);
        findViewById(R.id.incoming_answer).setEnabled(false);
        findViewById(R.id.incoming_mute_mic).setEnabled(false);
        findViewById(R.id.incoming_toggle_speaker).setEnabled(false);
        findViewById(R.id.incoming_remote_address).setEnabled(false);

        findViewById(R.id.incoming_hang_up).setOnClickListener(v -> {
            if (core.getCurrentCall() != null) {
                core.getCurrentCall().terminate();
            }
            finish();
        });

        findViewById(R.id.incoming_answer).setOnClickListener(v -> {
            if (core.getCurrentCall() != null) {
                core.getCurrentCall().accept();
            }
        });

        findViewById(R.id.incoming_mute_mic).setOnClickListener(v -> {
            core.enableMic(!core.micEnabled());
        });

        findViewById(R.id.incoming_toggle_speaker).setOnClickListener(v -> toggleSpeaker());
    }

    private final CoreListenerStub coreListener = new CoreListenerStub() {
        @Override
        public void onAudioDeviceChanged(Core core, AudioDevice audioDevice) {
        }

        @Override
        public void onAudioDevicesListUpdated(Core core) {
        }

        @Override
        public void onCallStateChanged(Core core, Call call, Call.State state, String message) {

            if (state != null) {
                switch (state) {
                    case IncomingReceived:
                        findViewById(R.id.incoming_hang_up).setEnabled(true);
                        findViewById(R.id.incoming_answer).setEnabled(true);
                        ((TextView) findViewById(R.id.incoming_remote_address)).setText(call.getRemoteAddress().getUsername());
                        break;
                    case Connected:
                        findViewById(R.id.incoming_mute_mic).setEnabled(true);
                        findViewById(R.id.incoming_mute_mic).setVisibility(View.VISIBLE);
                        findViewById(R.id.incoming_toggle_speaker).setEnabled(true);
                        findViewById(R.id.incoming_toggle_speaker).setVisibility(View.VISIBLE);
                        break;
                    case Released:
                        findViewById(R.id.incoming_hang_up).setEnabled(false);
                        findViewById(R.id.incoming_answer).setEnabled(false);
                        findViewById(R.id.incoming_mute_mic).setEnabled(false);
                        findViewById(R.id.incoming_toggle_speaker).setEnabled(false);
                        ((TextView) findViewById(R.id.incoming_remote_address)).setText("");
                        break;
                }
            }
        }
    };

    private void toggleSpeaker() {
        Call currentCall = core.getCurrentCall();
//        if (currentCall == null) return;

        AudioDevice currentAudioDevice = currentCall.getOutputAudioDevice();
        boolean speakerEnabled = currentAudioDevice != null && currentAudioDevice.getType() == AudioDevice.Type.Speaker;

        for (AudioDevice audioDevice : core.getAudioDevices()) {
            if (speakerEnabled && audioDevice.getType() == AudioDevice.Type.Earpiece) {
                currentCall.setOutputAudioDevice(audioDevice);
                return;
            } else if (!speakerEnabled && audioDevice.getType() == AudioDevice.Type.Speaker) {
                currentCall.setOutputAudioDevice(audioDevice);
                return;
            }
            // If you wanted to route the audio to a Bluetooth headset:
            // else if (audioDevice.getType() == AudioDevice.Type.Bluetooth) {
            //     currentCall.setOutputAudioDevice(audioDevice);
            // }
        }
    }

    private void login(String username, String password, String domain) {
        TransportType transportType = TransportType.Tls;
        AuthInfo authInfo = Factory.instance().createAuthInfo(username, null, password, null, null, domain, null);

        AccountParams params = core.createAccountParams();
        Address identity = Factory.instance().createAddress("sip:" + username + "@" + domain);
        params.setIdentityAddress(identity);

        Address address = Factory.instance().createAddress("sip:" + domain);
        if (address != null) {
            address.setTransport(transportType);
        }
        params.setServerAddress(address);
        params.setRegisterEnabled(true);

        Account account = core.createAccount(params);
        core.addAuthInfo(authInfo);
        core.addAccount(account);

        core.setDefaultAccount(account);
        core.addListener(coreListener);
        core.start();
    }
}