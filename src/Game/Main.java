//package Game;
//
//import Characters.BaseCharacter;
//import Characters.Bullets.BaseBullet;
//import Characters.Teams;
//import Characters.Towers.MainTower;
//import Characters.players.Wizard;
//import Utils.Bar;
//import Utils.Images;
//import javafx.animation.AnimationTimer;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.event.EventHandler;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
////TODO: edit distanceX and distanceZ
//public class Main extends Application {
//    List<MainTower> towerList = new ArrayList<>();
//    AnchorPane gameField = new AnchorPane();
//    Images images = new Images();
//    int seconds = 3*60;
//    List<ImageView> deck = new ArrayList<>(){{
//        add(new ImageView());
//        add(new ImageView());
//        add(new ImageView());
//    }};
//    Bar exirBar = new Bar(Color.WHITE, Color.PURPLE, 300);
//    Label exirLabel = new Label();
//    List<BaseCharacter> playerDeck;
//    List<BaseCharacter> botDeck;
//
//
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//
//        Group root = new Group();
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 1000, 1000));
//        primaryStage.setResizable(false);
//        primaryStage.show();
//
//
//        gameField.setPrefSize(1024,1000);
//        gameField.relocate(0,0);
//        root.getChildren().add(gameField);
//
//
//       loadGame();
//
//
////        towerList.add(new MainTower(images, gameField,100,200,0,0,0,0,Setting.KingHp.get(Levels.L1),30,5,Teams.BLUE,5 ));
////        towerList.add(new MainTower(images, gameField,450,110,0,0,0,0,Setting.KingHp.get(Levels.L1),30,5,Teams.BLUE,5 ));
////        towerList.add(new MainTower(images, gameField, 800,200,0,0,0,0,Setting.KingHp.get(Levels.L1),30,5,Teams.BLUE,5 ));
////        towerList.add(new MainTower(images, gameField, 100,700,0,0,0,0,Setting.KingHp.get(Levels.L1),30,5,Teams.BLUE,5 ));
////        towerList.add(new MainTower(images, gameField, 450,800,0,0,0,0,Setting.KingHp.get(Levels.L1),30,5,Teams.BLUE,5 ));
////        towerList.add(new MainTower(images, gameField, 800,700,0,0,0,0,Setting.KingHp.get(Levels.L1),30,5,Teams.BLUE,5 ));
//        List<BaseBullet> bulletList = new ArrayList<>();
//        Wizard wizard = new Wizard(images, gameField, 600,600,0,2,2,0,50,30,60,Teams.BLUE,towerList,3,bulletList,botDeck);
//        AnimationTimer animationTimer = new AnimationTimer() {
//            @Override
//            public void handle(long l) {
//
//
//            }
//        };
//
//        Label main_clock_lb = new Label();
//        gameField.getChildren().add(main_clock_lb);
//        main_clock_lb.relocate(450, 30);
//        main_clock_lb.setFont(new Font(30));
//        Thread timerThread = new Thread(() -> {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//            while (seconds>=0) {
//                try {
//                    Thread.sleep(1000); //1 second
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                final String time = String.format("%02d:%02d",  (seconds) / 60, seconds % 60);
//                Platform.runLater(() -> {
//                    main_clock_lb.setText(time);
//                });
//                secondMinus();
//            }
//        });
//        timerThread.setDaemon(true);
//        timerThread.start();
//        animationTimer.start();
//
//
//
//
//    }
//    private double getCenterX(ImageView imageView){
//        return imageView.getLayoutX()+imageView.getFitWidth()/2;
//    }
//    private double getCenterY(ImageView imageView){
//        return imageView.getLayoutY()+imageView.getFitHeight()/2;
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//        LocalDateTime time = LocalDateTime.now();
//        System.out.println(time.getSecond());
//    }
//
//
//
//    private void loadGame(){
//        BackgroundImage myBI=  new BackgroundImage(new Image(getClass().getResource("../Utils/grass.png").toExternalForm()),
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
//                BackgroundSize.DEFAULT);
//        gameField.setBackground(new Background(myBI));
//        for(ImageView imageView: deck){
//            gameField.getChildren().add(imageView);
//            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//
//                }
//            });
//            imageView.setFitHeight(50);
//            imageView.setFitWidth(50);
//        }
//        deck.get(0).relocate(710,910);
//        deck.get(1).relocate(770, 910);
//        deck.get(2).relocate(830, 910);
//        deck.get(0).setImage(images.cannonImage);
//        deck.get(1).setImage(images.cannonImage);
//        deck.get(2).setImage(images.cannonImage);
//        gameField.getChildren().add(exirBar);
//        gameField.getChildren().add(exirLabel);
//        exirBar.relocate(650,970);
//        exirLabel.setText("10");
//        exirLabel.setFont(new Font(12));
//        exirLabel.relocate(950,965);
//    }
//    private void secondMinus(){
//        seconds--;
//    }
//}
