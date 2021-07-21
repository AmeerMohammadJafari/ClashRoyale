package Characters;

import Utils.Images;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class BaseCharacter {
    public boolean setted = false;
    public Images image;
    public ImageView imageView = new ImageView();
    public Pane layer;
    public double y;
    public double x;
    public double r;
    public double dx;
    public double dy;
    public double dr;
    public double health;
    public double damage;
    public double range;
    public double width;
    public double height;
    public Teams teamType;

    public BaseCharacter target = null;

    public boolean removable = false;
    public double delay;
    public BaseCharacter(Images image, Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double range, Teams teamType, double delay) {
        this.image = image;
        this.layer = layer;
        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
        this.dr = dr;
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.teamType = teamType;
        this.delay = delay;
    }
    public abstract void addToLayer();
    public abstract boolean isRemovable();
    public  double getCenterX(){
        return imageView.getLayoutX()+imageView.getFitWidth()/2;
    }
    public  double getCenterY(){
        return imageView.getLayoutY()+imageView.getFitHeight()/2;
    }
    public abstract boolean isAlive();
    public double getDistance(BaseCharacter character){
        return Math.sqrt(Math.pow(Math.abs(character.getCenterX() - this.getCenterX()),2) + Math.pow(Math.abs(character.getCenterY()-this.getCenterY()),2) + Math.pow(Math.abs(character.r - this.r),2));
    }
    public Boolean inRange(BaseCharacter character) {
        return getDistance(character)<=range;
    }
    public void hitByDamage(double damage){
        this.health-=damage;
    }
    public void removeFromLayer() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                layer.getChildren().remove(imageView);
            }
        });
    }
}
