package Characters.Towers;

import Characters.BaseCharacter;
import Characters.Bullets.BaseBullet;
import Characters.Teams;
import DataModel.Point;
import Utils.Images;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.lang.management.PlatformLoggingMXBean;
import java.util.List;

public class Cannon extends BaseTower{
    public Cannon(Images image, Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double range, Teams teamType, double delay, List<BaseBullet> bulletList, List<BaseCharacter> opponents, List<BaseTower> towers) {
        super(image, layer, x, y, r, dx, dy, dr, health, damage, range, teamType, delay, bulletList, opponents, towers);
        imageView.setImage(image.cannonImage);
        addToLayer();
        imageView.setFitWidth(30);
        imageView.setFitHeight(60);
    }

    @Override
    public boolean isRemovable() {
        return this.removable;
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


        double distanceTotal = getDistance(target);

        if( Double.compare( distanceTotal, range) > 0) {
            setTarget( null);
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
        if(target!=null){
            rotateTowardTarget(this.target);
            hitTarget();
        }
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



}
