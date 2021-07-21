package Characters.players;

import Characters.BaseCharacter;
import Characters.BasePlayer;
import Characters.Bullets.BaseBullet;
import Characters.Bullets.BigBullet;
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

public class Dragon extends BasePlayer {
    public Dragon(Images image, Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double range, Teams teamType, List<BaseTower> towers, double delay, List<BaseBullet> bullets, List<BaseCharacter> opponents) {
        super(image, layer, x, y, r, dx, dy, dr, health, damage, range, teamType, towers, delay, bullets, opponents);
        imageView.setImage(image.dragonImage);
        imageView.setFitHeight(50);
        imageView.setFitWidth(60);
        addToLayer();
    }

    @Override
    public void setTarget(BaseCharacter character) {
        super.setTarget(character);
    }

    public boolean hitTarget(){
        if(this.target==null){
            return false;
        }
        if(inRange(target)){
            if(nextHit==null){
                nextHit = LocalDateTime.now().plusNanos((long) (Math.pow(10,9) * delay));
                bulletList.add(new BigBullet(image.bigBullet, layer, new Point(getCenterX(), getCenterY()), new Point(target.getCenterX(), target.getCenterY()), opponents, damage));
                return true;
            }else if(LocalDateTime.now().compareTo(nextHit)>=0){
                nextHit = LocalDateTime.now().plusNanos((long)(delay*Math.pow(10,9)));
                bulletList.add(new BigBullet(image.bigBullet, layer, new Point(getCenterX(), getCenterY()), new Point(target.getCenterX(), target.getCenterY()), opponents, damage));
                return true;
            }
        }

        return false;
    }
}
