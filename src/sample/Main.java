package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class starts the application and create a stage and set a scene to it
 */
public class Main extends Application {

    private static Stage stage;
    private static MediaPlayer sound;

    /**
     * set the initial scene (Login.fxml) to the stage
     * @param primaryStage stage
     * @throws Exception exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.getIcons().add(new Image("\\images\\stageIcon.png"));
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Clash Royale");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * This method changes the scene of the stage
     * @param fxml name of the fxml file
     */
    public static void changeScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxml));
            stage.setScene(new Scene(root));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This method changes the scene of the stage but also set an object (user) to the stage
     * @param fxml name of the fxml file
     * @param user user which will be set to the stage
     */
    public static void changeScene(String fxml, User user){
        try {
            stage.setUserData(user);
            Parent root = FXMLLoader.load(Main.class.getResource(fxml));
            stage.setScene(new Scene(root));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * getter method for sound
     * @return sound
     */
    public static MediaPlayer getSound() {
        return sound;
    }

    /**
     * setter method for sound
     * @param sound sound
     */
    public static void setSound(MediaPlayer sound) {
        Main.sound = sound;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
