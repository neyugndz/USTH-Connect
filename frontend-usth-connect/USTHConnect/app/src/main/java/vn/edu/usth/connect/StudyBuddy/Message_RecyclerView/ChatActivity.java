package vn.edu.usth.connect.StudyBuddy.Message_RecyclerView;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.linphone.core.Account;
import org.linphone.core.AccountParams;
import org.linphone.core.Address;
import org.linphone.core.AuthInfo;
import org.linphone.core.ChatMessage;
import org.linphone.core.ChatMessageListenerStub;
import org.linphone.core.ChatRoom;
import org.linphone.core.ChatRoomBackend;
import org.linphone.core.ChatRoomCapabilities;
import org.linphone.core.ChatRoomParams;
import org.linphone.core.Content;
import org.linphone.core.Core;
import org.linphone.core.CoreListenerStub;
import org.linphone.core.Factory;
import org.linphone.core.RegistrationState;
import org.linphone.core.TransportType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.connect.Models.StudyBuddy.Message;
import vn.edu.usth.connect.Network.MessageService;
import vn.edu.usth.connect.Network.RetrofitClient;
import vn.edu.usth.connect.Network.SessionManager;
import vn.edu.usth.connect.R;

public class ChatActivity extends AppCompatActivity {

    private Core core;
    private ChatRoom chatRoom;

    private String username; // Username of Sip Account
    private String password; // Password of Sip Account
    private String box_chat;
    private Long connectionId;
    private String senderId;
    private String receiverId;
    private String authHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_chat.xml
        setContentView(R.layout.activity_chat);

        authHeader = "Bearer " + SessionManager.getInstance().getToken();

        // Get username, password, boxChat name
        username = getIntent().getStringExtra("sip_username");
        password = getIntent().getStringExtra("sip_password");
        box_chat = getIntent().getStringExtra("BoxChat_Name");
        connectionId = getIntent().getLongExtra("connectionId", -1);
        senderId = getIntent().getStringExtra("senderId");
        receiverId = getIntent().getStringExtra("receiverId");

        // Debugging logs
        Log.d("ChatActivity", "SIP Username: " + username);
        Log.d("ChatActivity", "SIP Password: " + password);
        Log.d("ChatActivity", "BoxChat Name: " + box_chat);
        Log.d("ChatActivity", "Connection ID: " + connectionId);
        Log.d("ChatActivity", "Sender ID: " + senderId);
        Log.d("ChatActivity", "Receiver ID: " + receiverId);


        // Set name for textView
        TextView box_chat_name = findViewById(R.id.boxchat_name);
        box_chat_name.setText(box_chat);

        // Create Function & Core:
        // BoxChat Core
        // BoxChat
        Factory factory = Factory.instance();
        core = factory.createCore(null, null, this);

        Log.d("ChatActivity", "Core initialized: " + (core != null));

        // BoxChat Function
        boxchat_function();

        // Login SIP Account for User
        login(username, password);

        // Button Function
        setup_function();

        if (connectionId != -1) {
            fetchMessages();
        } else {
            Log.e("ChatActivity", "Invalid connectionId. Unable to fetch messages.");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        // Fetch connections when the fragment becomes visible again
        fetchMessages();
    }

    // Box chat CoreListener
    // When Account fully login and Received Message
    private final CoreListenerStub BoxChatCoreListener = new CoreListenerStub() {
        // Check when state when 2 account connected
        // when connected => Create a ChatRoom
        // and Enable Button
        @Override
        public void onAccountRegistrationStateChanged(Core core, Account account, RegistrationState state, String message) {
            Log.d("ChatActivity", "Registration state: " + state); // Log all states

            if (state == RegistrationState.Progress) {
                Log.d("ChatActivity", "Registration in progress: " + message);
            } else if (state == RegistrationState.Ok) {
                Log.d("ChatActivity", "Registration successful for user: " + username);

                if (chatRoom == null) {
                    Log.d("ChatActivity", "ChatRoom is null, creating one...");
                    createBasicChatRoom();
                }

                // Enable send Message button when login complete
                Button send_message = findViewById(R.id.send_message);
                send_message.setEnabled(true);
                Log.d("ChatActivity", "Send Message button enabled: " + send_message.isEnabled());

                // Enable send Image button when login complete
                ImageButton send_image = findViewById(R.id.send_image);
                send_image.setEnabled(true);
                Log.d("ChatActivity", "Send Image button enabled: " + send_image.isEnabled());
            } else if (state == RegistrationState.Failed) {
                Log.d("ChatActivity", "Registration failed: " + message);
            }
        }


        @Override
        public void onMessageReceived(Core core, ChatRoom chatRoom, ChatMessage message) {

            // If the chat room wasn't existing, it is automatically created by the library
            // If we already sent a chat message, the chatRoom variable will be the same as the one we already have
            if (ChatActivity.this.chatRoom == null) {
                if (chatRoom.hasCapability(ChatRoomCapabilities.Basic.toInt())) {
                    ChatActivity.this.chatRoom = chatRoom;
                }
            }
            // Notify the sender the message has been read
            chatRoom.markAsRead();
            addMessageToHistory(message);
        }
    };

