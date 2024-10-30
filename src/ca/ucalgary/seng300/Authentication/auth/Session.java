package src.ca.ucalgary.seng300.Authentication.auth;

import src.ca.ucalgary.seng300.Authentication.models.User;

import java.time.LocalDateTime;

public class Session {

    private String sessionId;
    private User user;
    private LocalDateTime loginTime;
    private boolean isActive;

    public Session(String sessionId, User user) {
        this.sessionId = sessionId;
        this.user = user;
        this.loginTime = LocalDateTime.now();
        this.isActive = true;
    }

    public void invalidate() {
        this.isActive = false;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public String getSessionId() {
        return sessionId;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }
}
