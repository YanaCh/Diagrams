package models.connectors;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.figures.Figure;
import views.Observer;

import java.io.Serializable;

public class ConnectorAdapter implements Figure, Serializable {

    transient Connectors connector;

    public ConnectorAdapter(Connectors connector) {
        this.connector = connector;
    }


    @Override
    public double getFigX() {
        return connector.getX();
    }

    @Override
    public double getFigY() {
        return connector.getY();
    }

    @Override
    public void setFigX(double figX) {

    }

    @Override
    public void setFigY(double figY) {

    }

    @Override
    public double getCenterX() {
        return 0;
    }

    @Override
    public void setCenterX(double centerX) {

    }

    @Override
    public double getCenterY() {
        return 0;
    }

    @Override
    public void setCenterY(double centerY) {

    }

    @Override
    public double getFigW() {
        return 0;
    }

    @Override
    public void setFigW(double figW) {

    }

    @Override
    public double getFigH() {
        return 0;
    }

    @Override
    public void setFigH(double figH) {

    }

    @Override
    public double getFigX1() {
        return connector.getX1();
    }

    @Override
    public double getFigY1() {
        return connector.getY1();
    }

    @Override
    public void setFigX1(double figX1) {

    }

    @Override
    public void setFigY1(double figY1) {

    }

    public Figure getSource() {
        return null;
    }

    @Override
    public Color getFigColor() {
        return null;
    }

    @Override
    public void recalculateSize(double zoomFactor){}

    @Override
    public void setFigColor(Color color) {
        connector.setColor(color);

    }

    @Override
    public void setFigParams() {
        connector.setConParams();

    }

    @Override
    public void setText(Text text) {
        connector.setText(text);
    }


    @Override
    public void recalculateFigParams(double newX, double newY,double newH, double newW) {
        connector.recalculate();

    }

    @Override
    public boolean isMouseInside(double mouseX, double mouseY) {
      if(connector.isMouseInside(mouseX, mouseY))
          return true;
      else
          return false;
    }

    @Override
    public void registerObserver(Observer observer){
        connector.registerObserver(observer);

    }

    @Override
    public void addShape(Pane canvas) {
        connector.addShape(canvas);
    }

    @Override
    public int getType() {
        return connector.getType();
    }

    @Override
    public int getLayer() {
        return connector.getLayer();
    }

    @Override
    public void setLayer(int layer) {
        connector.setLayer(layer);

    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void notifyChanges() {

    }

    @Override
    public boolean isText() {
        return false;
    }

}
