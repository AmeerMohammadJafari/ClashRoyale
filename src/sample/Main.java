package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;
    private static MediaPlayer sound;

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

    public static void changeScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxml));
            stage.setScene(new Scene(root));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

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

    public static MediaPlayer getSound() {
        return sound;
    }

    public static void setSound(MediaPlayer sound) {
        Main.sound = sound;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
