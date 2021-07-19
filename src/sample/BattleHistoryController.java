package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * This class controls Battle history scene
 */
public class BattleHistoryController {

    private ArrayList<Label> youList = new ArrayList<>();
    private ArrayList<Label> vsList = new ArrayList<>();
    private ArrayList<Label> opponentList = new ArrayList<>();
    @FXML
    private Label you1;

    @FXML
    private Label opponent1;

    @FXML
    private Label you2;

    @FXML
    private Label opponent2;

    @FXML
    private Label you3;

    @FXML
    private Label opponent3;

    @FXML
    private Label vs1;

    @FXML
    private Label vs2;

    @FXML
    private Label vs3;

    @FXML
    private Button backButton;

    /**
     * build the scenes and initializing the nodes with user's info
     */
    @FXML
    public void initialize(){
        vsList.add(vs1);
        vsList.add(vs2);
        vsList.add(vs3);
        youList.add(you1);
        youList.add(you2);
        youList.add(you3);
        opponentList.add(opponent1);
        opponentList.add(opponent2);
        opponentList.add(opponent3);
        for(Label l : vsList){
            l.setVisible(false);
        }
        for(Label l : youList){
            l.setVisible(false);
        }
        for(Label l : opponentList){
            l.setVisible(false);
        }
        Runnable runnable = () -> {
            while(true){
                if(vs1.getScene() != null){
                    update();
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

    private void update(){
        User user = (User) vs1.getScene().getWindow().getUserData();
        LinkedHashMap<Boolean, String> history = user.getBattleHistory();
        int i = 0 ;
        try {
            for (Boolean b : history.keySet()) {
                Label you = youList.get(i);
                Label vs = vsList.get(i);
                Label opponent = opponentList.get(i);
                you.setVisible(true);
                vs.setVisible(true);
                opponent.setVisible(true);
                if (b) {
                    you.setTextFill(Color.GREEN);
                    opponent.setTextFill(Color.RED);
                } else {
                    you.setTextFill(Color.RED);
                    opponent.setTextFill(Color.GREEN);
                }
                i++;
            }
        }catch (NullPointerException ignored){

        }
    }

    /**
     * handle buttons clicking
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
