package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventDispatcher;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileController {

    private User user;

    @FXML
    private Label numberOfCups;

    @FXML
    private Label currentLeague;

    @FXML
    private Label level;

    @FXML
    private Button backButton;

    @FXML
    private ImageView card2;

    @FXML
    private ImageView card3;

    @FXML
    private ImageView card4;

    @FXML
    private ImageView card5;

    @FXML
    private ImageView card6;

    @FXML
    private ImageView card1;

    @FXML
    private ImageView card7;

    @FXML
    private ImageView card8;


    // the data must first become updated in initialize method
    @FXML
    public void initialize() {
        Runnable runnable = () -> {
            while(true){
                if(card1.getScene() != null){
                    updateScene();
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


    public void updateScene() {
        user = (User) card1.getScene().getWindow().getUserData();
        // updating the labels
        numberOfCups.setText(String.valueOf(user.getNumberOfCups()));
        currentLeague.setText(user.getCurrentLeague());
        level.setText(String.valueOf(user.getLevel()));

        ArrayList<ImageView> images = new ArrayList<>();
        images.add(card1);
        images.add(card2);
        images.add(card3);
        images.add(card4);
        images.add(card5);
        images.add(card6);
        images.add(card7);
        images.add(card8);
        // initializing the images to empty at first
        for (ImageView image : images) {
            image.setImage(new Image("\\images\\EmptyCard.png"));
        }

        for (int i = 0; i < 8; i++) {
            Deck d = null;
            try {
                d = user.getDecks().get(i);
                images.get(i).setImage(Deck.deckToImage(d));
            } catch (NullPointerException | IndexOutOfBoundsException ignored) {
                images.get(i).setImage(new Image("\\images\\EmptyCard.png"));
            }

        }

    }

    @FXML
    void handle() {
        Main.changeScene("MainMenu.fxml");
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
