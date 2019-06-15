package models.connectors;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import math.Vector2;
import math.Vector3;
import models.figures.Figure;
import views.Observer;

import java.io.Serializable;

public class MyLine extends Line implements Connectors, Serializable {

    private double x, y, x1, y1;
    private Figure from;
    private Figure to; // 2 recs, from one to another
    private transient Color color;
    private Vector3 colorRGB;
    private  Vector2 vecTo;
    private  Vector2 vecFrom;
    private transient Text text;
    private int layer;
    private int figLayer;

    private transient Observer observer;

    public MyLine(double x, double y, double x1, double y1){
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
    }


    public MyLine(double x, double y, double x1, double y1, Vector2 vecFrom, Vector2 vecTo){

        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;

        this.vecFrom = vecFrom;
        this.vecTo = vecTo;
    }

    public MyLine(double x, double y, double x1, double y1, Figure from, Figure to){

        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;

        this.from = from;
        this.to = to;

        vecFrom = new Vector2(from.getCenterX()- x, from.getCenterY() - y);
        vecTo = new Vector2(to.getCenterX()- x1, to.getCenterY() - y1);

    }

    @Override
    public int getFigLayer() {
        return figLayer;
    }

    public void setFigLayer(int layer){
        this.figLayer = layer;
    }

    public Vector2 getVecTo() {
        return vecTo;
    }

    public Vector2 getVecFrom() {
        return vecFrom;
    }


    public  Vector3 toRGBCode(Color color )
    {
        return new Vector3(color.getRed() * 255,color.getGreen()* 255, color.getBlue()* 255);
    }

    public void setColor(Color color) {
        this.color = color;
        colorRGB = toRGBCode(color);

    }

    public Color getFigColor() {
        return color;
    }

    public Vector3 getColorRGB(){return colorRGB;}

    public void setColorRGB(Vector3 colorRGB){
        this.color = Color.rgb((int) colorRGB.getX(),(int)colorRGB.getY(),(int) colorRGB.getZ());
        this.colorRGB = colorRGB;
        setStroke(color);

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
            //text.setX((x + x1 - text.getLayoutBounds().getWidth()*2)/2);
           // text.setY((y + y1 - text.getLayoutBounds().getHeight())/2);
        }

        catch (NullPointerException e){

            System.out.println(e + "Set connectors text!");
            text = new Text("");

        }
    }

    public String getText(){
        return null;
    }

    public void setTextContent(String textContent){

    }


    public void recalculate(){
        x = from.getCenterX() - vecFrom.getX();
        y = from.getCenterY() - vecFrom.getY();
        x1 = to.getCenterX() - vecTo.getX();
        y1 = to.getCenterY() - vecTo.getY();
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


    public Figure getFrom() {
        return from;
    }

    @Override
    public Vector2 getInterPoint() {
        return null;
    }

    public Figure getTo() {
        return to;
    }


    private void notifyChanges(){
        observer.update();
    }
}

