package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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



    private boolean checkFields() {
        // check empty fields

        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            errorLabel.setText("The fields must not be empty");
            return false;
        }



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
                if(u == null)
                    return false;
                if (username.getText().equals(u.getUsername())) {
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
        System.out.println("reach end of the repetitive");
        return false;
    }

    private void writeUser(User user) {
        User u = null;
        ArrayList<User> users = new ArrayList<>();
        try (FileInputStream fIn = new FileInputStream("Info.bin");
             ObjectInputStream input = new ObjectInputStream(fIn)) {
            while(true) {
                u = (User) input.readObject();
                if(u == null)
                    break;
                users.add(u);
            }
        }catch (EOFException | FileNotFoundException ignored){

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try (FileOutputStream fOut = new FileOutputStream("Info.bin");
             ObjectOutputStream output = new ObjectOutputStream(fOut)) {

            for (User element : users) {
                output.writeObject(element);
            }
            output.writeObject(user);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handle(ActionEvent event) {
        if (event.getSource() == backButton) {
            Main.changeScene("Login.fxml");
        } else if (event.getSource() == signUpButton) {
            if (checkFields()) {
                User user = new User(username.getText(), password.getText());
                writeUser(user);
                System.out.println("sign up successfully");
                errorLabel.setText("You signed up successfully !");
                Main.changeScene("Login.fxml");
            }
        }
    }

}
