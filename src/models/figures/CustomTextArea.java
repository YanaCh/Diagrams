package models.figures;

import controllers.CurrentState;
import controllers.DragResizeMod;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import math.Vector2;
import math.Vector3;
import views.Observer;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

import java.io.Serializable;
import java.util.function.UnaryOperator;

public class CustomTextArea extends TextArea implements Figure, Serializable {

    private double x,y,x1,y1, w, h, centerX, centerY;
    private int layer;
    private String text;

    private Figure source;

    private Vector2 vecFrom;

    private transient Observer observer;

    private String cssScroll;
    private String cssGrey;
    private String serializableText;
    public String cssTransparent;


    private transient CurrentState currentState = CurrentState.getInstance();

    private transient UnaryOperator<Change> filter = c -> {

        int caret = c.getCaretPosition();
        int anchor = c.getAnchor() ;

        if (caret != anchor) {
            int start = caret ;
            int end = caret ;
            String text = c.getControlNewText();
            while (start > 0 ) start-- ;
            while (end < text.length()) end++ ;
            c.setAnchor(start);
            c.setCaretPosition(end);



        }

        return c ;
    };


    public CustomTextArea(){}

    public CustomTextArea(double x, double y, double x1, double y1, Observer observer, Figure source){

        this.source = source;

        this.h = Math.abs(y - y1);
        this.w = Math.abs(x - x1);
        this.x = Math.min(x1, x);
        this.y = Math.min(y1, y);
        this.x1 = x+w;
        this.y1 = y+h;
        centerX = x + (x1 - x)/2;
        centerY = y + (y1 - y)/2;

        init(source);

    }

    public CustomTextArea(double centerX, double centerY, double w, double h, Figure source, Observer observer){

        this.source = source;
        this.centerX = centerX;
        this.centerY = centerY;
        this.w = w;
        this.h = h;
        this.x = centerX - w/2;
        this.y = centerY - h/2;


        init(source);


    }

    private void init(Figure source){

        vecFrom = new Vector2(source.getFigX() - x, source.getFigY() - y);



        cssScroll = this.getClass().getResource("/css/ScrollDisable.css").toExternalForm();
        cssGrey = this.getClass().getResource("/css/GreyTextBackground.css").toExternalForm();
        cssTransparent = this.getClass().getResource("/css/TransparentTextBackground.css").toExternalForm();

        //setMouseTransparent(true);
        //setFocusTraversable(false);

        setFont(Font.font("Lucida Console", FontWeight.MEDIUM, 14));

        TextFormatter<String> formatter = new TextFormatter<>(filter);
        setTextFormatter(formatter);
        setOnMousePressed(e -> {

        });
        setOnMouseClicked(e -> {
            if (observer != null) DragResizeMod.ResizerResize(e, observer, this);
            this.getStylesheets().add(cssGrey);


            if(!getSelectedText().equals("") ) {
                System.out.println("selected text:"
                        + getSelectedText());
                currentState.selectedTextArea = this;

            }
            else
                currentState.selectedTextArea = null;

        });




    }



    @Override
    public boolean isText() {
        return true;
    }

    public void followFig(Figure figure){
        x = figure.getFigX() - vecFrom.getX();
        y = figure.getFigY() - vecFrom.getY();
        setFigParams();

    }


    @Override
    public void setFigParams() {
        setTranslateX(x);
        setTranslateY(y);
        setPrefWidth(w);
        setPrefHeight(h);
        setWrapText(true);
        //setFigText("Text");

        //setFont(Font.font("Lucida Console", FontWeight.MEDIUM, 14));
        this.getStylesheets().add(cssScroll);
        this.getStylesheets().add(cssGrey);



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

    public Figure getSource() {
        return source;
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
    public void setFigText(Text text) {
        serializableText = text.getText();


    }

    public String getFigText(){
        return this.text;
    }

    @Override
    public CustomTextArea getCustomTextArea() {
        return null;
    }

    @Override
    public void setColorRGB(Vector3 colorRGB) {

    }

    @Override
    public Vector3 getColorRGB() {
        return null;
    }

    public String getSerializableText(){
        return serializableText;
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
