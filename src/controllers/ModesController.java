package controllers;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import math.GeomCalculations;
import models.connectors.ConnectorAdapter;
import models.connectors.MyLine;
import models.connectors.arrow.Arrow;
import models.figures.*;
import views.EditorView;
import views.Observer;

import java.util.Iterator;

public class ModesController extends BaseEventHandler {

    private Point2D startDrag, endDrag;
    private CurrentState currentState;
    private Observer observer;

    //For dragging
    private Figures draggedFig;
    private boolean dragged;
    private double offsetX;
    private double offsetY;

    //For lines
    private   Figures from, to;
    private ConnectorAdapter adapter;
    private ControllerImpl controller;

    public ModesController(ControllerImpl controller, Observer observer){
        dragged = false;
        currentState = CurrentState.getInstance();
        this.controller = controller;
        this.observer = observer;
    }

    @Override
    public void doHandle(MouseEvent event) {
        if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {

            switch (currentState.mode){
                case 0:
                    startDrag = new Point2D(event.getX(), event.getY());
                    break;
                case 1:
                    startDrag = new Point2D(event.getX(), event.getY());

                    break;
                case 2:
                    startDrag = new Point2D(event.getX(), event.getY());
                    break;

                case 3:
                    setActive(false);
                    DragResizeMod.handleDragAndResize(observer);
                    break;

                case 4:
                    for(Iterator itr = currentState.treeSet.descendingIterator();itr.hasNext();){
                        Figures fig = (Figures) itr.next();
                        if(fig.isMouseInside(event.getX(),event.getY()) &&fig.getType()==2){
                            from = fig;
                            startDrag = new Point2D(event.getX(), event.getY());
                            break;
                        }
                    }
                    break;

                case 5:
                    for(Iterator itr = currentState.treeSet.descendingIterator();itr.hasNext();){
                        Figures fig = (Figures) itr.next();
                        if(fig.isMouseInside(event.getX(),event.getY())){
                            controller.changeColor(fig);
                            observer.update();
                            break;
                        }
                    }


                    break;

                case 6:
                    for(Iterator itr = currentState.treeSet.descendingIterator();itr.hasNext();){
                        Figures fig = (Figures) itr.next();
                        if(fig.isMouseInside(event.getX(),event.getY())&& fig.getType()==2){
                            from = fig;
                            startDrag = new Point2D(event.getX(), event.getY());
                            break;
                        }
                    }
                    break;

                case 7:
                    startDrag = new Point2D(event.getX(), event.getY());
                    break;
            }

        }

        if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
            switch (currentState.mode){
                case 0:
                    dragged=true;
                    endDrag = new Point2D(event.getX(), event.getY());
                    currentState.tempFig = new TextEllipse(startDrag.getX(), startDrag.getY(),event.getX(), event.getY());
                    controller.setShape(currentState.tempFig, new Text(""));
                    controller.setStroke(currentState.tempFig);



                    break;
                case 1:
                    if(Math.abs(startDrag.getX() - event.getX()) > 20 &&
                            Math.abs(startDrag.getY() - event.getY())>20) {
                        dragged = true;
                        endDrag = new Point2D(event.getX(), event.getY());
                        currentState.tempFig = new ImageRect(startDrag.getX(), startDrag.getY(),event.getX(), event.getY());
                        controller.setShape(currentState.tempFig, new Text(""));
                        controller.setStroke(currentState.tempFig);


                    }

                    break;
                case 2:
                    dragged=true;
                    endDrag = new Point2D(event.getX(), event.getY());
                    currentState.tempFig = new TextRect(startDrag.getX(), startDrag.getY(),event.getX(), event.getY());
                    controller.setShape(currentState.tempFig, new Text(""));
                    controller.setStroke(currentState.tempFig);
                    break;

                case 3:


                    break;

                case 4:
                    if(from != null ) {
                        endDrag = new Point2D(event.getX(), event.getY());
                        currentState.tempFig = new ConnectorAdapter(new MyLine(startDrag.getX(), startDrag.getY(),event.getX(), event.getY()));
                        controller.setShape(currentState.tempFig, new Text(""));
                        controller.setStroke(currentState.tempFig);
                    }
                    break;

                case 6:
                    if(from != null ) {
                        endDrag = new Point2D(event.getX(), event.getY());
                        currentState.tempFig = new ConnectorAdapter(new Arrow(startDrag.getX(), startDrag.getY(),event.getX(), event.getY()));
                        controller.setShape(currentState.tempFig, new Text(""));
                        controller.setStroke(currentState.tempFig);
                    }
                    break;

                case 7:
                    dragged=true;
                    endDrag = new Point2D(event.getX(), event.getY());
                    currentState.tempFig = new CustomTextArea(startDrag.getX(), startDrag.getY(),event.getX(), event.getY(), observer);
                    controller.setStroke(currentState.tempFig);
                    controller.setShape(currentState.tempFig, new Text(""));
                    break;
            }

        }

