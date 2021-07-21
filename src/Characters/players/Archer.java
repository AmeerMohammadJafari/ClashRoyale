package Characters.players;

import Characters.BaseCharacter;
import Characters.BasePlayer;
import Characters.Bullets.BaseBullet;
import Characters.Bullets.LongBullet;
import Characters.Teams;
import Characters.Towers.BaseTower;
import Characters.Towers.MainTower;
import DataModel.Point;
import Utils.Images;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.time.LocalDateTime;
import java.util.List;

public class Archer extends BasePlayer {
    public Archer(Images image, Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double range, Teams teamType, List<BaseTower> towers, double delay, List<BaseBullet> bullets, List<BaseCharacter> opponents) {
        super(image, layer, x, y, r, dx, dy, dr, health, damage, range, teamType, towers, delay, bullets, opponents);
        imageView.setImage(image.archerImage);
        addToLayer();
    }

    @Override
    public void addToLayer() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                layer.getChildren().add(imageView);
                imageView.relocate(x,y);
                imageView.setFitHeight(40);
                imageView.setFitWidth(40);
                setted = true;
            }
        });
    }
    public boolean hitTarget(){
        if(this.target==null){
            return false;
        }
        if(inRange(target)){
            if(nextHit==null){
                nextHit = LocalDateTime.now().plusNanos((long) (Math.pow(10,9) * delay));
                bulletList.add(new LongBullet(image.shortBulletImage, layer, new Point(getCenterX(), getCenterY()), target, damage));
                return true;
            }else if(LocalDateTime.now().compareTo(nextHit)>=0){
                nextHit = LocalDateTime.now().plusNanos((long)(delay*Math.pow(10,9)));
                bulletList.add(new LongBullet(image.shortBulletImage, layer, new Point(getCenterX(), getCenterY()), target, damage));
                return true;
            }
        }

        return false;
    }
}
