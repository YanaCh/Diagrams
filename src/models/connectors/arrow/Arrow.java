package models.connectors.arrow;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import math.GeomCalculations;
import models.connectors.Connectors;
import models.figures.Figures;
import models.figures.TextEllipse;
import views.Observer;

public class Arrow extends Group implements Connectors {

   private double x, y, x1, y1;
   private Point2D interPoint;
   private Point2D vecTo;
   private Point2D vecFrom;
   private Text text;
   private Line line;
   private Line arrow1;
   private Line arrow2;
   private TextEllipse to;
   private Figures from;
   private Color color;
   private int layer;

   private ArrowCalculations arrowCalculations;

   private int count = 0;

   private Observer observer;

   public Arrow(double x, double y, double x1, double y1){

       this.x = x;
       this.y = y;
       this.x1 = x1;
       this.y1 = y1;
       this.line = new Line();
       this.arrow1 = new Line();
       this.arrow2 = new Line();

       arrowCalculations = new DraggingArrow(this);
       arrowCalculations.calculate();

   }

   public Arrow(double x, double y, double x1, double y1, Point2D interPoint, Figures from,
                TextEllipse ellipTo){

       this.x = x;
       this.y = y;
       this.x1 = x1;
       this.y1 = y1;
       this.line = new Line();
       this.arrow1 = new Line();
       this.arrow2 = new Line();
       this.interPoint = interPoint;
       this.to = ellipTo;
       this.from = from;

       vecFrom = new Point2D(from.getCenterX()- x, from.getCenterY() - y);
       vecTo = new Point2D(to.getCenterX()- x1, to.getCenterY() - y1);

       arrowCalculations = new ReleasedArrow(this);
       arrowCalculations.calculate();

   }


    public void setColor(Color c){
        color = c;
        line.setStroke(c);
        arrow1.setStroke(c);
        arrow2.setStroke(c);
    }

    public void setText(Text text){
       this.text = text;
    }

    public void setLineParams(){
        line.setStartX(x);
        line.setStartY(y);
        line.setEndX(x1);
        line.setEndY(y1);
        line.getStrokeDashArray().addAll(4d, 8d);
        line.setStrokeWidth(3);

    }

    public void setArrow1Params(double arrowStartX, double arrowStartY, double arrowEndX, double arrowEndY){
        arrow1.setStartX(arrowStartX);
        arrow1.setStartY(arrowStartY);
        arrow1.setEndX(arrowEndX);
        arrow1.setEndY(arrowEndY);
        arrow1.setStrokeWidth(3);

    }

    public void setArrow2Params(double arrowStartX, double arrowStartY, double arrowEndX, double arrowEndY){
        arrow2.setStartX(arrowStartX);
        arrow2.setStartY(arrowStartY);
        arrow2.setEndX(arrowEndX);
        arrow2.setEndY(arrowEndY);
        arrow2.setStrokeWidth(3);
    }



    public void recalculate(){
        x = from.getCenterX() - vecFrom.getX();
        y = from.getCenterY() - vecFrom.getY();
        x1 = to.getCenterX() - vecTo.getX();
        y1 = to.getCenterY() - vecTo.getY();
        interPoint = GeomCalculations.interactionPointsWithEllipse(new Point2D(x, y),
                new Point2D(x1, y1), to);

        arrowCalculations.calculate();

    }

    public void setConParams(){

            try {

                setColor(color);
            }
            catch (NullPointerException e){

                System.out.println(e + "Set connectors color!");
                setColor(Color.TEAL);

            }
            try {
                text.setX((x + x1 - text.getLayoutBounds().getWidth()*2)/2);
                text.setY((y + y1 - text.getLayoutBounds().getHeight())/2);
            }

            catch (NullPointerException e){

                System.out.println(e + "Set connectors text!");
                text = new Text("");

            }
        if(count<1) {
            this.getChildren().addAll(arrow1, arrow2, line, text);
            count++;
        }
    }


    public Point2D getInterPoint() {
        return interPoint;
    }

    public void setInterPoint(Point2D interPoint) {
        this.interPoint = interPoint;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public TextEllipse getTo() {
        return to;
    }

    public void setTo(TextEllipse to) {
        this.to = to;
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
