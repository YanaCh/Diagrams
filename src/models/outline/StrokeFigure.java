package models.outline;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import models.figures.*;

public class StrokeFigure extends StrokeShape {
    private Rectangle rectangle;
    private double x2,y2,x3,y3,w,h;
    private Circle circle2, circle3;

    public StrokeFigure(Figures figure) {
        super(figure);
        rectangle = new Rectangle();
        super.shape = rectangle;
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
        super.setParams();
        setCircle(circle2,x2,y2);
        setCircle(circle3,x3,y3);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        rectangle.setWidth(w);
        rectangle.setHeight(h);
    }

    @Override
    public void joinParamsInComposition() {
        super.joinParamsInComposition();
        getChildren().addAll(circle2,circle3);
        notifyChanges();
    }

    @Override
    public void changeParams(double x, double y, double h, double w) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        calculateParams();
    }

    private void calculateParams(){
        x1 = x + w;
        y1 = y;
        x2 = x1;
        y2 = y1 + h;
        x3 = x;
        y3 = y2;
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
        if(figure instanceof TextRect || figure instanceof ImageRect || figure instanceof CustomTextArea) {
            x = figure.getFigX();
            y = figure.getFigY();
            w = figure.getFigW();
            h = figure.getFigH();
        }

    }


}
