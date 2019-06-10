package controllers;

import math.Vector2;
import models.figures.Figure;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import math.GeomCalculations;
import models.connectors.ConnectorAdapter;
import models.connectors.MyLine;
import models.connectors.arrow.Arrow;
import models.figures.*;
import views.Observer;

import java.io.Serializable;
import java.util.Iterator;

public class ModesController extends BaseEventHandler implements Serializable {

    private Vector2 startDrag, endDrag;
    private CurrentState currentState;
    private Observer observer;

    //For dragging
    private Figure draggedFig;
    private boolean dragged;
    private double offsetX;
    private double offsetY;

    //For lines
    private Figure from, to;
    private ConnectorAdapter adapter;
    private ControllerImpl controller;
    private SheetManager sheetManager;


    public ModesController( Observer observer){
        dragged = false;
        currentState = CurrentState.getInstance();
        this.controller = ControllerImpl.getInstance();
        this.sheetManager = SheetManager.getInstance();
        this.observer = observer;

    }

    @Override
    public void doHandle(MouseEvent event) {
        if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {

            switch (currentState.mode){
                case 0:
                    startDrag = new Vector2(event.getX(), event.getY());
                    break;
                case 1:
                    startDrag = new Vector2(event.getX(), event.getY());

                    break;
                case 2:
                    startDrag = new Vector2(event.getX(), event.getY());
                    break;

                case 3:
                    setActive(false);
                    DragResizeMod.handleDragAndResize(observer);
                    break;

                case 4:
                    for(Iterator itr = sheetManager.currentTreeSet.descendingIterator();itr.hasNext();){
                        Figure fig = (Figure) itr.next();
                        if(fig.isMouseInside(event.getX(),event.getY()) &&fig.getType()==2){
                            from = fig;
                            startDrag = new Vector2(event.getX(), event.getY());
                            break;
                        }
                    }
                    break;

                case 5:
                    for(Iterator itr = sheetManager.currentTreeSet.descendingIterator();itr.hasNext();){
                        Figure fig = (Figure) itr.next();
                        if(fig.isMouseInside(event.getX(),event.getY())){
                            controller.changeColor(fig);
                            observer.update();
                            break;
                        }
                    }


                    break;

                case 6:
                    for(Iterator itr = sheetManager.currentTreeSet.descendingIterator();itr.hasNext();){
                        Figure fig = (Figure) itr.next();
                        if(fig.isMouseInside(event.getX(),event.getY())&& fig.getType()==2){
                            from = fig;
                            startDrag = new Vector2(event.getX(), event.getY());
                            break;
                        }
                    }
                    break;

                case 7:
                    startDrag = new Vector2(event.getX(), event.getY());
                    break;
            }

        }

        if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
            switch (currentState.mode){
                case 0:
                    if(Math.abs(startDrag.getX() - event.getX()) > 20 &&
                            Math.abs(startDrag.getY() - event.getY())>20) {
                        dragged = true;
                        endDrag = new Vector2(event.getX(), event.getY());
                        currentState.tempFig = new TextEllipse(startDrag.getX(), startDrag.getY(), event.getX(), event.getY());
                        controller.setShape(currentState.tempFig, new Text(""));
                        controller.setStroke(currentState.tempFig);
                    }



                    break;
                case 1:
                    if(Math.abs(startDrag.getX() - event.getX()) > 20 &&
                            Math.abs(startDrag.getY() - event.getY())>20) {
                        dragged = true;
                        endDrag = new Vector2(event.getX(), event.getY());
                        currentState.tempFig = new ImageRect(startDrag.getX(), startDrag.getY(),event.getX(), event.getY());
                        controller.setShape(currentState.tempFig, new Text(""));
                        controller.setStroke(currentState.tempFig);


                    }

                    break;
                case 2:
                    if(Math.abs(startDrag.getX() - event.getX()) > 20 &&
                            Math.abs(startDrag.getY() - event.getY())>20) {
                        dragged = true;
                        endDrag = new Vector2(event.getX(), event.getY());
                        currentState.tempFig = new TextRect(startDrag.getX(), startDrag.getY(), event.getX(), event.getY());
                        controller.setShape(currentState.tempFig, new Text(""));
                        controller.setStroke(currentState.tempFig);
                    }
                    break;

                case 3:


                    break;

                case 4:
                    if(from != null ) {
                        endDrag = new Vector2(event.getX(), event.getY());
                        currentState.tempFig = new ConnectorAdapter(new MyLine(startDrag.getX(), startDrag.getY(),event.getX(), event.getY()));
                        controller.setShape(currentState.tempFig, new Text(""));
                        controller.setStroke(currentState.tempFig);
                    }
                    break;

                case 6:
                    if(from != null ) {
                        endDrag = new Vector2(event.getX(), event.getY());
                        currentState.tempFig = new ConnectorAdapter(new Arrow(startDrag.getX(), startDrag.getY(),event.getX(), event.getY()));
                        controller.setShape(currentState.tempFig, new Text(""));
                        controller.setStroke(currentState.tempFig);
                    }
                    break;

                case 7:
                    dragged=true;
                    endDrag = new Vector2(event.getX(), event.getY());
                   // currentState.tempFig = new CustomTextArea(startDrag.getX(), startDrag.getY(),event.getX(), event.getY(), observer);
                    controller.setStroke(currentState.tempFig);
                    controller.setShape(currentState.tempFig, new Text(""));
                    break;
            }

        }

