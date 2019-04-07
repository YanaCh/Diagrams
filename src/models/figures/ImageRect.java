package models.figures;

import javafx.geometry.Rectangle2D;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.connectors.Connectors;
import views.Observer;

import java.util.ArrayList;

public class ImageRect extends ImageView implements Figures {

    private Image img;
    private double x,y,x1,y1, w, h, centerX, centerY;
    private Rectangle2D rect;
    private Color color;
    private int layer;

    private Observer observer;

    public ImageRect(double x, double y, double x1, double y1){

        this.h = Math.abs(y - y1);
        this.w = Math.abs(x - x1);
        this.x = Math.min(x1, x);
        this.y = Math.min(y1, y);
        this.x1 = x+w;
        this.y1 = y+h;
        centerX = x + (x1 - x)/2;
        centerY = y + (y1 - y)/2;

        this.img = new Image("resources/actorshape.png");
        this.color = Color.MAGENTA;
        setViewport(rect);

    }

    public void setFigParams() {
        setImage(this.img);
        setColor(this.img, color);
        setX(x);
        setY(y);
        setFitWidth(w);
        setFitHeight(h);

    }

    @Override
    public void setText(Text text) {

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


    public boolean isMouseInside(double mouseX, double mouseY) {
        if(this.contains(mouseX,mouseY))
            return true;
        else
            return false;
    }


    public double getFigX() {
        return x;
    }


    public double getFigY() {
        return y;
    }


    public void setFigX(double figX) {
        this.x = figX;

    }


    public void setFigY(double figY) {
        this.y = figY;

    }


    public double getCenterX() {
        return centerX;
    }


    public void setCenterX(double centerX) {
        this.centerX = centerX;

    }


    public double getCenterY() {
        return centerY;
    }


    public void setCenterY(double centerY) {
        this.centerY = centerY;

    }


    public double getFigW() {
        return w;
    }


    public void setFigW(double figW) {
        this.w = figW;

    }


    public double getFigH() {
        return h;
    }


    public void setFigH(double figH) {
        this.h = figH;

    }


    public double getFigX1() {
        return x1;
    }


    public double getFigY1() {
        return y1;
    }


    public void setFigX1(double figX1) {
        this.x1 = figX1;

    }


    public void setFigY1(double figY1) {
        this.y1 = figY1;

    }



    public Color getFigColor() {
        return color;
    }


    public void setFigColor(Color c) {
        this.color = c;

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
    public int getPriority() {
        return 4;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
        notifyChanges();

    }

    public void notifyChanges(){
        observer.update();
    }


    private void setColor(Image img, Color color){
        Lighting lighting = new Lighting();
        lighting.setDiffuseConstant(1.0);
        lighting.setSpecularConstant(0.0);
        lighting.setSpecularExponent(0.0);
        lighting.setSurfaceScale(0.0);
        lighting.setLight(new Light.Distant(45, 70, color));

        setEffect(lighting);

    }


}
