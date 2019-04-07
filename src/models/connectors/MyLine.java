package models.connectors;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import models.figures.Figures;
import views.Observer;

public class MyLine extends Line implements Connectors {

    private double x, y, x1, y1;
    private Figures from, to; // 2 recs, from one to another
    private Color color;
    private Point2D vecTo;
    private Point2D vecFrom;
    private Text text;
    private int layer;

    private Observer observer;

    public MyLine(double x, double y, double x1, double y1){
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
    }

    public MyLine(double x, double y, double x1, double y1, Figures from, Figures to){

        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;

        this.from = from;
        this.to = to;

        vecFrom = new Point2D(from.getCenterX()- x, from.getCenterY() - y);
        vecTo = new Point2D(to.getCenterX()- x1, to.getCenterY() - y1);

    }


    public void setConParams(){

        setStrokeWidth(3);
        setStartX(x);
        setStartY(y);
        setEndX(x1);
        setEndY(y1);

        try {

            setStroke(color);
        }
        catch (NullPointerException e){

            System.out.println(e + "Set connectors color!");
            setStroke(Color.TEAL);

        }

        try {
            text.setX((x + x1 - text.getLayoutBounds().getWidth()*2)/2);
            text.setY((y + y1 - text.getLayoutBounds().getHeight())/2);
        }

        catch (NullPointerException e){

            System.out.println(e + "Set connectors text!");
            text = new Text("");

        }
    }


    public void recalculate(){
        x = from.getCenterX() - vecFrom.getX();
        y = from.getCenterY() - vecFrom.getY();
        x1 = to.getCenterX() - vecTo.getX();
        y1 = to.getCenterY() - vecTo.getY();
    }


    public void setColor(Color color) {
        this.color = color;

    }

    public void setText(Text text){
        this.text = text;
    }

    @Override
    public boolean isMouseInside(double mouseX, double mouseY) {
        if(this.contains(mouseX,mouseY))
            return true;
        else
            return false;
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void addShape(Pane canvas) {
        canvas.getChildren().add(this);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;

    }

    @Override
    public double getX1() {
        return x1;
    }

    @Override
    public double getY1() {
        return y1;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
        notifyChanges();
    }

    @Override
    public int getType() {
        return 1;
    }

    private void notifyChanges(){
        observer.update();
    }
}

