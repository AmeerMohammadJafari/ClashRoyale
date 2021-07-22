package Characters.Towers;

import Characters.BaseCharacter;
import Characters.Bullets.BaseBullet;
import Characters.Teams;
import Utils.Bar;
import Utils.Images;
import Utils.Levels;
import Utils.Setting;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.util.List;

public class MainTower extends BaseTower {
    Bar hp;
    Levels level;
    double startHealth;
    public MainTower(Images image, Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double range, Teams teamtype, double delay, Levels level, List<BaseBullet> bulletList, List<BaseCharacter> opponents, List<BaseTower> towers) {
        super(image, layer, x, y, r, dx, dy, dr, health, damage, range, teamtype, delay,bulletList,opponents,towers);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imageView = new ImageView(image.towerImage);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
            }
        });

        hp = new Bar(Color.WHITE, Color.RED);
        addToLayer();
        this.level = level;
        this.startHealth = health;
    }
    @Override
    public void checkTarget() {
        if( target == null) {
            return;
        }

        if( target.health<=0 || target.isRemovable()) {
            setTarget( null);
            nextHit=null;
            return;
        }


        double distanceTotal = getDistance(target);

        if( distanceTotal>range) {

            setTarget( null);
            nextHit=null;
        }
    }



    @Override
    public void findTarget(List<? extends BaseCharacter> targetList) {
        BaseCharacter closestTarget = null;
        double closestDistance = 0.0;
        for(BaseCharacter character: targetList){
            if(character.r>0){
                continue;
            }
            if(closestTarget==null){
                closestTarget=character;
                closestDistance = getDistance(character);
            }else if(closestDistance>getDistance(character)){
                closestTarget = character;
                closestDistance = getDistance(character);
            }
        }
        for(BaseTower tower: towers){
            if(closestTarget==null && inRange(tower)){
                closestTarget=tower;
                closestDistance = getDistance(tower);
            }else if(closestDistance>getDistance(tower) && inRange(tower)){
                closestTarget = tower;
                closestDistance = getDistance(tower);
            }
        }
        setTarget(closestTarget);
    }




    @Override
    public void updateUI() {
        hitTarget();
        hp.setValue( this.health/ startHealth);
    }





    @Override
    public boolean isRemovable() {
        return this.health<=0;
    }

    @Override
    public void addToLayer() {
        super.addToLayer();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                layer.getChildren().add(hp);
                hp.relocate(x + 20, getCenterY()-imageView.getFitHeight()/2-20);
            }
        });


    }

    @Override
    public void removeFromLayer() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                layer.getChildren().remove(imageView);
                layer.getChildren().remove(hp);
            }
        });
    }
}
