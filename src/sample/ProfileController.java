package sample;

import javafx.application.Platform;

import javafx.fxml.FXML;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;

/**
 * This class controls the profile scene
 */
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


    /**
     * initializing the nodes from user's info
     */
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

    private void updateScene() {
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

    /**
     * handle back button
     */
    @FXML
    void handle() {
        Media media = new Media(getClass().getResource("/sounds/click.wav").toExternalForm());
        MediaPlayer click = new MediaPlayer(media);
        click.play();
        Main.changeScene("MainMenu.fxml");
    }


    /**
     * when mouse entered to a button's range, the cursor will be in hand form
     * @param event mouse event
     */
    @FXML
    void mouseEntered(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.getScene().setCursor(Cursor.HAND);
    }

    /**
     * when mouse exited from a button's range, the cursor will be again in default form
     * @param event mouse event
     */
    @FXML
    void mouseExited(MouseEvent event) {
        Node node = (Node) event.getSource();
        node.getScene().setCursor(Cursor.DEFAULT);
    }


}
