package models.connectors.arrow;

import controllers.ControllerImpl;
import controllers.CurrentState;
import controllers.SheetManager;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import math.GeomCalculations;
import math.Vector2;
import math.Vector3;
import models.connectors.ConnectorAdapter;
import models.connectors.Connectors;
import models.figures.CustomTextArea;
import models.figures.Figure;
import models.figures.TextEllipse;
import views.Observer;

import java.io.Serializable;

public class Arrow extends Group implements Connectors, Serializable {

   private double x, y, x1, y1;
   private Vector2 interPoint;
   private Vector2 vecTo;
   private Vector2 vecFrom;
   private String textContent;
   private transient Text text;
   private transient Line line;
   private transient Line arrow1;
   private transient Line arrow2;
   private TextEllipse to;
   private Figure from;
   private transient Color color;
   private Vector3 colorRGB;
   private int layer;
   private int figLayer;


   private transient ArrowCalculations arrowCalculations;

   private int count = 0;

   private transient Observer observer;


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


    public Arrow(double x, double y, double x1, double y1, Vector2 interPoint,  Vector2 vecFrom, Vector2 vecTo){

        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.line = new Line();
        this.arrow1 = new Line();
        this.arrow2 = new Line();
        this.interPoint = interPoint;

        this.vecFrom = vecFrom;
        this.vecTo = vecTo;

        arrowCalculations = new ReleasedArrow(this);
        arrowCalculations.calculate();

    }

   public Arrow(double x, double y, double x1, double y1, Vector2 interPoint, Figure from,
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

       vecFrom = new Vector2(from.getCenterX()- x, from.getCenterY() - y);
       vecTo = new Vector2(to.getCenterX()- x1, to.getCenterY() - y1);

       arrowCalculations = new ReleasedArrow(this);
       arrowCalculations.calculate();




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


    public Color getFigColor() {
        return color;
    }

    public Vector3 getColorRGB(){return colorRGB;}

    public void setColorRGB(Vector3 colorRGB){
        this.color = Color.rgb((int) colorRGB.getX(),(int)colorRGB.getY(),(int) colorRGB.getZ());
        this.colorRGB = colorRGB;
        line.setStroke(color);
        arrow1.setStroke(color);
        arrow2.setStroke(color);

    }

    public void setColor(Color c){
        color = c;
        colorRGB = toRGBCode(c);
        line.setStroke(c);
        arrow1.setStroke(c);
        arrow2.setStroke(c);
    }

    public void setText(Text text){
       this.text = text;
       this.textContent = text.getText();
    }

    public void setTextContent(String textContent){
        this.textContent = textContent;
        this.text.setText(textContent);
        setConParams();
    }

    public String getText(){
       return textContent;
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
        interPoint = GeomCalculations.interactionPointsWithEllipse(new Vector2(x, y),
                new Vector2(x1, y1), to);

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
                text.setFont(Font.font("Lucida Console", FontWeight.MEDIUM, 14));

            }

            catch (NullPointerException e){

                System.out.println(e + "Set connectors text!");
                text = new Text("");

            }
        if(count<1) {
            this.getChildren().addAll(arrow1, arrow2, line);
            count++;
        }
    }


    public Vector2 getInterPoint() {
        return interPoint;
    }

    public void setInterPoint(Vector2 interPoint) {
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
        canvas.getChildren().addAll(this,text);
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



    private void notifyChanges(){
        observer.update();
    }



}