    // Message CoreListener
    private final ChatMessageListenerStub chatMessageListener = new ChatMessageListenerStub() {
        @Override
        public void onMsgStateChanged(ChatMessage message, ChatMessage.State state) {
            View messageView = (View) message.getUserData();
            if (messageView != null) {
                // Check state of message
                // todo: set a small text below instead of color (?)
                switch (state) {
                    case InProgress:
                        messageView.setBackgroundColor(getColor(R.color.yellow));
                        break;
                    case Delivered:
                        // The proxy server has acknowledged the message with a 200 OK
                        messageView.setBackgroundColor(getColor(R.color.orange));
                        break;
                    case DeliveredToUser:
                        // User as received it
                        messageView.setBackgroundColor(getColor(R.color.blue));
                        break;
                    case Displayed:
                        // User as read it (client called chatRoom.markAsRead()
                        messageView.setBackgroundColor(getColor(R.color.green));
                        break;
                    case NotDelivered:
                        // User might be invalid or not registered
                        messageView.setBackgroundColor(getColor(R.color.red));
                        break;
                    case FileTransferDone:
                        // We finished uploading/downloading the file
                        if (!message.isOutgoing()) {
                            LinearLayout messages = findViewById(R.id.messages);
                            messages.removeView(messageView);
                            addMessageToHistory(message);
                        }
                        break;
                }
            }
        }
    };

