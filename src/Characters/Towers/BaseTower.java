package Characters.Towers;

import Characters.BaseCharacter;
import Characters.Bullets.BaseBullet;
import Characters.Bullets.ShortBullet;
import Characters.Teams;
import DataModel.Point;
import Utils.Images;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.time.LocalDateTime;
import java.util.List;

public abstract class BaseTower extends BaseCharacter {
    public LocalDateTime nextHit = null;
    public List<BaseBullet> bulletList;
    public List<BaseCharacter> opponents;
    public List<BaseTower> towers;
    public BaseTower(Images image, Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double range, Teams teamType, double delay, List<BaseBullet> bulletList, List<BaseCharacter> opponents,List<BaseTower> towers) {
        super(image, layer, x, y, r, dx, dy, dr, health, damage, range, teamType, delay);
        this.bulletList = bulletList;
        this.opponents = opponents;
        this.towers = towers;
    }

    public void addToLayer(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                layer.getChildren().add(imageView);
                imageView.relocate(x,y);
                setted = true;
            }
        });

    }
    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }
    public void setRemovable(boolean removable) {
        this.removable = removable;
    }
    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }


    public abstract void checkTarget();
    public abstract void findTarget( List<? extends BaseCharacter> targetList);
    public abstract void updateUI();


    public void checkRemovability(){
        if(this.health<=0){
            setRemovable(true);
            setTarget(null);
        }
    }
    public boolean hitTarget(){
        if(this.target==null || !inRange(target)){
            return false;
        }
        if(nextHit==null){
            nextHit = LocalDateTime.now().plusNanos((long) (Math.pow(10,9) * delay));
            bulletList.add(new ShortBullet(image.shortBulletImage, layer, new Point(getCenterX(), getCenterY()), target, damage));
            return true;
        }else if(LocalDateTime.now().compareTo(nextHit)>=0){
            nextHit = nextHit.plusNanos((long)(delay*Math.pow(10,9)));
            bulletList.add(new ShortBullet(image.shortBulletImage, layer, new Point(getCenterX(), getCenterY()), target, damage));
            return true;
        }
        return false;
    }
    public void getDamagedBy(BaseCharacter character){
        this.health-=character.damage;
    }
    public void setTarget(BaseCharacter character){
        this.target = character;
    }


}
