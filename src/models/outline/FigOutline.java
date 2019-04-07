package models.outline;

import controllers.ControllerImpl;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import models.figures.Figures;
import models.figures.ImageRect;
import models.figures.TextEllipse;
import models.figures.TextRect;
import views.Observer;

public class FigOutline extends Rectangle implements OutlineInterface {
    private Figures figure;
    private Circle circle,circle1, circle2, circle3;
    private double x,y,x1,y1,x2,y2,x3,y3, w, h;
    private Group group;
    private ControllerImpl controller;
    private Observer observer;

    public FigOutline(Figures figure){
        this.figure = figure;
        controller = ControllerImpl.getInstance();
        group = new Group();

    }


    @Override
    public void initialize() {
        initForEllipse();
        initForRectangle();
        calculateParams();

        circle = new Circle();
        circle1 = new Circle();
        circle2 = new Circle();
        circle3 = new Circle();

    }

    @Override
    public void setParams() {
        setCircle(circle,x,y);
        setCircle(circle1,x1,y1);
        setCircle(circle2,x2,y2);
        setCircle(circle3,x3,y3);
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.BLACK);
        this.getStrokeDashArray().addAll(8d, 8d);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setWidth(w);
        this.setHeight(h);
    }

    @Override
    public void joinParamsInComposition() {

    group.getChildren().addAll(this, circle,circle1,circle2,circle3);
    notifyChanges();

    }

    @Override
    public Group getComposition() {
        return group;
    }

    @Override
    public void changeParams(double x, double y, double h, double w) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        calculateParams();
        setParams();
        if(figure instanceof TextRect || figure instanceof ImageRect)
            controller.changeParams(figure,x,y,h,w);
        if(figure instanceof TextEllipse)
            //controller.changeParams(figure,(x+x2)/2,(y+y2)/2,h,w);
            controller.changeParams(figure,(x+x2)/2 - w/2,(y+y2)/2,h,w);

    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    private void calculateParams(){
        x1 = x + w;
        y1 = y;
        x2 = x1;
        y2 = y1 + h;
        x3 = x;
        y3 = y2;
    }

    private void setCircle(Circle circle, double centerX, double centerY){
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        circle.setRadius(5);
        circle.setFill(Color.BLUE);
        circle.setStroke(Color.BLUE);

    }

    private void initForEllipse(){
        if(figure instanceof TextEllipse){
            x = figure.getCenterX() - figure.getFigW()/2;
            y = figure.getCenterY() - figure.getFigH()/2;
            w = figure.getFigW();
            h = figure.getFigH();

        }

    }

    private void initForRectangle(){
        if(figure instanceof TextRect || figure instanceof ImageRect) {
            x = figure.getFigX();
            y = figure.getFigY();
            w = figure.getFigW();
            h = figure.getFigH();
        }

    }

    private void notifyChanges(){ observer.update(); }
}
