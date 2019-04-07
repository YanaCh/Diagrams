package models.connectors;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import views.Observer;

public interface Connectors {

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


}
