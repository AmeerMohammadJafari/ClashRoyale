package Characters.Bullets;

import Characters.BaseCharacter;
import DataModel.Point;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.List;

public class BigBullet extends BaseBullet{
    Point destination;
    List<BaseCharacter> target;
    double speed = 4;
    double damage;
    Point currentPosition;
    public BigBullet(Image image, Pane layer, Point source, Point destination,List<BaseCharacter> target, double damage) {
        super(image, layer, source);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        this.destination = destination;
        this.target = target;
        this.damage = damage;
        this.currentPosition = source;
    }

    @Override
    public boolean update() {
        if(Point.distance(currentPosition, destination)<10){
            if(target!=null){
                for(BaseCharacter character: target){
                    if(Point.distance(currentPosition,new Point(character.getCenterX(), character.getCenterY()))<=20){
                        character.hitByDamage(this.damage);
                    }
                }
            }
            layer.getChildren().remove(imageView);
            return false;
        }
        return true;
    }

    @Override
    public void move() {
        double x, y;
        double distance = Point.distance(destination, currentPosition);
        if(distance<10){
            return;
        }
        rotateTowardTarget(destination);
        Point direction = new Point((destination.getX()-currentPosition.getX())/distance, (destination.getY() - currentPosition.getY())/distance);
        x=currentPosition.getX()+speed*direction.getX();
        y = currentPosition.getY()+speed*direction.getY();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imageView.relocate(x,y);
            }
        });
        currentPosition = new Point(x,y);
    }
}
