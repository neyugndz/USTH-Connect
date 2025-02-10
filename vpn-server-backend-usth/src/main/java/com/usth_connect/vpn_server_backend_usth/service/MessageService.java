package com.usth_connect.vpn_server_backend_usth.service;

import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.Connection;
import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.Message;
import com.usth_connect.vpn_server_backend_usth.entity.studyBuddy.StudyBuddy;
import com.usth_connect.vpn_server_backend_usth.repository.ConnectionRepository;
import com.usth_connect.vpn_server_backend_usth.repository.MessageRepository;
import com.usth_connect.vpn_server_backend_usth.repository.StudyBuddyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class MessageService {
    private final Logger LOGGER = Logger.getLogger(MessageService.class.getName());

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private StudyBuddyRepository studyBuddyRepository;

    // Save the Sent Message
    public Message sendMessage(Long connectionId, String senderId, String receiverId, String content) {

        LOGGER.info("Received sendMessage request: "
                + "connectionId=" + connectionId
                + ", senderId=" + senderId
                + ", receiverId=" + receiverId
                + ", content=" + content);

        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid connection ID"));

        LOGGER.info("Fetched connection: " + connection.getId());

        StudyBuddy sender = studyBuddyRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender ID"));

        LOGGER.info("Fetched sender: " + sender.getStudentId());

        StudyBuddy receiver = studyBuddyRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID"));

        LOGGER.info("Fetched receiver: " + receiver.getStudentId());


        Message message = new Message();
        message.setConnection(connection);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        // Save and log the saved message out for debugging
        Message savedMessage = messageRepository.save(message);
        LOGGER.info("Message saved successfully with ID: " + savedMessage.getId());

        return savedMessage;
    }

    // Fetch the history of message
    public List<Message> getMessages(Long connectionId) {
        return messageRepository.findByConnectionIdOrderByCreatedAtAsc(connectionId);
    }

    // Mark the status of the read message
    public void markMessageAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        message.setRead(true);
        messageRepository.save(message);
    }
}
