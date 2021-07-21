package Game;

import Characters.BaseCharacter;
import Characters.BasePlayer;
import Characters.Bullets.BaseBullet;
import Characters.Spells.Arrows;
import Characters.Spells.BaseSpell;
import Characters.Spells.FireBall;
import Characters.Spells.Rage;
import Characters.Teams;
import Characters.Towers.BaseTower;
import Characters.Towers.Cannon;
import Characters.Towers.InfernoTower;
import Characters.Towers.MainTower;
import Characters.players.*;
import DataModel.Point;
import Utils.Bar;
import Utils.Images;
import Utils.Levels;
import Utils.Setting;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Deck;
import sample.Main;
import sample.User;

import java.util.*;

public class Game {

    private List<Deck> cardDeck = new ArrayList<>();
    private List<Deck> botDeck = new ArrayList<>();
    private List<Deck> inDeck = new ArrayList<>();
    private Stage primaryStage;
    private AnchorPane gameField = new AnchorPane();
    private Group root = new Group();
    private int seconds = 3*60;
    private Label main_clock_lb = new Label();
    private Bar exirBar = new Bar(Color.WHITE, Color.PURPLE, 300);
    private Label exirLabel = new Label();
    private ImageView selectedImageView;
    private int selectedDeck;
    private Images images = new Images();

    List<ImageView> deck = new ArrayList<>(){{
        add(new ImageView());
        add(new ImageView());
        add(new ImageView());
    }};
    HashMap<Deck, Integer> exirCost = new HashMap<>(){{
       put(Deck.Archer, 3);
       put(Deck.Barbarian, 5);
       put(Deck.BabyDragon, 4);
       put(Deck.Wizard, 5);
       put(Deck.MiniPekka, 4);
       put(Deck.Giant, 5);
       put(Deck.Valkyrie, 4);
       put(Deck.Rage, 3);
       put(Deck.Fireball, 4);
       put(Deck.Arrows, 3);
       put(Deck.Cannon, 6);
       put(Deck.InfernoTower, 5);

    }};

    private List<BaseTower> playerTowers = new ArrayList<>();
    private List<BasePlayer> playerPlayers = new ArrayList<>();
    private List<BaseBullet> playerBullets = new ArrayList<>();
    private List<BaseCharacter> playerCharacters = new ArrayList<>();
    private List<BaseSpell> playerSpells = new ArrayList<>();
    private volatile Double playerExir = 10.0;

    private List<BaseTower> botTowers = new ArrayList<>();
    private List<BasePlayer> botPlayers = new ArrayList<>();
    private List<BaseBullet> botBullets = new ArrayList<>();
    private List<BaseCharacter> botCharacters = new ArrayList<>();
    private List<BaseSpell> botSpells = new ArrayList<>();
    private volatile Double botExir= 10.0;
    private BaseTower playerKing = null;
    private BaseTower botKing = null;
    private String winner = "";
    private User user;
    private LinkedHashMap<Boolean,String> battleHistory;
    private Thread timerThread;

    private Levels level;

