package src.ca.ucalgary.seng300.gameApp.leaderboardScreens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import src.ca.ucalgary.seng300.network.Client;
import src.ca.ucalgary.seng300.leaderboard.logic.Leaderboard;
import src.ca.ucalgary.seng300.gameApp.IScreen;

/**
 * Represents the Checkers Leaderboard screen, implementing the IScreen interface.
 * This class handles the UI components and layout for displaying the leaderboard.
 */
public class CheckerLB implements IScreen {
    private Scene scene;// The scene representing the leaderboard screen

    /**
     * Constructs the Checkers Leaderboard screen.
     *
     * @param stage      The primary stage of the application.
     * @param controller The LeaderboardController to handle navigation.
     * @param client     The client for retrieving leaderboard data.
     */
    public CheckerLB(Stage stage, LeaderboardController controller, Client client) {
        // Title Label
        Label titleLabel = new Label("CHECKERS' LEADERBOARD");
        titleLabel.getStyleClass().add("leaderboard-title");

        // Retrieve leaderboard data from the client
        String[][] leaderboard = client.getCheckersLeaderboard(() -> {
            System.out.println("\n" + "Checkers Leaderboard GET call succeeded");
        });

        //Get leaderboard information
        VBox leaderboardEntries = createLeaderboardEntries(leaderboard);

        // Back Button
        Button backButton = new Button("Back to LeaderBoard Menu");
        backButton.getStyleClass().add("leaderboard-button");
        backButton.getStyleClass().add("leaderboard-button-primary");
        backButton.setOnAction(e -> controller.showLeaderBoardMenu());

        // Main Layout
        VBox layout = new VBox(20, titleLabel, leaderboardEntries, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Root Pane for the leaderboard screen
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(layout);
        rootPane.getStyleClass().add("leaderboard-pane");

        // Scene
        scene = new Scene(rootPane, 1280, 900);
        scene.getStylesheets().add((getClass().getClassLoader().getResource("LeaderboardStyles.css").toExternalForm()));
    }

    /**
     * Creates a styled label.
     *
     * @param text      The text for the label.
     * @param fontSize  The font size for the label.
     * @param alignment The alignment for the label.
     * @return A styled Label instance.
     */
    private Label createLabel(String text, int fontSize, Pos alignment) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", fontSize));
        label.setAlignment(alignment);
        return label;
    }

    /**
     * Creates a VBox containing leaderboard entries.
     *
     * @param data A 2D array of leaderboard data, where each row contains player ID, rating, and wins.
     * @return The VBox containing leaderboard entries.
     */
    private VBox createLeaderboardEntries(String[][] data) {
        int lastEntry = data.length - 1;// Identify the last entry to style the box differently
        int count = 0;// Counter for the current entry index

        // VBox to hold all leaderboard entries.
        VBox entriesBox = new VBox(5);
        entriesBox.setAlignment(Pos.CENTER);
        entriesBox.setPadding(new Insets(10));
        entriesBox.setMaxWidth(420);
        entriesBox.setStyle("-fx-border-color: grey; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");

        // Header row with column titles.
        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setMaxWidth(400);
        headerBox.setPrefWidth(400);
        headerBox.setStyle("-fx-background-color: grey; -fx-padding: 10; -fx-background-radius: 10 10 0 0;");

        // Create header labels for Player ID, Rating, and Wins
        Label nameHeader = new Label("PLAYERID");
        nameHeader.setFont(Font.font("Arial",FontWeight.BOLD,20));
        nameHeader.setPrefWidth(220);
        nameHeader.setAlignment(Pos.CENTER);
        nameHeader.setTextFill(Color.WHITE);

        Label eloHeader = new Label("RATING");
        eloHeader.setFont(Font.font("Arial",FontWeight.BOLD,20));
        eloHeader.setPrefWidth(90);
        eloHeader.setAlignment(Pos.CENTER);
        eloHeader.setTextFill(Color.WHITE);

        Label winsHeader = new Label("WINS");
        winsHeader.setFont(Font.font("Arial",FontWeight.BOLD,20));
        winsHeader.setPrefWidth(80);
        winsHeader.setAlignment(Pos.CENTER);
        winsHeader.setTextFill(Color.WHITE);

        // Add headers to the header box
        headerBox.getChildren().addAll(nameHeader,eloHeader,winsHeader);
        entriesBox.getChildren().add(headerBox);

        // Add each player's data to the leaderboard
        for (String[] entry : data) {
            HBox entryBox = new HBox(10);
            entryBox.setSpacing(40);
            entryBox.setStyle("-fx-padding: 5; -fx-background-color: lightgrey;");
            entryBox.setPrefWidth(400);
            entryBox.setMinWidth(400);
            entryBox.setMaxWidth(400);

            // Apply special style for the last entry.
            if (count == lastEntry) {
                entryBox.setStyle("-fx-padding: 5; -fx-background-color: lightgrey; -fx-background-radius: 0 0 10 10;");

            }

            entryBox.setAlignment(Pos.BASELINE_LEFT);

            Label playerLabel = new Label(entry[0]);
            playerLabel.setFont(Font.font("Arial", FontWeight.BOLD,16));
            playerLabel.setPrefWidth(160);
            playerLabel.setAlignment(Pos.CENTER);

            Label eloLabel = new Label(entry[1]);
            eloLabel.setFont(new Font("Arial", 16));
            eloLabel.setPrefWidth(100);
            eloLabel.setAlignment(Pos.CENTER);

            Label winsLabel = new Label(entry[2]);
            winsLabel.setFont(new Font("Arial", 16));
            eloLabel.setPrefWidth(100);
            eloLabel.setAlignment(Pos.CENTER);

            // Add labels to the entry box
            entryBox.getChildren().addAll(playerLabel, eloLabel, winsLabel);
            entriesBox.getChildren().add(entryBox);

            count++;
        }
        return entriesBox;
    }

    /**
     * Creates a styled button.
     *
     * @param text      The text for the button.
     * @param fontSize  The font size for the button text.
     * @param width     The width of the button.
     * @param bgColor   The background color for the button.
     * @param action    The event handler for the button.
     * @return A styled Button instance.
     */
    private Button createButton(String text, int fontSize, double width, String bgColor, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button button = new Button(text);
        button.setFont(new Font("Arial", fontSize));
        button.setPrefWidth(width);
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white;", bgColor));
        button.setOnAction(action);
        return button;
    }

    /**
     * Returns the scene for this screen.
     *
     * @return The Scene instance for this screen.
     */
    @Override
    public Scene getScene() {
        return scene;
    }
}




