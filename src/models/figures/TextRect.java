package models.figures;

import controllers.ControllerImpl;
import controllers.CurrentState;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.connectors.Connectors;
import views.Observer;

import java.util.ArrayList;

public class TextRect extends Rectangle implements Figures {

    protected double x,y,x1,y1, w, h, centerX, centerY;
    protected Color color;
    protected Text text;
    private int layer;

    private Observer observer;

    private CustomTextArea customTextArea;
    private CurrentState currentState;
    private ControllerImpl controller;

    public TextRect(double x, double y, double x1, double y1){

        this.h = Math.abs(y - y1);
        this.w = Math.abs(x - x1);
        this.x = Math.min(x1, x);
        this.y = Math.min(y1, y);
        this.x1 = x+w;
        this.y1 = y+h;
        centerX = x + (x1 - x)/2;
        centerY = y + (y1 - y)/2;
        color = Color.GOLD;
    }

    public TextRect(double x, double y, double x1, double y1, CurrentState currentState, ControllerImpl controller){

        this.h = Math.abs(y - y1);
        this.w = Math.abs(x - x1);
        this.x = Math.min(x1, x);
        this.y = Math.min(y1, y);
        this.x1 = x+w;
        this.y1 = y+h;
        centerX = x + (x1 - x)/2;
        centerY = y + (y1 - y)/2;
        color = Color.GOLD;

        this.currentState = currentState;
        this.controller = controller;

        //customTextArea = new CustomTextArea(x, y,x1 - 20, y1 - 20, null);
        //currentState.treeSet.add(customTextArea);
        //controller.setShape(customTextArea, new Text(""));
    }



    @Override
    public void setFigParams(){
        try {
            setFill(color);
            setStroke(color);
            setX(x);
            setY(y);
            setWidth(w);
            setHeight(h);
        }
        catch (NullPointerException e){
            notifyChanges();
        }


    }

    @Override
    public void recalculateFigParams(double newX, double newY, double newH, double newW) {
        x = newX;
        y = newY;
        w = newW;
        h = newH;
        x1 = x+w;
        y1 = y+h;
        centerX = x + (x1 - x)/2;
        centerY = y + (y1 - y)/2;

        System.out.println("Recalculating");


        //if (currentState == null) return;
        //if (customTextArea == null) return;

        //customTextArea.recalculateFigParams(newX + 20, newY + 20, newH - 40, newW - 40);
        //customTextArea.setFigParams();
        //customTextArea.S
        //currentState.treeSet.remove(customTextArea);
        //if (true) return;
        //customTextArea = new CustomTextArea(x, y,x1 - 40, y1 - 40);
        //currentState.treeSet.add(customTextArea);
        //controller.setShape(customTextArea, new Text(""));
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
        this.x = figX;

    }

    @Override
    public void setFigY(double figY) {
        this.y = figY;

    }

    @Override
    public double getCenterX() {
        return centerX;
    }

    @Override
    public void setCenterX(double centerX) {
        this.centerX = centerX;

    }

    @Override
    public double getCenterY() {
        return centerY;
    }

    @Override
    public void setCenterY(double centerY) {
        this.centerY = centerY;

    }

    @Override
    public double getFigW() {
        return w;
    }

    @Override
    public void setFigW(double figW) {
        this.w = figW;

    }

    @Override
    public double getFigH() {
        return h;
    }

    @Override
    public void setFigH(double figH) {
        this.h = figH;

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
        this.x1 = figX1;

    }

    @Override
    public void setFigY1(double figY1) {
        this.y1 = figY1;

    }

    @Override
    public Color getFigColor() {
        return color;
    }

    @Override
    public void setFigColor(Color c) {
        this.color = c;

    }


    @Override
    public void setText(Text text) {
        this.text = text;
        this.text.setX(x + (w - text.getLayoutBounds().getWidth())/2);
        this.text.setY(y + (h  - text.getLayoutBounds().getHeight())/2 );

    }

    public void recalculateSize(double zoomFactor){
        w = w* zoomFactor;
        h = h* zoomFactor;
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void addShape(Pane canvas) {
        canvas.getChildren().add(this);
        if(text!=null)
            canvas.getChildren().addAll(text);
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
    public void setLayer(int layer) {
        this.layer = layer;
        notifyChanges();
    }

    @Override
    public int getPriority() {
        return 1;
    }

    public void notifyChanges(){
        observer.update();
    }

}