    public Game(User user, Stage stage){
        cardDeck.addAll(user.getDecks());
        botDeck.addAll(cardDeck);
        this.primaryStage = stage;
        level = setLevel(user.getLevel());
        this.user = user;
        this.battleHistory = user.getBattleHistory();
    }
    public void start(){

       loadGame();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(checkEnd()){
                    if(winner.equals("bot")){
                        battleHistory.put(false,"Bot");
                    }else if(winner.equals("player")){
                        battleHistory.put(true,user.getUsername());
                    }
                    User.rewriteUser(user);
                    Main.changeScene("MainMenu.fxml");
                    this.stop();
                    return;
                }
               botPlayers.forEach(p->{
                   if(p.setted){
                       p.checkTarget();
                       p.findTarget(playerPlayers);
                       p.move();
                       p.hitTarget();
                   }
               });
                playerPlayers.forEach(p->{
                    if(p.setted){
                        p.checkTarget();
                        p.findTarget(botPlayers);
                        p.move();
                        p.hitTarget();
                    }
                });

               for(int i=0; i<botBullets.size(); i++){
                   botBullets.get(i).move();
                   if(!botBullets.get(i).update()){
                       botBullets.remove(botBullets.get(i));
                   }
               }
                for(int i=0; i<playerBullets.size(); i++){
                    playerBullets.get(i).move();
                    if(!playerBullets.get(i).update()){
                        playerBullets.remove(playerBullets.get(i));
                    }
                }

               playerTowers.forEach(t->{
                   t.updateUI();
               });
                botTowers.forEach(t->{
                    t.updateUI();
                });
                for(int i=0; i<playerTowers.size(); i++){
                    if(playerTowers.get(i).isRemovable()){
                        playerTowers.get(i).removeFromLayer();
                        playerTowers.remove(playerTowers.get(i));
                    }
                }
                for(int i=0; i<botTowers.size(); i++){
                    if(botTowers.get(i).isRemovable()){
                        botTowers.get(i).removeFromLayer();
                        botTowers.remove(botTowers.get(i));
                    }
                }
                for(int i=0; i<playerPlayers.size(); i++){
                    if(playerPlayers.get(i).isRemovable()){
                        playerPlayers.get(i).removeFromLayer();
                        playerCharacters.remove(playerPlayers.get(i));
                        playerPlayers.remove(playerPlayers.get(i));
                    }
                }
                for(int i=0; i<botPlayers.size(); i++){
                    if(botPlayers.get(i).isRemovable()){
                        botPlayers.get(i).removeFromLayer();
                        botCharacters.remove(botPlayers.get(i));
                        botPlayers.remove(botPlayers.get(i));
                    }
                }
                for(int i=0; i<playerTowers.size(); i++){
                    if(playerTowers.get(i).setted){
                        playerTowers.get(i).findTarget(botCharacters);
                        playerTowers.get(i).hitTarget();
                        playerTowers.get(i).checkTarget();
                    }
                }
                for(int i=0; i<botTowers.size(); i++){
                    if(botTowers.get(i).setted){
                        botTowers.get(i).findTarget(playerCharacters);
                        botTowers.get(i).hitTarget();
                        botTowers.get(i).checkTarget();
                    }
                }

                for(int i=0; i<playerSpells.size(); i++){
                    if(!playerSpells.get(i).update()){
                        playerSpells.remove(playerSpells.get(i));
                    }
                }
                for(int i=0; i<botSpells.size(); i++){
                    if(!botSpells.get(i).update()){
                        botSpells.remove(botSpells.get(i));
                    }
                }
                exirBar.setValue(playerExir/10);
                exirLabel.setText(String.valueOf(playerExir));


            }
        };
        createCharacter(new Point(800,700), Teams.BLUE, Deck.Barbarian);
        createCharacter(new Point(800,110), Teams.RED, Deck.Giant);
