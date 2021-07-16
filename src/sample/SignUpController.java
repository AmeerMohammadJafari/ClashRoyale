package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.util.ArrayList;

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

    @FXML
    public void initialize() {
        signUpButton.setDisable(true);
    }


    private boolean checkFields() {
        // check repetitive user
        if (repetitiveUser()) {
            errorLabel.setText("The username you entered is chosen by another user.Please try another one !");
            return false;
        }
        return true;
    }

    private boolean repetitiveUser() {
        User u = null;
        try (FileInputStream fIn = new FileInputStream("Info.bin");
             ObjectInputStream input = new ObjectInputStream(fIn)) {
            while (true) {
                u = (User) input.readObject();
                if (u == null)
                    return false;
                if (username.getText().equals(u.getUsername())) {
                    return true;
                }
            }
        } catch (EOFException ignored) {

        } catch (FileNotFoundException | NullPointerException ignored) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    @FXML
    void handle(ActionEvent event) {
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


    @FXML
    void checkFields(KeyEvent event) {
        signUpButton.setDisable(username.getText().isEmpty() || username.getText().trim().isEmpty() ||
                password.getText().isEmpty());
    }


}
