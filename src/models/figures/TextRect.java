package models.figures;

import controllers.ControllerImpl;
import controllers.CurrentState;
import controllers.SheetManager;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import views.Observer;

import java.io.Serializable;

public class TextRect extends Rectangle implements Figure, Serializable {

    protected double x,y,x1,y1, w, h, centerX, centerY;
    private double xText,yText,x1Text,y1Text, wText, hText;
    protected transient Color color;
    private String colorRGB;
    protected Text text;
    private int layer;

    private transient Observer observer;

    private CustomTextArea customTextArea;
    private transient CurrentState currentState;
    private transient SheetManager sheetManager;
    private transient ControllerImpl controller;

    public TextRect(){}

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

        this.currentState = CurrentState.getInstance();
        this.controller = ControllerImpl.getInstance();
        this.sheetManager = SheetManager.getInstance();

        //customTextArea = new CustomTextArea(0,0,0, 0, null);
       // customTextArea.setVisible(false);

    }

    public TextRect(double x, double y, double x1, double y1, Observer observer){

        this.h = Math.abs(y - y1);
        this.w = Math.abs(x - x1);
        this.x = Math.min(x1, x);
        this.y = Math.min(y1, y);
        this.x1 = this.x+w;
        this.y1 = this.y+h;
        centerX = x + (x1 - x)/2;
        centerY = y + (y1 - y)/2;

        this.currentState = CurrentState.getInstance();
        this.controller = ControllerImpl.getInstance();
        this.sheetManager = SheetManager.getInstance();

        xText = w/30;
        yText = h/30;
        x1Text = w/30;
        y1Text = h/1.1;
        hText = 30;
        wText = x1Text - xText;


        customTextArea = new CustomTextArea(this.x + xText, this.y + yText,this.x1 - x1Text, this.y1 - y1Text, observer, this);
        sheetManager.currentTreeSet.add(customTextArea);
        customTextArea.setText("Text");
        controller.setShape(customTextArea, text);
    }


    public Figure getSource() {
        return null;
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

        if (currentState == null) return;
        if (customTextArea == null) return;

        //controller.changeParams(customTextArea,newX + xText, newY + yText, hText  , x1 - x1Text - newX - xText );
            customTextArea.followFig(this);

    }

    public void deleteText(){
        customTextArea.setDisable(true);
        customTextArea.setVisible(false);
        customTextArea = null;
    }

    @Override
    public boolean isText() {
        return false;
    }


    @Override
    public void setText(Text text) {

        if (customTextArea == null) return;
        this.text = text;



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
        colorRGB = toRGBCode(color);

    }

    public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
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
      //  canvas.getChildren().add(this);

        if(customTextArea!=null)
            canvas.getChildren().addAll(this, customTextArea);
         // else {

           // canvas.getChildren().add(this);

       // }

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
