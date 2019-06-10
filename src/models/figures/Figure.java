package models.figures;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import views.Observer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "figure")
public interface Figure {

   double getFigX();

   double getFigY();

   void setFigX(double figX);

   void setFigY(double figY);

   double getCenterX();

   void setCenterX(double centerX);

   double getCenterY();

   void setCenterY(double centerY);

   double getFigW();

   void setFigW(double figW);

   double getFigH();

   void setFigH(double figH);

   double getFigX1();

   double getFigY1();

   void setFigX1(double figX1);

   void setFigY1(double figY1);

   Color getFigColor();

   void setFigColor(Color color);

   void setFigParams();

   void setText(Text text);

   void recalculateFigParams(double newX, double newY, double newH, double newW );

   boolean isMouseInside(double mouseX, double mouseY);

   void recalculateSize(double zoomFactor);

   void registerObserver(Observer observer);

   void addShape(Pane canvas);

   int getType();

   int getLayer();

   void setLayer(int layer);

   int getPriority();

   void notifyChanges();

   boolean isText();

   Figure getSource();

}
