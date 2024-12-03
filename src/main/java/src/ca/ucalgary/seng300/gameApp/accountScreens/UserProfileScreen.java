package src.ca.ucalgary.seng300.gameApp.accountScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.gameApp.IScreen;
import src.ca.ucalgary.seng300.gameApp.ScreenController;

import java.util.HashMap;
import java.util.Map;

/**
 * UserProfileScreen represents the user profile screen in the application
 * Allows the user to view their profile and search for other player profiles
 */
public class UserProfileScreen implements IScreen {
    private Scene scene;
    private VBox userProfileInfoLayout;

    /**
     * Constructs the UserProfileScreen with the given stage, controller, and client
     *
     * @param stage      the primary stage of the application
     * @param controller the screen controller for navigating between different screens
     * @param client     the client for interacting with the network
     */
    public UserProfileScreen(Stage stage, ScreenController controller, Client client) {
        // Obtains the username of the current logged-in user
        String currentUsername = client.getCurrentUsername();

        // Title for the user profile screen
        Label userProfileTitle = new Label("USER PROFILE: " + currentUsername);
        userProfileTitle.setFont(new Font("Arial", 32));

        // Layout for displaying user profile information
        userProfileInfoLayout = new VBox(10);
        userProfileInfoLayout.setAlignment(Pos.CENTER);
        userProfileInfoLayout.setPadding(new Insets(0, 0, 0, 20));

        // Display user profile details (initially the logged-in user's profile)
        String profileInfo = client.getCurrentUserProfile();
        displayProfile(profileInfo);

        // Label and text field for searching another user's profile
        Label searchProfileLabel = new Label("Search Profile:");
        searchProfileLabel.setFont(new Font("Arial", 16));
        TextField searchProfileField = new TextField();
        searchProfileField.setPromptText("Enter profile name");

        // Search button to find and display another user's profile information
        Button searchButton = new Button("Search");
        searchButton.setFont(new Font("Arial", 16));
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF;");
        searchButton.setOnAction(e -> {
            // Search for profile name based on username entered
            String profileName = searchProfileField.getText();
            if (!profileName.isEmpty()) {
                String searchResult = client.searchProfile(profileName);
                // If profile unable to be found, then display error message
                if (searchResult.startsWith("Profile not found")) {
                    showErrorMessage("Error", "Profile not found for username: " + profileName);
                } else {
                    // Display the searched user profile result information
                    displayProfile(searchResult);
                    userProfileTitle.setText("USER PROFILE: " + profileName);
                }
            } else {
                // Display an error message if searched user profile input is empty
                showErrorMessage("Error", "Please enter a valid profile name.");
            }
        });

        // Button to return to the logged-in user's profile
        Button xButton = new Button("X");
        xButton.setFont(new Font("Arial", 16));
        xButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF;");
        xButton.setOnAction(e -> {
            // Reset to the logged-in user's profile information
            String updatedProfileInfo = client.getCurrentUserProfile();
            displayProfile(updatedProfileInfo);
            String currentUsernameReset = client.getCurrentUsername();
            userProfileTitle.setText("USER PROFILE: " + currentUsernameReset);
            searchProfileField.clear();
        });

        // Button to go to the manage profile screen
        Button manageProfileButton = new Button("Manage Profile");
        manageProfileButton.setFont(new Font("Arial", 18));
        manageProfileButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #FFFFFF;");
        manageProfileButton.setOnAction(e -> controller.showManageProfileScreen());

        // Button to go back to the main menu
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 18));
        backButton.setStyle("-fx-background-color: #AAAAAA; -fx-text-fill: #FFFFFF;");
        backButton.setOnAction(e -> controller.showMainMenu());

        // Layout for search user profile components
        HBox searchLayout = new HBox(10, searchProfileLabel, searchProfileField, searchButton, xButton);
        searchLayout.setAlignment(Pos.CENTER);

        // Layout for user profile screen buttons
        HBox buttonsLayout = new HBox(20, manageProfileButton, backButton);
        buttonsLayout.setAlignment(Pos.CENTER);

        // Main layout of the user profile screen
        VBox userProfileLayout = new VBox(20, userProfileTitle, userProfileInfoLayout, searchLayout, buttonsLayout);
        userProfileLayout.setAlignment(Pos.CENTER);
        userProfileLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(userProfileLayout);

        // Initialize the scene for the user profile screen
        scene = new Scene(rootPane, 1280, 900);
    }

    /**
     * Displays the given profile information in the user profile screen
     *
     * @param profileInfo the user profile information to show
     */
    private void displayProfile(String profileInfo) {
        // Clear the existing content from the profile display area
        userProfileInfoLayout.getChildren().clear();

        // Handle condition when the user profile cannot be found
        if (profileInfo.startsWith("Profile not found")) {
            Label notFoundLabel = new Label(profileInfo);
            notFoundLabel.setFont(new Font("Arial", 16));
            userProfileInfoLayout.getChildren().add(notFoundLabel);
            return;
        }

        // Parse and display every user profile information into blocks
        String[] profileLists = profileInfo.split("\n\n");
        // Iterate through each user profile block and split it into lines containing key-value pairs
        for (String block : profileLists) {
            String[] lines = block.split("\n");
            // Store key-value pairs from the profile block
            Map<String, String> profileData = new HashMap<>();
            // Iterate through each line and split the line into key and value based on delimiter
            for (String line : lines) {
                String[] parts = line.split(": ");
                // Check if key-value pairs are valid and add to the profile data map
                if (parts.length == 2) {
                    profileData.put(parts[0], parts[1]);
                }
            }

            // Layout to display user profile information
            VBox profileBox = new VBox(5);
            profileBox.setPadding(new Insets(5));
            profileBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

            // Create all necessary user profile information labels
            Label gametypeLabel = new Label("Gametype: " + profileData.getOrDefault("Gametype", ""));
            Label playerIDLabel = new Label("PlayerID: " + profileData.getOrDefault("PlayerID", ""));
            Label eloLabel = new Label("Elo: " + profileData.getOrDefault("Elo", ""));
            Label winsLabel = new Label("Wins: " + profileData.getOrDefault("Wins", ""));
            Label lossesLabel = new Label("Losses: " + profileData.getOrDefault("Losses", ""));
            Label drawsLabel = new Label("Draws: " + profileData.getOrDefault("Draws", ""));
            Label gamesPlayedLabel = new Label("Games Played: " + profileData.getOrDefault("Games Played", ""));

            // Add all user profile information labels to the layout
            profileBox.getChildren().addAll(
                    gametypeLabel, playerIDLabel, eloLabel, winsLabel, lossesLabel, drawsLabel, gamesPlayedLabel
            );

            // Add the user profile box to the main layout to display
            userProfileInfoLayout.getChildren().add(profileBox);
        }
    }

    /**
     * Displays an error message in a dialog box
     *
     * @param title   the title of the error message dialog box
     * @param message the content of the error message dialog box
     */
    private void showErrorMessage(String title, String message) {
        // Create and display an error message alert with the appropriate title and content
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Returns the scene for the user profile screen
     *
     * @return the scene for the user profile screen
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}
