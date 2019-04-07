package models.figures;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import models.connectors.Connectors;
import views.Observer;

import java.util.ArrayList;

public class TextEllipse extends Ellipse implements Figures {

   private    double x,y,x1,y1,centerX, centerY, radiusX, radiusY;
    //A2(x,y),B2(x2,y2) vertices of the to
    private Color color;
    private int layer;

    Observer observer;

    public TextEllipse(double x, double y, double x1, double y1){

        this.centerX = (x + x1)/2;
        this.centerY = (y + y1)/2;
        this.radiusX =  Math.abs((x1 - x)/2);
        this.radiusY = Math.abs((y1 - y))/2;
        this.x = -radiusX + centerX;
        this.y = centerY;
        this.x1 = centerX;
        this.y1 = -radiusY + centerY;
        this.color = Color.GREENYELLOW;

    }


     @Override
     public void setFigParams() {

      setFill(color);
      setStroke(color);
      setCenterX(centerX);
      setCenterY(centerY);
      setRadiusX(radiusX);
      setRadiusY(radiusY);

     }

    @Override
    public void recalculateFigParams(double newX, double newY, double newH, double newW) {
        centerX = newX;
        centerY = newY;
        radiusX = newW/2;
        radiusY = newH/2;
        x = centerX - radiusX;
        y = centerY;
        x1 = centerX;
        y1 = -radiusY + centerY;

        /*x =newX;
        y = newY;
        radiusX = newW/2;
        radiusY = newH/2;
        centerX = x + radiusX;
        centerY = y;
        x1 = centerX;
        y1 = -radiusY + centerY;
        */
    }


    @Override
    public boolean isMouseInside(double mouseX, double mouseY) {
     if(this.contains(mouseX,mouseY))
      return true;
     else
      return false;
    }



    @Override
    public double getFigX() {
        return x;
    }

    @Override
    public double getFigY() {
        return y;
    }

    @Override
    public void setFigX(double figX) {

    }

    @Override
    public void setFigY(double figY) {

    }

    @Override
    public double getFigW() {
        return radiusX*2;
    }

    @Override
    public void setFigW(double figW) {

    }

    @Override
    public double getFigH() {
        return radiusY*2;
    }

    @Override
    public void setFigH(double figH) {

    }

    @Override
    public double getFigX1() {
        return x1;
    }

    @Override
    public double getFigY1() {
        return y1;
    }

    @Override
    public void setFigX1(double figX1) {

    }

    @Override
    public void setFigY1(double figY1) {

    }

   @Override
   public Color getFigColor() {
    return color;
   }

   @Override
   public void setFigColor(Color color) {
       this.color = color;

   }

    @Override
    public void setText(Text text) {

    }

    public void recalculateSize(double zoomFactor){
        radiusY = radiusY* zoomFactor;
        radiusX = radiusX* zoomFactor;
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
    public int getType() {
        return 2;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
        notifyChanges();
    }

   public void notifyChanges(){ observer.update(); }


 }