    // Endpoint to get the history of the connection between users
    private void fetchMessages() {
        String authHeader = "Bearer " + SessionManager.getInstance().getToken();
        MessageService messageService = RetrofitClient.getInstance().create(MessageService.class);

        messageService.getMessages(authHeader, connectionId).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Message> messages = response.body();
                        for (Message message : messages) {
                            addMessagesToHistory(message);
                        }
                    } else {
                        Log.e("ChatActivity", "Response body is null (No messages found)");
                    }
                } else if (response.code() == 204) {
                    Log.e("ChatActivity", "No content available (HTTP 204)");
                } else {
                    Log.e("ChatActivity", "Failed to fetch messages. Code: " + response.code());

                    // Handle error response safely
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("ChatActivity", "Error body: " + errorBody);
                        } catch (IOException e) {
                            Log.e("ChatActivity", "Error reading error body", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("ChatActivity", "Failed to fetch messages: " + t.getMessage());
            }
        });
    }



    private void addMessageToHistory(ChatMessage chatMessage) {
        // To display a chat message, iterate over it's contents list
        for (Content content : chatMessage.getContents()) {
            if (content.isText()) {
                // Content is of type plain/text
                addTextMessageToHistory(chatMessage, content);
            } else if (content.isFile()) {
                // Content represents a file we received and downloaded or a file we sent
                // Here we assume it's an image
                if (content.getName().endsWith(".jpeg") || content.getName().endsWith(".jpg") || content.getName().endsWith(".png")) {
                    addImageMessageToHistory(chatMessage, content);
                }
            } else if (content.isFileTransfer()) {
                // Content represents a received file we didn't download yet
                addDownloadButtonToHistory(chatMessage, content);
            }
        }
    }

    // todo: Design a more beautiful layout
    private void addMessagesToHistory(Message message) {
        LinearLayout messagesLayout = findViewById(R.id.messages);

        // Handle text messages only
        TextView messageView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Align based on sender
        if (message.getSender().getStudentId().equals(senderId)) {
            layoutParams.gravity = Gravity.END; // Outgoing messages on the right
            messageView.setBackgroundColor(getColor(R.color.green));
        } else {
            layoutParams.gravity = Gravity.START; // Incoming messages on the left
            messageView.setBackgroundColor(getColor(R.color.purple_200));
        }

        messageView.setLayoutParams(layoutParams);
        messageView.setText(message.getContent());
        messageView.setPadding(16, 8, 16, 8);

        messagesLayout.addView(messageView);

        // Scroll to the bottom
        ScrollView scrollView = findViewById(R.id.scroll);
        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }

    // Add Message into Message Layout
    private void addTextMessageToHistory(ChatMessage chatMessage, Content content) {
        // Create TextView for Message
        TextView messageView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // If message outgoing from user: display right side, else left side
        layoutParams.gravity = chatMessage.isOutgoing() ? Gravity.RIGHT : Gravity.LEFT;

        // Set layout and set Text
        messageView.setLayoutParams(layoutParams);

        // Content is of type plain/text, we can get the text in the content
        messageView.setText(content.getUtf8Text());

        // Change background color of message
        if (chatMessage.isOutgoing()) {
            // Actually don't know where it display
            messageView.setBackgroundColor(getColor(R.color.white));
        } else {
            // Receiver will see purple
            messageView.setBackgroundColor(getColor(R.color.purple_200));
        }

        // Set user data
        chatMessage.setUserData(messageView);

        // Add message in Message Layout
        LinearLayout messages = findViewById(R.id.messages);
        messages.addView(messageView);

        // Set scroll down for message layout
        ScrollView scrollView = findViewById(R.id.scroll);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);

    }

    // add Image into Message Layout
    private void addImageMessageToHistory(ChatMessage chatMessage, Content content) {
        // Create ImageView hold image
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = chatMessage.isOutgoing() ? Gravity.RIGHT : Gravity.LEFT;
        imageView.setLayoutParams(layoutParams);

        // As we downloaded the file to the content.filePath, we can now use it to display the image
        imageView.setImageBitmap(BitmapFactory.decodeFile(content.getFilePath()));
        chatMessage.setUserData(imageView);

        // Add image in Message Layout
        LinearLayout messages = findViewById(R.id.messages);
        messages.addView(imageView);

        // Set scroll down for message layout
        ScrollView scrollView = findViewById(R.id.scroll);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    // Download or Show image from other send to
    // todo: make it auto instead of click button on it
    private void addDownloadButtonToHistory(ChatMessage chatMessage, Content content) {
        Button buttonView = new Button(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = chatMessage.isOutgoing() ? Gravity.RIGHT : Gravity.LEFT;
        buttonView.setLayoutParams(layoutParams);
        buttonView.setText("Download Image & Load Image");

        chatMessage.setUserData(buttonView);
        buttonView.setOnClickListener(v -> {
            buttonView.setEnabled(false);

            // Set the path to where we want the file to be stored
            // Here we will use the app private storage
            content.setFilePath(getFilesDir().getAbsolutePath() + "/" + content.getName());

            // Start the download
            chatMessage.downloadContent(content);

            // Download progress will be notified through onMsgStateChanged callback,
            // so we need to add a listener if not done yet
            if (!chatMessage.isOutgoing()) {
                chatMessage.addListener(chatMessageListener);
            }
        });

        LinearLayout messages = findViewById(R.id.messages);
        messages.addView(buttonView);

        // Set scroll down for message layout
        ScrollView scrollView = findViewById(R.id.scroll);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    // Create ChatRoom without saving last chatting data
    // todo: upgrade to advance ChatRoom
    private void createBasicChatRoom() {
        // It doesn't include advanced features such as end-to-end encryption or groups
        // But it is interoperable with any SIP service as it's relying on SIP SIMPLE messages
        // If you try to enable a feature not supported by the basic backend, isValid() will return
        ChatRoomParams params = core.createDefaultChatRoomParams();
        params.setBackend(ChatRoomBackend.Basic);
        params.enableEncryption(false);
        params.enableGroup(false);

        if (params.isValid()) {
            // We also need the SIP address of the person we will chat with
            // Connect to the contact automatically
            String remoteSipUri = String.format("sip:%s@sip.linphone.org", box_chat);
            Address remoteAddress = Factory.instance().createAddress(remoteSipUri);

            if (remoteAddress != null) {
                // And finally we will need our local SIP address
                Address localAddress = core.getDefaultAccount().getParams().getIdentityAddress();
                ChatRoom room = core.createChatRoom(params, localAddress, new Address[]{remoteAddress});
                if (room != null) {
                    chatRoom = room;
                }
            }
        }
    }

    // Send Message
    private void sendMessage() {
        // Get message
        EditText editText = findViewById(R.id.message);
        String content = editText.getText().toString();

        // Create ChatRoom
        // We need to create a ChatMessage object using the ChatRoom
        ChatMessage chatMessage = chatRoom.createMessageFromUtf8(content);

        // Then we can send it, progress will be notified using the onMsgStateChanged callback
//        chatMessage.addListener(chatMessageListener);
        addMessageToHistory(chatMessage);

        MessageService messageService = RetrofitClient.getInstance().create(MessageService.class);
        messageService.sendMessage(authHeader, connectionId, senderId, receiverId, content).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    // If the API successfully processes the message, you can update the UI to reflect that the message was sent
                    Message apiMessage = response.body();
                    updateMessageStatus(chatMessage, "Sent");
                } else {
                    // Log the HTTP status code for further investigation
                    Log.e("ChatActivity", "Error code: " + response.code());

                    // Attempt to read the error body as a string
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("ChatActivity", "Error body: " + errorBody);
                    } catch (IOException e) {
                        Log.e("ChatActivity", "Failed to read error body", e);
                    }

                    // Optionally, mark the message as failed in the UI
                    updateMessageStatus(chatMessage, "Failed");
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("ChatActivity", "Failed to send message: " + t.getMessage());
                // Optionally, mark the message as failed in the UI
                updateMessageStatus(chatMessage, "Failed");
            }
        });

        // Send the message
