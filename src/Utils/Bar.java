package Utils;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Bar extends Pane {

    Rectangle outerHealthRect;
    Rectangle innerHealthRect;

    public Bar(Color outer, Color inner) {

        double height = 10;

        double outerWidth = 60;
        double innerWidth = 60;

        double x=0.0;
        double y=0.0;

        outerHealthRect = new Rectangle( x, y, outerWidth, height);
//        outerHealthRect.setStroke(Color.BLACK);
        outerHealthRect.setStrokeWidth(2);
        outerHealthRect.setStrokeType( StrokeType.OUTSIDE);
        outerHealthRect.setFill(outer);

        innerHealthRect = new Rectangle( x, y, innerWidth, height);
        innerHealthRect.setStrokeType( StrokeType.OUTSIDE);
        innerHealthRect.setFill(inner);

        getChildren().addAll( outerHealthRect, innerHealthRect);

    }
    public Bar(Color outer, Color inner, int width){
        double height = 10;

        double outerWidth = width;
        double innerWidth = width;

        double x=0.0;
        double y=0.0;

        outerHealthRect = new Rectangle( x, y, outerWidth, height);
//        outerHealthRect.setStroke(Color.BLACK);
        outerHealthRect.setStrokeWidth(2);
        outerHealthRect.setStrokeType( StrokeType.OUTSIDE);
        outerHealthRect.setFill(outer);

        innerHealthRect = new Rectangle( x, y, innerWidth, height);
        innerHealthRect.setStrokeType( StrokeType.OUTSIDE);
        innerHealthRect.setFill(inner);

        getChildren().addAll( outerHealthRect, innerHealthRect);
    }

    public void setValue( double value) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                innerHealthRect.setWidth( outerHealthRect.getWidth() * value);
            }
        });
    }


}