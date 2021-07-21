package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class is a controller for the Battle Deck scene
 */
public class BattleDeckController {

    private ArrayList<Button> deckButtons;
    private ArrayList<Button> upButtons;
    private ArrayList<ImageView> images;
    private LinkedList<Deck> chosenDecks = new LinkedList<>();


    @FXML
    private Button barbarianButton;

    @FXML
    private Button archerButton;

    @FXML
    private Button wizardButton;

    @FXML
    private Button miniPekkaButton;

    @FXML
    private Button giantButton;

    @FXML
    private Button valkyrieButton;

    @FXML
    private Button rageButton;

    @FXML
    private Button infernoButton;

    @FXML
    private Button cannonButton;

    @FXML
    private Button arrowsButton;

    @FXML
    private Button fireballButton;

    @FXML
    private Button babyDragonButton;

    @FXML
    private Button backButton;

    @FXML
    private Button button1;

    @FXML
    private ImageView card1;

    @FXML
    private Button button2;

    @FXML
    private ImageView card2;

    @FXML
    private Button button7;

    @FXML
    private ImageView card7;

    @FXML
    private Button button5;

    @FXML
    private ImageView card5;

    @FXML
    private Button button6;

    @FXML
    private ImageView card6;

    @FXML
    private Button button4;

    @FXML
    private ImageView card4;

    @FXML
    private Button button3;

    @FXML
    private ImageView card3;

    @FXML
    private Button button8;

    @FXML
    private ImageView card8;


    /**
     * initialize the nodes of the scene with user's info
     */
    @FXML
    public void initialize() {

        deckButtons = new ArrayList<>();
        deckButtons.add(barbarianButton);
        deckButtons.add(archerButton);
        deckButtons.add(babyDragonButton);
        deckButtons.add(wizardButton);
        deckButtons.add(miniPekkaButton);
        deckButtons.add(giantButton);
        deckButtons.add(valkyrieButton);
        deckButtons.add(rageButton);
        deckButtons.add(infernoButton);
        deckButtons.add(cannonButton);
        deckButtons.add(arrowsButton);
        deckButtons.add(fireballButton);
        // upButtons
        upButtons = new ArrayList<>();
        upButtons.add(button1);
        upButtons.add(button2);
        upButtons.add(button3);
        upButtons.add(button4);
        upButtons.add(button5);
        upButtons.add(button6);
        upButtons.add(button7);
        upButtons.add(button8);
        // images
        images = new ArrayList<>();
        images.add(card1);
        images.add(card2);
        images.add(card3);
        images.add(card4);
        images.add(card5);
        images.add(card6);
        images.add(card7);
        images.add(card8);


        for (ImageView imageView : images) {
            imageView.setImage(new Image("\\images\\EmptyCard.png"));
        }

        for (Button button : upButtons) {
            button.setDisable(true);
        }


        Runnable runnable = () -> {
            while (true) {
                if (card1.getScene() != null && button1 != null) {
                    updateCards();
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


    private void updateCards() {

        User user = (User) card1.getScene().getWindow().getUserData();
        // update chosenDecks
        chosenDecks.addAll(user.getDecks());

        ArrayList<Image> userImages = new ArrayList<>();
        for (Deck deck : user.getDecks()) {
            userImages.add(Deck.deckToImage(deck));
        }
        try {
            for (int i = 0; i < 8; i++) {
                images.get(i).setImage(userImages.get(i));
                upButtons.get(i).setDisable(false);
            }
        } catch (NullPointerException | IndexOutOfBoundsException ignored) {

        }
        for (Deck deck : chosenDecks) {
            deckSituation(deck, true);
        }
    }

    private Deck getDeck(Button button) {
        if (button == barbarianButton)
            return Deck.Barbarian;
        else if (button == archerButton)
            return Deck.Archer;
        else if (button == babyDragonButton)
            return Deck.BabyDragon;
        else if (button == wizardButton)
            return Deck.Wizard;
        else if (button == miniPekkaButton)
            return Deck.MiniPekka;
        else if (button == giantButton)
            return Deck.Giant;
        else if (button == valkyrieButton)
            return Deck.Valkyrie;
        else if (button == rageButton)
            return Deck.Rage;
        else if (button == fireballButton)
            return Deck.Fireball;
        else if (button == arrowsButton)
            return Deck.Arrows;
        else if (button == cannonButton)
            return Deck.Cannon;
        else if (button == infernoButton)
            return Deck.InfernoTower;
        return null;
    }


    private boolean allEnable(){
        for(Button button : upButtons){
            if(button.isDisabled())
                return false;
        }
        return true;
    }

    /**
     * This method handles a button clicking
     * @param event
     */
    @FXML
    void handle(ActionEvent event) {
        Media media = new Media(getClass().getResource("/sounds/click.wav").toExternalForm());
        MediaPlayer click = new MediaPlayer(media);
        click.play();
        Button src = (Button) event.getSource();
        if (src == backButton) {
            // TODO update the user
            updateUser();
            Main.changeScene("MainMenu.fxml");
        } else if (deckButtons.contains(src)) {
            if(allEnable()){
                return;
            }
            src.setDisable(true);
            int enables = 0;
            for (int i = 0; i < 8; i++) {
                if (upButtons.get(i).isDisabled()) {
                    upButtons.get(i).setDisable(false);
                    // set the up image
                    // get Deck from below buttons
                    images.get(i).setImage(Deck.deckToImage(getDeck(src)));
                    chosenDecks.add(enables, getDeck(src));
                    break;
                } else
                    enables++;
            }
        } else if (upButtons.contains(src)) {
            int enable = 0;
            int i = 0;
            while (i < 8) {
                if (!upButtons.get(i).isDisable()) {
                    if (upButtons.get(i) == src) {
                        images.get(i).setImage(new Image("\\images\\EmptyCard.png"));
                        break;
                    }
                    enable++;
                }
                i++;
            }
            src.setDisable(true);
            updateDecks(enable);
        }

    }

    private void updateUser() {
        User user = (User) card1.getScene().getWindow().getUserData();
        user.getDecks().clear();
        user.getDecks().addAll(chosenDecks);
    }


    private void deckSituation(Deck d, boolean disable) {
        switch (d) {
            case Barbarian -> barbarianButton.setDisable(disable);
            case Archer -> archerButton.setDisable(disable);
            case BabyDragon -> babyDragonButton.setDisable(disable);
            case Wizard -> wizardButton.setDisable(disable);
            case MiniPekka -> miniPekkaButton.setDisable(disable);
            case Giant -> giantButton.setDisable(disable);
            case Valkyrie -> valkyrieButton.setDisable(disable);
            case Rage -> rageButton.setDisable(disable);
            case Fireball -> fireballButton.setDisable(disable);
            case Arrows -> arrowsButton.setDisable(disable);
            case Cannon -> cannonButton.setDisable(disable);
            case InfernoTower -> infernoButton.setDisable(disable);
        }
    }

    private void updateDecks(int index) {
        Deck d = chosenDecks.get(index);
        chosenDecks.remove(d);
        deckSituation(d, false);


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
