package Characters;

import Characters.Bullets.BaseBullet;
import Characters.Towers.BaseTower;
import Characters.Towers.MainTower;
import DataModel.Point;
import Utils.Images;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.time.LocalDateTime;
import java.util.List;

public abstract class BasePlayer extends BaseCharacter {
    public List<BaseTower> towers;
    public LocalDateTime nextHit = null;
    public List<BaseBullet> bulletList;
    public List<BaseCharacter> opponents;

    public int rotation;
    public BasePlayer(Images image, Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double range, Teams teamType, List<BaseTower> towers, double delay, List<BaseBullet> bullets, List<BaseCharacter> opponents) {
        super(image, layer, x, y, r, dx, dy, dr, health, damage, range, teamType, delay);
        this.towers = towers;
        this.bulletList = bullets;
        this.opponents = opponents;
    }

    @Override
    public void addToLayer() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                layer.getChildren().add(imageView);
                imageView.relocate(x, y);
                setted = true;
            }
        });

    }

    public void findTarget(List<? extends BaseCharacter> targetList){

        BaseCharacter closestTarget = null;
        double closestDistance = 0.0;
        for(BaseCharacter character: targetList){
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
    public void move(){
        if(target!=null && inRange(target)) {
            rotateTowardTarget(target);
        }else{
            BaseTower tower = closestTower();
            if(tower!=null){
                rotateTowardTarget(tower);
                double distance = getDistance(tower);
                if(distance<80){
                    return;
                }
                double dVectorX = (tower.getCenterX() - getCenterX())/distance * dx;
                double dVectorY = (tower.getCenterY()-getCenterY())/distance * dy;
                x+=dVectorX;
                y+=dVectorY;
                imageView.relocate(x,y);
            }

        }
    }
    public void checkTarget(){
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
    public void setTarget(BaseCharacter character) {
        this.target = character;
    }
    public BaseTower closestTower(){
        BaseTower result = null;
        for(BaseTower tower: towers){
            if(result==null){
                result = tower;
                continue;
            }
            if(getDistance(result)>getDistance(tower)){
                result = tower;
            }

        }
        return result;
    }
    public void rotateTowardTarget(BaseCharacter target){
        Point center = new Point(getCenterX(), getCenterY());
        Point targetPosition = new Point(target.getCenterX(), target.getCenterY());
        double X = targetPosition.getX() - center.getX();
        double Y = targetPosition.getY() - center.getY();
        double radians = Math.atan2(Y, X);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imageView.setRotate(90+Math.toDegrees(radians));
            }
        });
    }


    @Override
    public boolean isRemovable() {
        return this.health<=0;
    }
    @Override
    public boolean isAlive() {
        return this.health>0;
    }


    public boolean hitTarget(){
        if(this.target==null){
            return false;
        }
        if(nextHit==null){
            nextHit = LocalDateTime.now().plusNanos((long) (Math.pow(10,9) * delay));
            return true;
        }else if(LocalDateTime.now().compareTo(nextHit)>=0){
            nextHit = nextHit.plusNanos((long)(delay*Math.pow(10,9)));
            return true;
        }
        return false;
    }

}