//        chatMessage.send();
        // Clear Text after send
        editText.getText().clear();
    }

    private void updateMessageStatus(ChatMessage chatMessage, String status) {
        // You can create a status indicator for the message (e.g., showing a small icon, changing the color, etc.)
        View messageView = (View) chatMessage.getUserData();

        if (messageView != null) {
            // Based on the status, update the message view (e.g., changing the color or adding a small icon)
            switch (status) {
                case "Sent":
                    messageView.setBackgroundColor(getColor(R.color.green)); // Example for sent
                    break;
                case "Failed":
                    messageView.setBackgroundColor(getColor(R.color.red)); // Example for failed
                    break;
                default:
                    messageView.setBackgroundColor(getColor(R.color.bottom_navigator_before)); // Default color for unprocessed messages
            }
        }
    }

    // Send Image
    private void sendImage() {
        // We need to create a Content for our file transfer
        Content content = Factory.instance().createContent();

        // Every content needs a content type & subtype
        content.setType("image");
        content.setSubtype("png");

        // The simplest way to upload a file is to provide it's path
        // First copy the sample file from assets to the app directory if not done yet
        String filePath = getFilesDir().getAbsolutePath() + "/phone.png";
        copy("phone.png", filePath);
        content.setFilePath(filePath);

        // We need to create a ChatMessage object using the ChatRoom
        ChatMessage chatMessage = chatRoom.createFileTransferMessage(content);

        // Then we can send it, progress will be notified using the onMsgStateChanged callback
        chatMessage.addListener(chatMessageListener);

        // Ensure a file sharing server URL is correctly set in the Core
        core.setFileTransferServer("https://www.linphone.org:444/lft.php");

        addMessageToHistory(chatMessage);

        // Send the message
        chatMessage.send();
    }

    private void copy(String from, String to) {
        File outFile = new File(to);
        if (outFile.exists()) {
            return;
        }

        try (InputStream inFile = getAssets().open(from);
             FileOutputStream outStream = new FileOutputStream(outFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inFile.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            Log.i("MessageActivity", "Failed to copy file");
        }
    }

    // Login Function
    private void login(String username, String password) {
        String domain = "sip.linphone.org";
        AuthInfo authInfo = Factory.instance().createAuthInfo(username, null, password, null, null, domain, null);

        Address identity = Factory.instance().createAddress("sip:" + username + "@" + domain);

        Address address = Factory.instance().createAddress("sip:" + domain);

        //
        AccountParams box_chat_param = core.createAccountParams();
        box_chat_param.setIdentityAddress(identity);

        if (address != null) {
            address.setTransport(TransportType.Tls);
        }

        box_chat_param.setServerAddress(address);
        box_chat_param.setRegisterEnabled(true);

        Account box_chat_account = core.createAccount(box_chat_param);
        core.addAuthInfo(authInfo);
        core.addAccount(box_chat_account);

        core.setDefaultAccount(box_chat_account);
        core.addListener(BoxChatCoreListener);
        core.start();

    }

    private void boxchat_function(){
        // BoxChat Function
        findViewById(R.id.send_message).setOnClickListener(v -> sendMessage());
        findViewById(R.id.send_message).setEnabled(false);

        findViewById(R.id.send_image).setOnClickListener(v -> sendImage());
        findViewById(R.id.send_image).setEnabled(false);
    }

    private void setup_function() {
        ImageButton back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}