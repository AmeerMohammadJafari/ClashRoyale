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
 * This class controls login scene
 */
public class LoginController {

    private MediaPlayer click;

    private User loginUser;
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Label errorLabel;

    /**
     * just make the login button disable at first
     */
    @FXML
    public void initialize(){
        loginButton.setDisable(true);
    }




    private boolean checkFields(){

        // check users for login
        User u = null;
        try (FileInputStream fIn = new FileInputStream("Info.bin");
             ObjectInputStream input = new ObjectInputStream(fIn)) {
            while (true) {
                u = (User) input.readObject();
                if(u == null)
                    break;
                if (usernameTextField.getText().equals(u.getUsername()) &&
                passwordTextField.getText().equals(u.getPassword())) {
                    loginUser = u;
                    return true;
                }
            }
        } catch (EOFException ignored){
            System.out.println("end of the file");
        }
        catch (FileNotFoundException | NullPointerException ignored){

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        errorLabel.setText("Not correct");
        return false;
    }


    /**
     * This method is for handling button clicking
     * @param event action event
     */
    @FXML
    void handle(ActionEvent event) {
        Media media = new Media(getClass().getResource("/sounds/click.wav").toExternalForm());
        click = new MediaPlayer(media);
        click.play();
        if(event.getSource() == loginButton) {
            if (checkFields()) {
                Main.changeScene("MainMenu.fxml", loginUser);
            }
        }
        else if(event.getSource() == signUpButton){
            Main.changeScene("SignUp.fxml");
        }
    }

    /**
     * This method make loging button enable whenever the password and username fields are not empty
     * @param event key event
     */
    @FXML
    void keyFields(KeyEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        loginButton.setDisable(username.isEmpty() || username.trim().isEmpty() || password.isEmpty());
    }






}
