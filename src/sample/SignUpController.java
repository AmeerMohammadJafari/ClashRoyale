package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.*;

/**
 * This class controls the sign up scene
 */
public class SignUpController {

    @FXML
    private Button backButton;

    @FXML
    private TextField username;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;

    /**
     * just set the sign up button disable at first
     */
    @FXML
    public void initialize() {
        signUpButton.setDisable(true);
    }


    private boolean checkFields() {
        if (User.repetitiveUsername(username.getText())) {
            errorLabel.setText("The username you entered is chosen by another user.Please try another one !");
            return false;
        }
        else return true;
    }

    /**
     * handling button clicing
     * @param event action event
     */
    @FXML
    void handle(ActionEvent event) {
        Media media = new Media(getClass().getResource("/sounds/click.wav").toExternalForm());
        MediaPlayer click = new MediaPlayer(media);
        click.play();
        if (event.getSource() == backButton) {
            Main.changeScene("Login.fxml");
        } else if (event.getSource() == signUpButton) {
            if (checkFields()) {
                User user = new User(username.getText(), password.getText());
                User.saveUser(user);
                System.out.println("sign up successfully");
                errorLabel.setText("You signed up successfully !");
                Main.changeScene("Login.fxml");
            }
        }
    }


    /**
     * check the username and password fields, whenever both are not empty, the sign up button will be able
     * @param event key event
     */
    @FXML
    void checkFields(KeyEvent event) {
        signUpButton.setDisable(username.getText().isEmpty() || username.getText().trim().isEmpty() ||
                password.getText().isEmpty());
    }


}