        if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {

            switch (currentState.mode){
                case 0:
                    if(dragged) {
                        TextEllipse el = new TextEllipse(startDrag.getX(), startDrag.getY(),event.getX(), event.getY());
                        currentState.treeSet.add(el);
                        System.out.println(currentState.treeSet);
                        controller.setShape(el,new Text(""));
                        currentState.tempFig = null;
                        startDrag = null;
                        endDrag = null;
                        dragged = false;
                    }
                    break;
                case 1:
                    if(dragged) {
                        ImageRect imgR = new ImageRect(startDrag.getX(), startDrag.getY(),event.getX(), event.getY());
                        currentState.treeSet.add(imgR);
                        System.out.println(currentState.treeSet);
                        controller.setShape(imgR,new Text(""));
                        currentState.tempFig = null;
                        startDrag = null;
                        endDrag = null;
                        dragged = false;
                    }
                    break;
                case 2:
                    if(dragged) {
                        TextRect r = new TextRect(startDrag.getX(), startDrag.getY(),event.getX(), event.getY(), currentState, controller);
                        currentState.treeSet.add(r);
                        System.out.println(currentState.treeSet);
                        controller.setShape(r,new Text(""));
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
                    for(Iterator itr = currentState.treeSet.descendingIterator();itr.hasNext();){
                        Figures fig = (Figures) itr.next();
                        System.out.println(fig);
                        if (fig.isMouseInside(event.getX(), event.getY())) {
                            to = fig;
                            break;
                        }
                    }
                    if(to!=null && to != from && from != null && to.getType() == 2) {
                        MyLine l = new MyLine(startDrag.getX(), startDrag.getY(), endDrag.getX(), endDrag.getY(), from, to);
                        adapter = new ConnectorAdapter(l);
                        currentState.treeSet.add(adapter);
                        controller.setShape(adapter, new Text(""));
                    }
                    from = null;
                    to = null;
                    currentState.tempFig = null;
                    currentState.strokeShape = null;
                    observer.update();
                    break;

                case 6:
                    for(Iterator itr = currentState.treeSet.descendingIterator();itr.hasNext();){
                        Figures fig = (Figures) itr.next();
                        if (fig.isMouseInside(event.getX(), event.getY()) && fig instanceof TextEllipse){
                            to = fig;
                            break;
                        }
                    }
                    if(to!=null && to != from && from != null && to.getType() == 2) {
                        Point2D interP = GeomCalculations.interactionPointsWithEllipse(startDrag,
                                endDrag, (TextEllipse) to);
                        Arrow arrow = new Arrow(startDrag.getX(), startDrag.getY(),
                                endDrag.getX(), endDrag.getY(), interP, from, (TextEllipse) to);
                        adapter = new ConnectorAdapter(arrow);
                        currentState.treeSet.add(adapter);
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
                        CustomTextArea r = new CustomTextArea(startDrag.getX(), startDrag.getY(),event.getX(), event.getY(), observer);
                        currentState.treeSet.add(r);
                        System.out.println(currentState.treeSet);
                        controller.setShape(r,new Text(""));
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
        for(Figures figure:currentState.treeSet) {
            if(figure.getType()==1)
            controller.changeParams(figure,0,0,figure.getFigH(),figure.getFigW());
        }
    }


}