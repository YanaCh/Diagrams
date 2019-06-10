package models.outline;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import models.figures.Figure;
import views.Observer;

public abstract class StrokeShape extends Group {
    private Observer observer;
    protected Figure figure;
    protected Circle circle,circle1;
    protected double x;
    protected double y;
    protected double x1;
    protected double y1;
    protected Shape shape;

    public StrokeShape(Figure figure){
        this.figure = figure;
    }

    protected void setCircle(Circle circle, double centerX, double centerY){
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        circle.setRadius(5);
        circle.setFill(Color.BLUE);
        circle.setStroke(Color.BLUE);
    }

    public void initialize() {
        this.x = figure.getFigX();
        this.y = figure.getFigY();
        this.x1 = figure.getFigX1();
        this.y1 = figure.getFigY1();

        circle = new Circle();
        circle1 = new Circle();

    }

    public void setParams() {
        setCircle(circle,x,y);
        setCircle(circle1,x1,y1);
        shape.setStroke(Color.BLACK);
        shape.getStrokeDashArray().addAll(8d, 8d);
    }

    public void joinParamsInComposition() {
        this.getChildren().addAll(shape,circle,circle1);

    }

    public abstract void changeParams(double x, double y, double h, double w);

    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Shape getShape() {
        return shape;
    }

    public Figure getFigure() {return figure; }

    public void notifyChanges(){ observer.update(); }

}
