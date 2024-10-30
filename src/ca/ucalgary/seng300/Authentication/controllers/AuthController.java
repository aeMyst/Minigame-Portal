package src.ca.ucalgary.seng300.authentication.controllers;

import src.ca.ucalgary.seng300.authentication.interfaces.AuthInterface;
import src.ca.ucalgary.seng300.authentication.services.AuthService;

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

    public boolean logout(String username) {
        return true;
    }
}