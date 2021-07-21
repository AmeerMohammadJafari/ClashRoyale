package Characters.players;

import Characters.BaseCharacter;
import Characters.BasePlayer;
import Characters.Bullets.BaseBullet;
import Characters.Bullets.ShortBullet;
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

public class Barbarian extends BasePlayer {
    public Barbarian(Images image, Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double range, Teams teamType, List<BaseTower> towers, double delay, List<BaseBullet> bullets, List<BaseCharacter> opponents) {
        super(image, layer, x, y, r, dx, dy, dr, health, damage, range, teamType, towers, delay, bullets, opponents);
        imageView.setImage(image.barbarianImage);
        imageView.setFitHeight(60);
        imageView.setFitWidth(40);
        addToLayer();

    }

    public boolean hitTarget(){
        if(this.target==null){
            return false;
        }
        if(inRange(target)){
            if(nextHit==null){
                nextHit = LocalDateTime.now().plusNanos((long) (Math.pow(10,9) * delay));
                bulletList.add(new ShortBullet(image.shortBulletImage, layer, new Point(getCenterX(), getCenterY()), target, damage));
                return true;
            }else if(LocalDateTime.now().compareTo(nextHit)>=0){
                nextHit = LocalDateTime.now().plusNanos((long)(delay*Math.pow(10,9)));
                bulletList.add(new ShortBullet(image.shortBulletImage, layer, new Point(getCenterX(), getCenterY()), target, damage));
                return true;
            }
        }
        return false;
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
}
