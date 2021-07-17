package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;


public class MainMenuController {

    private MediaPlayer sound;
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
    public void initialize() {
        if(Main.getSound() == null) {
            Media media = new Media(getClass().getResource("/sounds/mainMenuSound.mp3").toExternalForm());
            sound = new MediaPlayer(media);
            sound.setOnEndOfMedia(() -> {
                sound.seek(Duration.ZERO);
            });
            sound.play();
            Main.setSound(sound);
        }
        Runnable runnable = () -> {
            while (true) {
                if (username.getScene() != null) {
                    User user = (User) username.getScene().getWindow().getUserData();
                    username.setText(user.getUsername());
                    return;
                } else {
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
        Media media = new Media(getClass().getResource("/sounds/click.wav").toExternalForm());
        MediaPlayer click = new MediaPlayer(media);
        click.play();
        Object object = event.getSource();
        if (object == logOutButton) {
            saveUserInFile();
            Main.changeScene("Login.fxml");
        } else if (object == profileButton) {
            Main.changeScene("Profile.fxml");
        } else if (object == BattleDeckButton) {
            Main.changeScene("BattleDeck.fxml");
        } else if (object == BattleHistoryButton) {
            Main.changeScene("BattleHistory.fxml");
        }
    }

    private void saveUserInFile() {
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
