package com.usth_connect.vpn_server_backend_usth.dto;

public class StudentSIPDTO {
    private String sipUsername;
    private String sipPassword;

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

