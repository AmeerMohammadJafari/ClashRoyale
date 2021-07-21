package Characters.Spells;

import Characters.BaseCharacter;
import DataModel.Point;
import Utils.Images;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;

public class Arrows extends BaseSpell {
    private double damage;
    private ImageView imageView = new ImageView();
    private Point currentPosition;
    Pane layer;
    private int speed = 5;
    public Arrows(Point currentPosition, Point position, double radius, double damage, List<BaseCharacter> targets, Pane layer, Images images) {
        super(position, radius, targets);
        this.damage = damage;
        imageView.setImage(images.arrowsImage);
        this.layer = layer;
        this.currentPosition = currentPosition;
        addToLayer();
    }
    private void addToLayer(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                layer.getChildren().add(imageView);
                imageView.relocate(currentPosition.getX(), currentPosition.getY());
                imageView.setFitHeight(100);
                imageView.setFitWidth(30);
            }
        });

    }

    @Override
    public boolean update() {
        double x,y;
        double distance = Point.distance(position, currentPosition);
        if(distance<10){
            for(BaseCharacter character: targets){
                if(inrange(character)){
                    character.hitByDamage(damage);
                }
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    layer.getChildren().remove(imageView);
                }
            });
            return false;
        }
        rotateTowardTarget(position);
        Point direction = new Point((position.getX()-currentPosition.getX())/distance, (position.getY() - currentPosition.getY())/distance);
        x=currentPosition.getX()+speed*direction.getX();
        y = currentPosition.getY()+speed*direction.getY();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imageView.relocate(x,y);
            }
        });
        currentPosition = new Point(x,y);
        return true;
    }
    public void rotateTowardTarget(Point point){
        Point center = new Point(getCenterX(), getCenterY());

        double X = point.getX() - center.getX();
        double Y = point.getY() - center.getY();
        double radians = Math.atan2(Y, X);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imageView.setRotate(90+Math.toDegrees(radians));
            }
        });

    }
    public  double getCenterX(){
        return imageView.getLayoutX()+imageView.getFitWidth()/2;
    }
    public  double getCenterY(){
        return imageView.getLayoutY()+imageView.getFitHeight()/2;
    }
    @Override
    public boolean inrange(BaseCharacter target) {
        if(Point.distance(new Point(target.getCenterX(), target.getCenterY(), target.r), new Point(getCenterX(), getCenterY()))<=radius){

            return true;
        }
        return false;
    }
}
