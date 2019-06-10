package models.figures;

import controllers.ControllerImpl;
import controllers.SheetManager;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import views.Observer;

import java.io.Serializable;

public class TextEllipse extends Ellipse implements Figure, Serializable {

    private double x,y,x1,y1,centerX, centerY, radiusX, radiusY;
    public double startingX, startingY, startingX1, startingY1;
    //A2(x,y),B2(x2,y2) vertices of the to
    private double  wText, hText;
    private Text text;
    private transient Color color;
    private String colorRGB;
    private int layer;

    private transient Observer observer;

    private CustomTextArea customTextArea;
    private transient SheetManager sheetManager;
    private transient ControllerImpl controller;

    public TextEllipse(double x, double y, double x1, double y1){

        startingX = x;
        startingY = y;
        startingX1 = x1;
        startingY1 = y1;

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

    public TextEllipse(double x, double y, double x1, double y1, Observer observer){

        startingX = x;
        startingY = y;
        startingX1 = x1;
        startingY1 = y1;

        this.centerX = (x + x1)/2;
        this.centerY = (y + y1)/2;
        this.radiusX =  Math.abs((x1 - x)/2);
        this.radiusY = Math.abs((y1 - y))/2;
        this.x = -radiusX + centerX;
        this.y = centerY;
        this.x1 = centerX;
        this.y1 = -radiusY + centerY;
        this.color = Color.GREENYELLOW;

        this.sheetManager = SheetManager.getInstance();
        this.controller = ControllerImpl.getInstance();


        hText = 30;
        wText = radiusX*2/1.2;

        customTextArea = new CustomTextArea(centerX, centerY, wText, hText, this, observer);
        sheetManager.currentTreeSet.add(customTextArea);
        customTextArea.setText("Text");
        controller.setShape(customTextArea, new Text(""));

    }


    public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
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
    public boolean isText() {
        return false;
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

        if (sheetManager == null) return;
        if (customTextArea == null) return;

        //controller.changeParams(customTextArea,newX + xText, newY + yText, hText  , x1 - x1Text - newX - xText );
        customTextArea.followFig(this);

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
       colorRGB = toRGBCode(color);

   }
    public Figure getSource() {
        return null;
    }

    @Override
    public void setText(Text text) {
        if (customTextArea == null) return;
        this.text = text;
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
        //canvas.getChildren().add(this);
        if(customTextArea!=null)
            canvas.getChildren().addAll(this, customTextArea);
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
