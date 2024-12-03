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
    private String currentUser;
    /**
     * Constructs the UserProfileScreen with the given stage, controller, and client
     *
     * @param stage The primary stage of the application
     * @param controller The screen controller for navigating between different screens
     * @param client The client for interacting with the network
     */
    public UserProfileScreen(Stage stage, ScreenController controller, Client client, String initialUser) {
        // User Profile Title
        this.currentUser = initialUser;

        Label userProfileTitle = new Label("USER PROFILE: " + initialUser);
        userProfileTitle.getStyleClass().add("title-label");

        // Layout for displaying user profile information
        userProfileInfoLayout = new VBox(10);
        userProfileInfoLayout.setAlignment(Pos.CENTER_LEFT);
        userProfileInfoLayout.setPadding(new Insets(20));
        userProfileInfoLayout.getStyleClass().add("user-profile-box");

        // Display user profile details (initially the logged-in user's profile)
        String userProfile = client.searchProfile(initialUser);
        displayProfile(userProfile);

        // Label and text field for searching another user's profile
        Label searchProfileLabel = new Label("Search Profile:");
        searchProfileLabel.getStyleClass().add("search-label");

        TextField searchProfileField = new TextField();
        searchProfileField.getStyleClass().add("input-field");
        searchProfileField.setPromptText("Enter profile name");

        // Search button to find and display another user's profile information
        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("button");
        searchButton.getStyleClass().add("submit-button");
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
        Button xButton = new Button("View Your Profile");
        xButton.getStyleClass().add("button");
        xButton.getStyleClass().add("exit-button");
        xButton.setOnAction(e -> {
            // Reset to the logged-in user's profile information
            String updatedProfileInfo = client.getCurrentUserProfile();
            String currentUsernameReset = client.getCurrentUsername();
            this.currentUser = currentUsernameReset;
            displayProfile(updatedProfileInfo);
            userProfileTitle.setText("USER PROFILE: " + currentUsernameReset);
            searchProfileField.clear();
        });

        // Button to go to the manage profile screen
        Button manageProfileButton = new Button("Manage Profile");
        manageProfileButton.getStyleClass().add("button");
        manageProfileButton.getStyleClass().add("submit-button");
        manageProfileButton.setOnAction(e -> controller.showManageProfileScreen());

        // Button to go back to the main menu
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> controller.showMainMenu());

        Button matchHistoryButton = new Button("Match History");
        matchHistoryButton.getStyleClass().add("button");
        matchHistoryButton.getStyleClass().add("submit-button");
        matchHistoryButton.setOnAction(e -> {
            String searchResults = searchProfileField.getText();
            if (searchResults.isEmpty()) {
                controller.showMatchHistoryScreen(currentUser);
            } else {
                controller.showMatchHistoryScreen(searchResults);
            }
        });

        // Layout for search user profile components
        HBox searchLayout = new HBox(10, searchProfileLabel, searchProfileField, searchButton, xButton);
        searchLayout.setAlignment(Pos.CENTER);

        // Layout for user profile screen buttons
        HBox buttonsLayout = new HBox(20, matchHistoryButton, manageProfileButton, backButton);
        buttonsLayout.setAlignment(Pos.CENTER);

        // Main layout of the user profile screen
        VBox userProfileLayout = new VBox(20, userProfileTitle, userProfileInfoLayout, searchLayout, buttonsLayout);
        userProfileLayout.setAlignment(Pos.CENTER);
        userProfileLayout.setPadding(new Insets(20));

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(userProfileLayout);
        rootPane.getStyleClass().add("root-pane");

        // Initialize the scene for the user profile screen
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("styles.css").toExternalForm()));
    }

    /**
     * Displays the given profile information in the user profile screen
     *
     * @param profileInfo The user profile information to show
     */
    private void displayProfile(String profileInfo) {
        // Clear the existing content from the profile display area
        userProfileInfoLayout.getChildren().clear();

        // Handle condition when the user profile cannot be found
        if (profileInfo.startsWith("Profile not found")) {
            Label notFoundLabel = new Label(profileInfo);
            notFoundLabel.getStyleClass().add("search-label");
            userProfileInfoLayout.getChildren().add(notFoundLabel);
            return;
        }

        // Parse and display every user profile information into blocks
        String[] profileLists = profileInfo.split("\n\n");
        // Iterate through each user profile block to extract key-value pairs
        for (String block : profileLists) {
            String[] lines = block.split("\n");
            // Store key-value pairs from the profile block
            Map<String, String> profileData = new HashMap<>();
            // Iterate through each line in block to split by delimiter
            for (String line : lines) {
                String[] parts = line.split(": ");
                // Add valid key-value pairs to the map
                if (parts.length == 2) {
                    profileData.put(parts[0], parts[1]);
                }
            }

            // Layout to display user profile information
            VBox profileBox = new VBox(5);
            profileBox.setPadding(new Insets(10));
            profileBox.getStyleClass().add("profile-box");

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
     * @param title The title of the error message dialog box
     * @param message The content of the error message dialog box
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
     * Returns the constructed scene for the user profile screen
     *
     * @return Scene representing the user profile screen
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}

