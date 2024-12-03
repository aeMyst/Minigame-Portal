package src.ca.ucalgary.seng300.Profile.services;

import org.junit.jupiter.api.Test;
import org.junit.*;


import org.junit.Test;
import src.ca.ucalgary.seng300.Profile.models.User;

import static org.junit.Assert.*;
public class AuthServiceTest {
    @Test
    public void testRegisterValidUser() {
        boolean result = authService.register("valid@example.com", "ValidUser", "Password1!");
        assertTrue("We have successfully registered the user! The user is now in our database", result);
    }

    @Test
    public void testRegisterInvalidEmail() {
        boolean result = authService.register("invalid-email", "ValidUser", "Password1!");
        assertFalse("Registration failed! The email provided is not in the correct format", result);
    }

    @Test
    public void testRegisterInvalidPassword() {
        boolean result = authService.register("valid@example.com", "ValidUser", "short");
        assertFalse("Registration failed! The password does not meet the required criteria", result);
    }

    @Test
    public void testRegisterInvalidUsername() {
        boolean result = authService.register("valid@example.com", "Invalid@User", "Password1!");
        assertFalse("Registration failed! The username contains invalid characters", result);
    }

    @Test
    public void testLoginValidUser() {
        authService.register("valid@example.com", "ValidUser", "Password1!");
        boolean result = authService.login("ValidUser", "Password1!");
        assertTrue("User logged in successfully! Welcome back to the platform", result);
    }

    @Test
    public void testLoginInvalidPassword() {
        authService.register("valid@example.com", "ValidUser", "Password1!");
        boolean result = authService.login("ValidUser", "WrongPassword");
        assertFalse("Login failed! The password provided is incorrect", result);
    }

    @Test
    public void testLoginInvalidUsername() {
        authService.register("valid@example.com", "ValidUser", "Password1!");
        boolean result = authService.login("WrongUser", "Password1!");
        assertFalse("Login failed! The username provided does not exist", result);
    }

    @Test
    public void testLogoutValidUser() {
        authService.register("valid@example.com", "ValidUser", "Password1!");
        authService.login("ValidUser", "Password1!");
        User user = authService.isLoggedIn();
        boolean result = authService.logout(user);
        assertTrue("User logged out successfully! Goodbye for now", result);
    }

    @Test
    public void testLogoutInvalidUser() {
        User invalidUser = new User("InvalidUser", "InvalidPassword", "invalid@example.com");
        boolean result = authService.logout(invalidUser);
        assertFalse("Logout failed! The user is not recognized by the system", result);
    }

    @Test
    public void testIsLoggedIn() {
        authService.register("valid@example.com", "ValidUser", "Password1!");
        authService.login("ValidUser", "Password1!");
        User loggedInUser = authService.isLoggedIn();
        assertNotNull("User login confirmed! The user is now active in the system", loggedInUser);
        assertEquals("The logged-in user matches the expected username! Welcome ValidUser", "ValidUser", loggedInUser.getUsername());
    }

    @Test
    public void testIsLoggedOut() {
        authService.register("valid@example.com", "ValidUser", "Password1!");
        authService.login("ValidUser", "Password1!");
        User user = authService.isLoggedIn();
        authService.logout(user);
        assertNull("We have successfully logged out! No user is currently active in the system", authService.isLoggedIn());
    }
    
}