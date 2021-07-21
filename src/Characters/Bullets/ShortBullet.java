package Characters.Bullets;

import Characters.BaseCharacter;
import DataModel.Point;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class ShortBullet extends BaseBullet{
    BaseCharacter target;
    double speed;
    double damage;
    Point currentPosition;
    public ShortBullet(Image image, Pane layer, Point source, BaseCharacter target, double damage) {
        super(image, layer, source);

        this.destination = new Point(target.getCenterX(), target.getCenterY(), target.r);
        this.target = target;
        this.damage = damage;
        this.currentPosition = source;
        speed=5;
        imageView.setFitHeight(16);
        imageView.setFitWidth(8);
    }

    @Override
    public boolean update() {
        if(Point.distance(currentPosition, destination)<10){
            target.hitByDamage(damage);
            layer.getChildren().remove(imageView);
            return false;
        }
        return true;
    }

    @Override
    public void move() {
        double x,y;
        double distance = Point.distance(destination, currentPosition);
        if(distance<10){
            return;
        }
        rotateTowardTarget(destination);
        Point direction = new Point((destination.getX()-currentPosition.getX())/distance, (destination.getY() - currentPosition.getY())/distance);
        x=currentPosition.getX()+speed*direction.getX();
        y = currentPosition.getY()+speed*direction.getY();
        imageView.relocate(x,y);
        currentPosition = new Point(x,y);
    }

}
