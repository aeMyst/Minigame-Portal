package src.ca.ucalgary.seng300.Profile;

import src.ca.ucalgary.seng300.Profile.controllers.AuthController;
import src.ca.ucalgary.seng300.Profile.models.User;
import src.ca.ucalgary.seng300.Profile.services.AuthService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        AuthController authController = new AuthController(authService);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Authentication System!");

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Check Logged-In User");
            System.out.println("4. Logout");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    boolean registered = authController.register(email, username, password);
                    if (registered) {
                        System.out.println("User registered successfully!");
                    } else {
                        System.out.println("Failed to register. Check your inputs.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    boolean loggedIn = authController.login(username, password);
                    if (loggedIn) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                }
                case 3 -> {
                    User loggedInUser = authController.isLoggedIn();
                    if (loggedInUser != null) {
                        System.out.println("Currently logged in as: " + loggedInUser.getUsername());
                    } else {
                        System.out.println("No user is currently logged in.");
                    }
                }
                case 4 -> {
                    User loggedInUser = authController.isLoggedIn();
                    if (loggedInUser != null && authController.logout(loggedInUser)) {
                        System.out.println("Logout successful.");
                    } else {
                        System.out.println("No user to log out.");
                    }
                }
                case 5 -> {
                    running = false;
                    System.out.println("bam");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
