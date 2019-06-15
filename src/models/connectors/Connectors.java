package models.connectors;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import math.Vector2;
import math.Vector3;
import models.figures.Figure;
import views.Observer;

import java.io.Serializable;

public interface Connectors extends Serializable {

    void recalculate();

    void setConParams();

    void setColor(Color color);

    void setText(Text text);

    boolean isMouseInside(double mouseX, double mouseY);

    void registerObserver(Observer observer);

    void addShape(Pane canvas);

    double getX();

    double getY();

    double getX1();

    double getY1();

    int getLayer();

    void setLayer(int layer);

    int getType();

    Figure getTo();

    Figure getFrom();

    Vector2 getInterPoint();

    Vector2 getVecTo();

    Vector2 getVecFrom();

    //as id
    void setFigLayer(int Layer);

    int getFigLayer();

    public void setColorRGB(Vector3 colorRGB);

    public Vector3 getColorRGB();

    public String getText();

    public void setTextContent(String textContent);



}
