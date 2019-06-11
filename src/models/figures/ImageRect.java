package models.figures;

import controllers.ControllerImpl;
import controllers.CurrentState;
import controllers.SheetManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import views.Observer;

import java.io.Serializable;

public class ImageRect extends ImageView implements Figure, Serializable {

    private transient Image img;
    private double x,y,x1,y1, w, h, centerX, centerY;
    private double xText,yText,x1Text,y1Text, wText, hText;
    private Text text;
    private Rectangle2D rect;
    private transient Color color;
    private String colorRGB;
    private int layer;

    private transient Observer observer;

    private CustomTextArea customTextArea;
    private transient SheetManager sheetManager;
    private transient ControllerImpl controller;
    private CurrentState currentState;

    public ImageRect(double x, double y, double x1, double y1){

        this.h = Math.abs(y - y1);
        this.w = Math.abs(x - x1);
        this.x = Math.min(x1, x);
        this.y = Math.min(y1, y);
        this.x1 = this.x+w;
        this.y1 = this.y+h;
        centerX = this.x + (this.x1 - this.x)/2;
        centerY = this.y + (this.y1 - this.y)/2;

        this.img = new Image("resources/actorshape.png");
        this.color = Color.MAGENTA;
        setViewport(rect);

    }

    public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    public ImageRect(double x, double y, double x1, double y1, Observer observer){

        this.h = Math.abs(y - y1);
        this.w = Math.abs(x - x1);
        this.x = Math.min(x1, x);
        this.y = Math.min(y1, y);
        this.x1 = this.x+w;
        this.y1 = this.y+h;
        centerX = this.x + (this.x1 - this.x)/2;
        centerY = this.y + (this.y1 - this.y)/2;

        this.img = new Image("resources/actorshape.png");
        this.color = Color.MAGENTA;
        setViewport(rect);


        this.sheetManager = SheetManager.getInstance();
        this.controller = ControllerImpl.getInstance();
        this.currentState = CurrentState.getInstance();

        xText = w/30;
        yText = h/30;
        x1Text = w/30;
        y1Text = h/1.1;
        hText = 30;
        wText = x1Text - xText;


        customTextArea = new CustomTextArea(this.x, this.y + h,this.x1, this.y1 + 22, observer, this);
        sheetManager.currentTreeSet.add(customTextArea);
        customTextArea.setText("Text");
        controller.setShape(customTextArea, text);
        customTextArea.setLayer(currentState.tempLayerVal);
        currentState.tempLayerVal++;

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
    public boolean isText() {
        return false;
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

        if (sheetManager == null) return;
        if (customTextArea == null) return;

        controller.changeParams(customTextArea,newX , newY + h, hText  , x1 - x1Text - newX - xText );
        //customTextArea.followFig(this);

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
        colorRGB = toRGBCode(color);

    }

    public Figure getSource() {
        return null;
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