        if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {

            switch (currentState.mode){
                case 0:
                    if(dragged) {
                        TextEllipse el = new TextEllipse(startDrag.getX(), startDrag.getY(),event.getX(), event.getY(), observer);
                        sheetManager.currentTreeSet.add(el);
                        System.out.println(sheetManager.currentTreeSet);
                        controller.setShape(el,new Text(""));
                        currentState.tempFig = null;
                        startDrag = null;
                        endDrag = null;
                        dragged = false;
                    }
                    break;
                case 1:
                    if(dragged) {
                        ImageRect imgR = new ImageRect(startDrag.getX(), startDrag.getY(),event.getX(), event.getY(), observer);
                        sheetManager.currentTreeSet.add(imgR);
                        System.out.println(sheetManager.currentTreeSet);
                        controller.setShape(imgR,new Text(""));
                        currentState.tempFig = null;
                        startDrag = null;
                        endDrag = null;
                        dragged = false;
                    }
                    break;
                case 2:
                    if(dragged) {
                        TextRect r = new TextRect(startDrag.getX(), startDrag.getY(),event.getX(), event.getY(), observer);
                        sheetManager.currentTreeSet.add(r);
                        System.out.println(sheetManager.currentTreeSet);
                        controller.setShape(r,new Text("Text"));
                        currentState.tempFig = null;
                        startDrag = null;
                        endDrag = null;
                        dragged = false;
                    }
                    break;
                case 3:
                    if (dragged ) {
                     //   draggedFig = null;
                     //   dragged = false;
                    }
                    break;
                case 4:
                    for(Iterator itr = sheetManager.currentTreeSet.descendingIterator();itr.hasNext();){
                        Figure fig = (Figure) itr.next();
                        System.out.println(fig);
                        if (fig.isMouseInside(event.getX(), event.getY())) {
                            to = fig;
                            break;
                        }
                    }
                    if(to!=null && to != from && from != null && to.getType() == 2) {
                        MyLine l = new MyLine(startDrag.getX(), startDrag.getY(), endDrag.getX(), endDrag.getY(), from, to);
                        adapter = new ConnectorAdapter(l);
                        sheetManager.currentTreeSet.add(adapter);
                        controller.setShape(adapter, new Text(""));
                    }
                    from = null;
                    to = null;
                    currentState.tempFig = null;
                    currentState.strokeShape = null;
                    observer.update();
                    break;

                case 6:
                    for(Iterator itr = sheetManager.currentTreeSet.descendingIterator();itr.hasNext();){
                        Figure fig = (Figure) itr.next();
                        if (fig.isMouseInside(event.getX(), event.getY()) && fig instanceof TextEllipse){
                            to = fig;
                            break;
                        }
                    }
                    if(to!=null && to != from && from != null && to.getType() == 2) {
                        Vector2 interP = GeomCalculations.interactionPointsWithEllipse(startDrag,
                                endDrag, (TextEllipse) to);
                        Arrow arrow = new Arrow(startDrag.getX(), startDrag.getY(),
                                endDrag.getX(), endDrag.getY(), interP, from, (TextEllipse) to);
                        adapter = new ConnectorAdapter(arrow);
                        sheetManager.currentTreeSet.add(adapter);
                        controller.setShape(adapter, new Text("<<include>>"));
                    }
                    from = null;
                    to = null;
                    currentState.tempFig = null;
                    currentState.strokeShape = null;
                    observer.update();
                    break;

                case 7:
                    if(dragged) {
                        //CustomTextArea r = new CustomTextArea(startDrag.getX(), startDrag.getY(),event.getX(), event.getY(), observer);
                        //currentState.treeSet.add(r);
                        System.out.println(sheetManager.currentTreeSet);
                       // controller.setShape(r,new Text(""));
                        currentState.tempFig = null;
                        startDrag = null;
                        endDrag = null;
                        dragged = false;
                    }
                    break;
            }

        }

    }

    private void changeAllConsParams(){
        for(Figure figure:sheetManager.currentTreeSet) {
            if(figure.getType()==1)
            controller.changeParams(figure,0,0,figure.getFigH(),figure.getFigW());
        }
    }


}