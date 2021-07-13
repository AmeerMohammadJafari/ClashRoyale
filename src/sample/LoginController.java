package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;

public class LoginController {

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





    private boolean checkFields(){
        // check empty fields
        if(usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()){
            errorLabel.setText("The fields must not be empty");
            return false;
        }
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



    @FXML
    void handle(ActionEvent event) {
        if(event.getSource() == loginButton) {
            if (checkFields()) {

            }
        }
        else if(event.getSource() == signUpButton){
            Main.changeScene("SignUp.fxml");
        }
    }

}
