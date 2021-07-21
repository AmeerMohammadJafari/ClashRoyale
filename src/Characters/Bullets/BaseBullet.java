package Characters.Bullets;

import DataModel.Point;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class BaseBullet {
    Image image;
    public ImageView imageView = new ImageView();
    Pane layer;
    Point source;
    Point destination;

    public BaseBullet(Image image, Pane layer, Point source) {
        this.image = image;
        this.imageView.setImage(image);
        this.layer = layer;
        this.source = source;
        addToLayer();
    }

    public void rotateTowardTarget(Point point){
        Point center = new Point(getCenterX(), getCenterY());

        double X = point.getX() - center.getX();
        double Y = point.getY() - center.getY();
        double radians = Math.atan2(Y, X);
        imageView.setRotate(90+Math.toDegrees(radians));
    }
    public  double getCenterX(){
        return imageView.getLayoutX()+imageView.getFitWidth()/2;
    }
    public  double getCenterY(){
        return imageView.getLayoutY()+imageView.getFitHeight()/2;
    }


    public void addToLayer(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                layer.getChildren().add(imageView);
            }
        });


    }
    public abstract boolean update();
    public abstract void move();
}
