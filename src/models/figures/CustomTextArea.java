package models.figures;

import controllers.ControllerImpl;
import controllers.DragResizeMod;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import views.Observer;

public class CustomTextArea extends TextArea implements Figures {

    private double x,y,x1,y1, w, h, centerX, centerY;
    private int layer;

    private Observer observer;


    public CustomTextArea(double x, double y, double x1, double y1, Observer observer){

        this.h = Math.abs(y - y1);
        this.w = Math.abs(x - x1);
        this.x = Math.min(x1, x);
        this.y = Math.min(y1, y);
        this.x1 = x+w;
        this.y1 = y+h;
        centerX = x + (x1 - x)/2;
        centerY = y + (y1 - y)/2;
        setText("Text");

        //setMouseTransparent(true);
        //setFocusTraversable(false);
        setOnMousePressed(e -> {

        });
        setOnMouseClicked(e -> {
            if (observer != null) DragResizeMod.ResizerResize(e, observer);
            System.out.println("xyi");
        });

    }


    @Override
    public void setFigParams() {
        setTranslateX(x);
        setTranslateY(y);
        setPrefWidth(w);
        setPrefHeight(h);
       // setWrapText(true);
        //ScrollBar scrollBar = (ScrollBar) this.lookup(".scroll-bar:vertical");
        //scrollBar.setDisable(true);
        //String css = this.getClass().getResource("/css/mycss.css").toExternalForm();
        //this.getStylesheets().add(css);





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
        return null;
    }

    @Override
    public void setFigColor(Color c) {


    }


    @Override
    public void setText(Text text) {

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
        return 4;
    }

    public void notifyChanges(){
        observer.update();
    }
}
