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

public class Giant extends BasePlayer {
    public Giant(Images image, Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double range, Teams teamType, List<BaseTower> towers, double delay, List<BaseBullet> bullets, List<BaseCharacter> opponents) {
        super(image, layer, x, y, r, dx, dy, dr, health, damage, range, teamType, towers, delay,bullets, opponents);
        imageView.setImage(image.giantImage);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        addToLayer();
    }

    @Override
    public boolean hitTarget() {
        if(target==null){
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
        BaseTower target = closestTower();
        if(target!=null){
            setTarget(target);
        }

    }

    @Override
    public void move() {
        if(target!=null){
            rotateTowardTarget(target);
            double distance = getDistance(target);
            if(distance<80){
                return;
            }
            double dVectorX = (target.getCenterX() - getCenterX())/distance * dx;
            double dVectorY = (target.getCenterY()-getCenterY())/distance * dy;
            x+=dVectorX;
            y+=dVectorY;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    imageView.relocate(x,y);
                }
            });
        }
    }

    @Override
    public void checkTarget() {
        if( target == null) {
            return;
        }


        if( !target.isAlive() || target.isRemovable()) {
            setTarget( null);
            return;
        }
    }
}