//        createCharacter(new Point(600,500), Teams.BLUE, Deck.Cannon);


        gameLoop.start();
        gameField.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!deck.contains(mouseEvent.getPickResult().getIntersectedNode())){
                    if(selectedImageView!=null){
                        createPlayer(new Point(mouseEvent.getX(), mouseEvent.getY()));
                    }
                }
            }
        });


    }

    private void loadGame(){

        primaryStage.setTitle("Battle");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.setResizable(false);
        primaryStage.show();
        gameField.setPrefSize(1024,1000);
        gameField.relocate(0,0);
        root.getChildren().add(gameField);

        BackgroundImage myBI=  new BackgroundImage(new Image(getClass().getResource("../Utils/grass.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        gameField.setBackground(new Background(myBI));

        gameField.getChildren().add(main_clock_lb);
        main_clock_lb.relocate(450, 30);
        main_clock_lb.setFont(new Font(30));
        timerThread = new Thread(() -> {
            while (seconds>=0) {
                try {
                    Thread.sleep(1000); //1 second
                    if(!winner.equals("")){
                        break;
                    }
                    if(playerExir<10){
                        synchronized (playerExir){
                            playerExir+=0.5;
                        }
                    }
                    if(botExir<10){
                        synchronized (botExir){
                            botExir+=0.1;
                        }
                    }
                    createBot();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String time = String.format("%02d:%02d",  (seconds) / 60, seconds % 60);
                Platform.runLater(() -> {
                    main_clock_lb.setText(time);
                });
                secondMinus();
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();


        for(ImageView imageView: deck){
            gameField.getChildren().add(imageView);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedImageView = imageView;
                    selectedDeck = deck.indexOf(imageView);

                }
            });
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
        }

        deck.get(0).relocate(710,910);
        deck.get(1).relocate(770, 910);
        deck.get(2).relocate(830, 910);

        for(int i=0; i<3; i++){
            deck.get(i).setImage(Deck.deckToImage(cardDeck.get(i)));
            inDeck.add(cardDeck.get(i));
        }
        gameField.getChildren().add(exirBar);
        gameField.getChildren().add(exirLabel);
        exirBar.relocate(650,970);
        exirLabel.setText("10");
        exirLabel.setFont(new Font(12));
        exirLabel.relocate(953,965);
        createTowers();

    }

    private void secondMinus(){
        seconds--;
    }
    private void replaceDeck(int num){
        Collections.shuffle(cardDeck);
        for(int i=0; i<cardDeck.size(); i++){
            if(!inDeck.contains(cardDeck.get(i))){
                inDeck.set(num, cardDeck.get(i));
                selectedImageView.setImage(Deck.deckToImage(cardDeck.get(i)));
            }
        }
    }

    private void createPlayer(Point position){
        if(exirCost.get(inDeck.get(selectedDeck))<=playerExir){
            synchronized (botExir){
                playerExir-=exirCost.get(inDeck.get(selectedDeck));
            }
            exirBar.setValue(playerExir/10);
            exirLabel.setText(String.valueOf(playerExir));
            createCharacter(position, Teams.BLUE, inDeck.get(selectedDeck));
            replaceDeck(selectedDeck);
            selectedImageView = null;

        }
    }
    private void createBot(){
        Collections.shuffle(botDeck);
        Random random = new Random();
        int[] temp = {random.nextInt(200)+600,random.nextInt(200)};

        for(Deck deck : botDeck){
            if(exirCost.get(deck)<=botExir && checkInstanceOfPlayer(deck)){
                Point tempPoint = new Point(temp[random.nextInt(2)],100);
                for(BaseCharacter character : botCharacters){
                    if(Point.distance(tempPoint, new Point(character.getCenterX(), character.getCenterY()))<100){
                        return;
                    }
                }
                createCharacter(tempPoint,Teams.RED, deck);
                synchronized (botExir){
                    botExir-=exirCost.get(deck);
                }
                System.out.println(deck);
                return;
            }
        }
    }

    
    private void createCharacter(Point position, Teams team, Deck deck){
        if(deck==Deck.Archer || deck==Deck.Barbarian || deck==Deck.BabyDragon || deck==Deck.Giant || deck==Deck.MiniPekka || deck == Deck.Valkyrie || deck == Deck.Wizard){
            createPlayerExtends(position, team, deck);
        }else if(deck==Deck.Arrows || deck == Deck.Fireball || deck == Deck.Rage){
            createSpellExtends(position, team, deck);
        }else if(deck==Deck.InfernoTower || deck==Deck.Cannon){
            createTowerExtends(position, team, deck);
        }
    }
    private void createPlayerExtends(Point position, Teams team, Deck deck){
        List<BaseTower> tempTower = team==Teams.BLUE ? botTowers : playerTowers;
        List<BaseBullet> tempBullets = team== Teams.BLUE ? playerBullets: botBullets;
        List<BaseCharacter> opponents = team==Teams.BLUE ? botCharacters : playerCharacters;
        BasePlayer temp = null;
        switch (deck){
            case Archer -> temp = new Archer(images,gameField, position.getX(), position.getY(),0,1,1,0, Setting.ArcherHp.get(level),Setting.ArcherDamage.get(level),Setting.ArcherRange,team,tempTower,Setting.ArcherDelay,tempBullets,opponents);
            case Barbarian -> temp = new Barbarian(images,gameField, position.getX(), position.getY(),0,0.8,0.8,0, Setting.BarbarianHp.get(level),Setting.BarbarianDamage.get(level),Setting.BarbarianRange,team,tempTower,Setting.BarbarianDelay,tempBullets,opponents);
            case BabyDragon -> temp = new Dragon(images,gameField, position.getX(), position.getY(),40,1,1,0, Setting.DragonHp.get(level),Setting.DragonDamage.get(level),Setting.DragonRange,team,tempTower,Setting.DragonDelay,tempBullets,opponents);
            case Giant -> temp = new Giant(images,gameField, position.getX(), position.getY(),0,0.4,0.4,0, Setting.GiantHp.get(level),Setting.GiantDamage.get(level),Setting.GiantRange,team,tempTower,Setting.GiantDelay,tempBullets,opponents);
            case MiniPekka -> temp = new MiniPekka(images,gameField, position.getX(), position.getY(),0,0.6,0.6,0, Setting.PekkaHp.get(level),Setting.PekkaDamage.get(level),Setting.PekkaRange,team,tempTower,Setting.Pekka,tempBullets,opponents);
            case Valkyrie -> temp = new Valkyrie(images,gameField, position.getX(), position.getY(),0,1,1,0, Setting.ValkyrieHp.get(level),Setting.ValkyrieDamage.get(level),Setting.ValkyrieRange,team,tempTower,Setting.ValkyrieDelay,tempBullets,opponents);
            case Wizard -> temp = new Wizard(images,gameField, position.getX(), position.getY(),0,1,1,0, Setting.WizardHp.get(level),Setting.WizardDamage.get(level),Setting.WizardRange,team,tempTower,Setting.WizardDelay,tempBullets,opponents);
        }
        if(team == Teams.BLUE){
            playerCharacters.add(temp);
            playerPlayers.add(temp);
        }else{
            botCharacters.add(temp);
            botPlayers.add(temp);
        }
    }
    private void createTowerExtends(Point position, Teams team, Deck deck){
        BaseTower temp = null;
        List<BaseTower> tempTower = team==Teams.BLUE ? botTowers : playerTowers;
        List<BaseBullet> tempBullets = team== Teams.BLUE ? playerBullets: botBullets;
        List<BaseCharacter> opponents = team==Teams.BLUE ? botCharacters : playerCharacters;
        switch (deck){
            case Cannon -> temp = new Cannon(images, gameField, position.getX(), position.getY(), 0, 0, 0, 0, Setting.CannonHp.get(level), Setting.CannonDamage.get(level), Setting.CannonRange, team, Setting.CannonDelay,tempBullets,opponents,tempTower);
            case InfernoTower -> temp = new InfernoTower(images, gameField, position.getX(), position.getY(), 0, 0, 0, 0, Setting.InfernoHp.get(level), Setting.InfernoDamage.get(level), Setting.InfernoRange, team, Setting.InfernoDelay,tempBullets,opponents,tempTower);
        }
        if(team == Teams.BLUE){
            playerCharacters.add(temp);
            playerTowers.add(temp);
        }else{
            botCharacters.add(temp);
            botTowers.add(temp);
        }

    }
    private void createSpellExtends(Point position, Teams team, Deck deck){
        BaseSpell temp = null;
        Point currentPosition = team==Teams.BLUE ? new Point(450,800) : new Point(450,110);
        if(team == Teams.BLUE){
            switch (deck){
                case Arrows -> temp = new Arrows(currentPosition,position,Setting.ArrowRadius, Setting.ArrowsDamage.get(level),botCharacters,gameField,images);
                case Fireball -> temp = new FireBall(currentPosition,position,Setting.FireballRadius, Setting.FireballDamage.get(level), botCharacters, gameField, images);
                case Rage -> temp = new Rage(position, 100, Setting.RageDuration.get(level), playerCharacters);
            }
            playerSpells.add(temp);
        }else{
            switch (deck){
                case Arrows -> temp = new Arrows(currentPosition,position,Setting.ArrowRadius, Setting.ArrowsDamage.get(level),playerCharacters,gameField,images);
                //TODO: edit Fireball current position;
                case Fireball -> temp = new FireBall(currentPosition, position,Setting.FireballRadius, Setting.FireballDamage.get(level), playerCharacters, gameField, images);
                case Rage -> temp = new Rage(position, 100, Setting.RageDuration.get(level), botCharacters);
            }
            botSpells.add(temp);
        }

    }

    private Levels setLevel(int l){
        Levels temp = Levels.L1;
        switch (l){
            case 1 -> temp = Levels.L1;
            case 2 -> temp = Levels.L2;
            case 3 -> temp = Levels.L3;
            case 4 -> temp = Levels.L4;
        }
        return temp;
    }

    private void createTowers(){
        botTowers.add(new MainTower(images, gameField,100,200,0,0,0,0,Setting.EdgeTowerHp.get(level),Setting.EdgeTowerDamage.get(level),Setting.EdgeRange,Teams.BLUE,Setting.EdgeDelay,level,botBullets,playerCharacters,playerTowers));
        botKing = new MainTower(images, gameField,450,110,0,0,0,0,Setting.KingHp.get(level),Setting.KingDamage.get(level),Setting.KingRange,Teams.BLUE,Setting.KingDelay,level,botBullets,playerCharacters,playerTowers);
        botTowers.add(botKing);
        botTowers.add(new MainTower(images, gameField, 800,200,0,0,0,0,Setting.EdgeTowerHp.get(level),Setting.EdgeTowerDamage.get(level),Setting.EdgeRange,Teams.BLUE,Setting.EdgeDelay,level,botBullets,playerCharacters,playerTowers));
        playerTowers.add(new MainTower(images, gameField, 100,700,0,0,0,0,Setting.EdgeTowerHp.get(level),Setting.EdgeTowerDamage.get(level),Setting.EdgeRange,Teams.RED,Setting.EdgeDelay,level,playerBullets,botCharacters,botTowers));
        playerKing = new MainTower(images, gameField, 450,800,0,0,0,0,Setting.KingHp.get(level),Setting.KingDamage.get(level),Setting.KingRange,Teams.RED,Setting.KingDelay,level,playerBullets,botCharacters,botTowers);
        playerTowers.add(playerKing);
        playerTowers.add(new MainTower(images, gameField, 800,700,0,0,0,0,Setting.EdgeTowerHp.get(level),Setting.EdgeTowerDamage.get(level),Setting.EdgeRange,Teams.RED,Setting.EdgeDelay,level,playerBullets,botCharacters,botTowers));
        playerCharacters.addAll(playerTowers);
        botCharacters.addAll(botTowers);

    }
    private boolean checkInstanceOfPlayer(Deck deck){
        if(deck!=Deck.Cannon && deck!=Deck.InfernoTower && deck!=Deck.Arrows && deck!=Deck.Fireball && deck!=Deck.Rage){
            return true;
        }
        return false;
    }

    private boolean checkEnd(){
        double playerTotal = 0.0;
        double botTotal = 0.0;
        if(playerKing.health<=0){
            winner = "bot";
            return true;
        }else if(botKing.health<=0){
            winner = "player";
            return true;
        }else if(seconds<=0){
            for(BaseTower tower:playerTowers){
                if(tower instanceof MainTower){
                    playerTotal+=tower.health;
                }
            }
            for(BaseTower tower:botTowers){
                if(tower instanceof MainTower){
                    botTotal+=tower.health;
                }
            }
            if(botTotal>playerTotal){
                winner = "bot";
            }else{
                winner = "player";
            }
            return true;

        }
        return false;
    }

}
