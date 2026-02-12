package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import engine.Engine;
import engine.Parser;
import engine.Command;
import exception.InvalidActionException;
import exception.MissingTaskException;

public class Main extends Application {
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Engine engine;

    /**
     * Starts the JavaFX application.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        // Initialize engine chatbot
        String filePath = "src/data/data.txt";
        engine = new Engine(filePath);

        // Create the main layout
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(10));

        // Create dialog display area
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        dialogContainer.setSpacing(8);
        dialogContainer.setPadding(new Insets(10));
        scrollPane.setContent(dialogContainer);
        scrollPane.setFitToWidth(true);

        // Add welcome message
        addDialogItem("Dicky", "Hello! I'm dicky. How can I help you today?", true);

        // Create input area
        HBox inputLayout = new HBox();
        inputLayout.setSpacing(8);
        userInput = new TextField();
        userInput.setPromptText("Enter a command...");
        userInput.setPrefHeight(40);

        sendButton = new Button("Send");
        sendButton.setPrefHeight(40);
        sendButton.setPrefWidth(80);

        HBox.setHgrow(userInput, Priority.ALWAYS);
        inputLayout.getChildren().addAll(userInput, sendButton);

        // Add components to main layout
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        mainLayout.getChildren().addAll(scrollPane, inputLayout);

        // Set up event handlers
        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());

        // Configure stage
        Scene scene = new Scene(mainLayout, 600, 700);
        stage.setTitle("engine Chatbot");
        stage.setScene(scene);
        stage.show();

        // Focus on input field
        userInput.requestFocus();
    }

    /**
     * Handles the user input event (button click or enter key).
     */
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        // Display user message
        addDialogItem("You", input, false);

        try {
            // Parse command
            Command cmd = Parser.parseCommand(input);
            String details = Parser.parseDetails(input);

            // Handle EXIT command
            if (cmd == Command.EXIT) {
                addDialogItem("engine", "Goodbye! Your tasks have been saved.", true);
                engine.write(); // Save tasks before exit
                userInput.setDisable(true);
                sendButton.setDisable(true);
                return;
            }

            // Execute command and get response
            String response = engine.executeCommandAndGetResponse(cmd, details);
            addDialogItem("engine", response, true);

        } catch (InvalidActionException | MissingTaskException e) {
            addDialogItem("engine", "Error: " + e.getMessage(), true);
        } catch (Exception e) {
            addDialogItem("engine", "An error occurred: " + e.getMessage(), true);
        }

        // Clear input
        userInput.clear();
        userInput.requestFocus();

        // Scroll to bottom
        scrollPane.setVvalue(1.0);
    }

    /**
     * Adds a dialog item (message) to the dialog container.
     *
     * @param sender       The name of the sender (e.g., "You" or "Dicky").
     * @param message      The message content.
     * @param isBotMessage True if the message is from the bot, false otherwise.
     */
    private void addDialogItem(String sender, String message, boolean isBotMessage) {
        VBox messageBox = new VBox();
        messageBox.setStyle(isBotMessage ? "-fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-color: #f5f5f5;" : 
                                          "-fx-border-color: #0078d4; -fx-border-radius: 5; -fx-background-color: #e3f2fd;");
        messageBox.setPadding(new Insets(8));
        
        Text senderLabel = new Text(sender + ":");
        senderLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        
        TextFlow messageFlow = new TextFlow();
        messageFlow.setStyle("-fx-padding: 5;");
        messageFlow.setMaxWidth(500);
        Text messageText = new Text(message);
        messageText.setStyle(isBotMessage ? "-fx-font-size: 13;" : "-fx-font-size: 13;");
        messageFlow.getChildren().add(messageText);
        
        messageBox.getChildren().addAll(senderLabel, messageFlow);
        dialogContainer.getChildren().add(messageBox);
    }

    /**
     * The main entry point for the JavaFX application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}