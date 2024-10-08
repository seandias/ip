package fx;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import skd.SkD;
import skd.ui.Ui;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private SkD skD;
    private Ui ui = new Ui();

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initializes the main window by binding the scroll pane to automatically scroll to the latest message,
     * displays a welcome message, and clears the user input field.
     * This method binds the vertical value property of the scroll pane to the height of the dialog container,
     * ensuring that the latest message is always visible. It also adds a welcome message to the dialog container
     * and resets the user input field.
     * This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String greet = ui.stringShowWelcome();
        dialogContainer.getChildren().addAll(DialogBox.getDukeDialog(greet, dukeImage));
        userInput.clear();
    }

    /** Injects the Duke instance */
    public void setSkd(SkD d) {
        skD = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = skD.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        if (input.equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }

        userInput.clear();
    }
}
