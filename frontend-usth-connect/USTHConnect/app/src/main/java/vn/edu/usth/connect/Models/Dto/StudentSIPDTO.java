package vn.edu.usth.connect.Models.Dto;

public class StudentSIPDTO {
    private String sipUsername;
    private String sipPassword;

    public StudentSIPDTO() {
    }

    public StudentSIPDTO(String sipUsername, String sipPassword) {
        this.sipUsername = sipUsername;
        this.sipPassword = sipPassword;
    }

    // Getters and setters
    public String getSipUsername() {
        return sipUsername;
    }

    public void setSipUsername(String sipUsername) {
        this.sipUsername = sipUsername;
    }

    public String getSipPassword() {
        return sipPassword;
    }

    public void setSipPassword(String sipPassword) {
        this.sipPassword = sipPassword;
    }
}
