package models.outline;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import models.figures.Figure;
import views.Observer;

public class ConOutline extends Line implements OutlineInterface {
    private Figure connector;
    private Circle circle,circle1;
    private double x,y,x1,y1;
    private Group group;

    public ConOutline(Figure connector){
        this.connector = connector;
        group = new Group();
    }

    @Override
    public void initialize() {
        x = connector.getFigX();
        y = connector.getFigY();
        x1 = connector.getFigX1();
        y1 = connector.getFigY1();

        circle = new Circle();
        circle1 = new Circle();

    }

    @Override
    public void setParams() {
        setCircle(circle,x,y);
        setCircle(circle1,x1,y1);
        this.setStartX(x);
        this.setStartY(y);
        this.setEndX(x1);
        this.setEndY(y1);
        this.setStroke(Color.BLACK);
        this.getStrokeDashArray().addAll(8d, 8d);

    }

    @Override
    public void joinParamsInComposition() {

        group.getChildren().addAll(this,circle,circle1);

    }

    @Override
    public Group getComposition() {
        return group;
    }

    @Override
    public void changeParams(double x, double y, double h, double w) {

    }

    @Override
    public void registerObserver(Observer observer) {

    }

    private void setCircle(Circle circle, double centerX, double centerY){
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        circle.setRadius(5);
        circle.setFill(Color.BLUE);
        circle.setStroke(Color.BLUE);

    }
}
