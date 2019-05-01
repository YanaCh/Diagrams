package controllers;

import javafx.application.Application;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.figures.*;
import models.outline.StrokeConnector;
import models.outline.StrokeFigure;
import models.outline.StrokeShape;
import views.EditorView;
import views.Observer;

import static javafx.application.Application.launch;

public class ControllerImpl {

    private static ControllerImpl controller;
    private Observer observer;
    CurrentState currentState = CurrentState.getInstance();

    private ControllerImpl(Observer observer){
        this.observer = observer;

    }

   public static ControllerImpl getInstance(Observer observer){
        if(controller == null) {
            controller = new ControllerImpl(observer);
       }
       return controller;
   }

   public static ControllerImpl getInstance(){
        if(controller != null)
            return controller;
        else
            return null;
   }

    public void setShape(Figures figure, Text text) {
        try {
            doShapeSetting(figure,text);
        }
        catch (NullPointerException e){
            figure.notifyChanges();
        }
    }

    private void doShapeSetting(Figures figure, Text text){
        figure.registerObserver(observer);
        figure.setFigColor(observer.getMultiColorButton().getValue());
        figure.setText(text);
        figure.setFigParams();
        figure.setLayer(currentState.tempLayerVal);
        currentState.tempLayerVal++;

    }

    public void setStroke(Figures figure) {
           try{
               doStrokeSetting(figure);
           }
           catch (NullPointerException e){
               currentState.strokeShape.notifyChanges();
           }
    }

    private void doStrokeSetting(Figures figure){
        if (figure.getType() == 2)
            currentState.strokeShape = new StrokeFigure(figure);
        else
            currentState.strokeShape = new StrokeConnector(figure);
        currentState.strokeShape.registerObserver(observer);
        currentState.strokeShape.initialize();
        currentState.strokeShape.setParams();
        currentState.strokeShape.joinParamsInComposition();

    }

    public void changeParams(Figures figure, double newX, double newY, double newH, double newW) {
        figure.recalculateFigParams(newX, newY,newH, newW);
        figure.setFigParams();

    }

    public void changeStrokeParams(Figures figure, double newX, double newY, double newH, double newW){
        StrokeShape s = currentState.strokeShape;
        s.changeParams(newX,newY,newH,newW);
        s.setParams();
        if(figure instanceof TextRect || figure instanceof ImageRect)
            changeParams(figure,newX,newY,newH,newW);
        if(figure instanceof TextEllipse)
           changeParams(figure,s.getX()+newW/2,s.getY() + newH/2,
                   newH,newW);
        if (figure instanceof CustomTextArea){
            changeParams(figure, newX, newY, newH, newW);
        }

        changeAllConsParams();
    }

    public void changeColor(Figures figure) {
        figure.setFigColor(observer.getMultiColorButton().getValue());
        figure.setFigParams();

    }

    public void setFigH(Figures figure,double h) {
        figure.setFigH(h);
        figure.setFigParams();
    }

    public void setFigW(Figures figure,double w) {
            figure.setFigW(w);
            figure.setFigParams();
    }

    private void changeAllConsParams(){
        for(Figures figure:currentState.treeSet) {
            if(figure.getType()==1)
                changeParams(figure,0,0,0,0);
        }
    }

    public void doDrugAndResize() {
        DragResizeMod.handleDragAndResize(observer);
    }
}
