package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class MainMenuController {


    @FXML
    private Button profileButton;

    @FXML
    private Button BattleDeckButton;

    @FXML
    private Button BattleHistoryButton;

    @FXML
    private Button TrainingCampButton;

    @FXML
    private Button oneVsOneButton;

    @FXML
    private Button twoVsTwoButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Label username;

    @FXML
    public void initialize(){
        
        Runnable runnable = () -> {
            while(true){
                if(username.getScene() != null){
                    User user = (User) username.getScene().getWindow().getUserData();
                    username.setText(user.getUsername());
                    return;
                }
                else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Platform.runLater(runnable);
    }

    @FXML
    void handle(ActionEvent event) {
        Object object = event.getSource();
        if(object == logOutButton){
            saveUserInFile();
            Main.changeScene("Login.fxml");
        }
        else if(object == profileButton){
            Main.changeScene("Profile.fxml");
        }
        else if(object == BattleDeckButton){
            Main.changeScene("BattleDeck.fxml");
        }
    }

    private void saveUserInFile(){
        User.rewriteUser((User) logOutButton.getScene().getWindow().getUserData());
    }


    @FXML
    void mouseEntered(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    void mouseExited(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.getScene().setCursor(Cursor.DEFAULT);
    }

}
