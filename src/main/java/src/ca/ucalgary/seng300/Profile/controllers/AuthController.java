package src.ca.ucalgary.seng300.Profile.controllers;

import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public boolean register(String email,String username, String password) {
        return authService.register(email,username, password);
    }

    public boolean login(String username, String password) {
        return authService.login(username, password);
    }

    public boolean logout(User currentUser) {
        return authService.logout(currentUser);
    }

    public User isLoggedIn(){
        return authService.isLoggedIn();
    }
}