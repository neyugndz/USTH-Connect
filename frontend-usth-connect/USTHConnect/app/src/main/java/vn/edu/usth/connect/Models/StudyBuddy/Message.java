package vn.edu.usth.connect.Models.StudyBuddy;

public class Message {
    private Long id;
    private String content;
    private boolean isRead;
    private String createdAt;
    private StudyBuddy sender;
    private StudyBuddy receiver;
    private Connection connection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public StudyBuddy getSender() {
        return sender;
    }

    public void setSender(StudyBuddy sender) {
        this.sender = sender;
    }

    public StudyBuddy getReceiver() {
        return receiver;
    }

    public void setReceiver(StudyBuddy receiver) {
        this.receiver = receiver;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
