package controllers;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.figures.Figures;
import views.EditorView;
import views.Observer;

import java.util.Iterator;

/**
 *  ************* How to use ************************
 *
 * Rectangle rectangle = new Rectangle(50, 50);
 * rectangle.setFill(Color.BLACK);
 * DragResizeMod.makeResizable(rectangle, null);
 *
 * Pane root = new Pane();
 * root.getChildren().add(rectangle);
 *
 * primaryStage.setScene(new Scene(root, 300, 275));
 * primaryStage.show();
 *
 * ************* OnDragResizeEventListener **********
 *
 * You need to override OnDragResizeEventListener and
 * 1) preform out of main field bounds check
 * 2) make changes to the node
 * (this class will not change anything in node coordinates)
 *
 * There is defaultListener and it works only with Canvas nad Rectangle
 */

public class DragResizeMod {



    public interface OnDragResizeEventListener {
        void onDrag(Figures figure, double x, double y, double h, double w);

        void onResize(Figures figures, double x, double y, double h, double w);
    }

    private static final OnDragResizeEventListener defaultListener = new OnDragResizeEventListener() {
        private ControllerImpl controller = ControllerImpl.getInstance();
        @Override
        public void onDrag(Figures figure, double x, double y, double h, double w) {
            /*
            // TODO find generic way to get parent width and height of any node
            // can perform out of bounds check here if you know your parent size
            if (x > width - w ) x = width - w;
            if (y > height - h) y = height - h;
            if (x < 0) x = 0;
            if (y < 0) y = 0;
            */
            setNodeSize(figure, x, y, h, w);
        }

        @Override
        public void onResize(Figures figure, double x, double y, double h, double w) {
            /*
            // TODO find generic way to get parent width and height of any node
            // can perform out of bounds check here if you know your parent size
            if (w > width - x) w = width - x;
            if (h > height - y) h = height - y;
            if (x < 0) x = 0;
            if (y < 0) y = 0;
            */
            setNodeSize(figure, x, y, h, w);
        }

        private void setNodeSize(Figures figure, double x, double y, double h, double w) {
            controller.changeStrokeParams(figure,x,y,h,w);
        }
    };

    public static enum S {
        DEFAULT,
        DRAG,
        NW_RESIZE,
        SW_RESIZE,
        NE_RESIZE,
        SE_RESIZE,
        E_RESIZE,
        W_RESIZE,
        N_RESIZE,
        S_RESIZE;
    }


    private double clickX, clickY, nodeX, nodeY, nodeH, nodeW;

    private S state = S.DEFAULT;

    private static boolean toDelete = false;

    private Node node;
    private Figures figure;
    private OnDragResizeEventListener listener = defaultListener;
    public static boolean wasCalled = false;
    private CurrentState  currentState = CurrentState.getInstance();
    private ControllerImpl controller = ControllerImpl.getInstance();

    private static final int MARGIN = 10;
    private static final double MIN_W = 30;
    private static final double MIN_H = 20;

   // public DragResizeMod(ControllerImpl controller){
     //   this.controller = controller;
    //}

    private DragResizeMod(Node node, Figures figure, OnDragResizeEventListener listener) {
        this.node = node;
        this.figure = figure;

        if (listener != null)
            this.listener = listener;
    }

    private DragResizeMod(){}

    public void makeDeletable(Figures figure, Observer observer){


        EditorView.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ( event.getCode().equals( KeyCode.DELETE ) ){
                    System.out.println("Delete");
                    for (Iterator itr = currentState.treeSet.descendingIterator(); itr.hasNext(); ) {
                        Figures fig = (Figures) itr.next();
                        if(fig.equals(figure)){
                            itr.remove();
                            currentState.strokeShape = null;
                            observer.update();
                        }
                    }

                }
            }
        });
    }

    public  void makeResizable(Node node, Figures figure) {
        makeResizable(node, figure,null);
    }

    public void makeResizable(Node node,Figures figure,OnDragResizeEventListener listener) {

        final DragResizeMod resizer = new DragResizeMod(node, figure,listener);

        node.setOnMousePressed(new BaseEventHandler() {
            @Override
            public void doHandle(MouseEvent event) {
            if(currentState.mode == 3) {
                    wasCalled = true;
                    System.out.println("Was called");
                    resizer.mousePressed(event);
            }
            }
        });
        node.setOnMouseDragged(new BaseEventHandler() {
            @Override
            public void doHandle(MouseEvent event) {
                if(currentState.mode == 3)
                resizer.mouseDragged(event);
            }
        });
        node.setOnMouseMoved(new BaseEventHandler() {
            @Override
            public void doHandle(MouseEvent event) {
                if(currentState.mode == 3)
                resizer.mouseOver(event);
            }
        });
        node.setOnMouseReleased(new BaseEventHandler() {
            @Override
            public void doHandle(MouseEvent event) {
                if(currentState.mode == 3) {
                    resizer.mouseReleased(event);
                    wasCalled = false;
                }
            }
        });
    }

    public static void handleDragAndResize(Observer observer){
        final DragResizeMod resizer = new DragResizeMod();

        EditorView.canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Pressed");
                System.out.println(wasCalled);
                if(!wasCalled)
                resizer.findFig(event, observer);

            }

        });

    }

    private void findFig(MouseEvent event, Observer observer){
        if(currentState.mode ==3) {
            for (Iterator itr = currentState.treeSet.descendingIterator(); itr.hasNext(); ) {
                Figures fig = (Figures) itr.next();
                if (fig.isMouseInside(event.getX(), event.getY())) {
                    controller.setStroke(fig);
                    makeDeletable(currentState.strokeShape.getFigure(), observer);
                    makeResizable(currentState.strokeShape.getShape(),
                           currentState.strokeShape.getFigure());
                    break;
                }
                currentState.strokeShape = null;
                observer.update();
            }
        }
    }

    private boolean isMultiLayer(MouseEvent event){
        int count = 0;
            for (Iterator itr = currentState.treeSet.descendingIterator(); itr.hasNext(); ) {
                Figures fig = (Figures) itr.next();
                if (fig.isMouseInside(event.getX(), event.getY()))
                    count++;
            }
            if(count > 1)
                return true;
            else
                return false;
    }



    protected void mouseReleased(MouseEvent event) {
        node.setCursor(Cursor.DEFAULT);
        state = S.DEFAULT;
    }

    protected void mouseOver(MouseEvent event) {
            S state = currentMouseState(event);
            Cursor cursor = getCursorForState(state);
            node.setCursor(cursor);
    }

    private S currentMouseState(MouseEvent event) {
        S state = S.DEFAULT;
        boolean left = isLeftResizeZone(event);
        boolean right = isRightResizeZone(event);
        boolean top = isTopResizeZone(event);
        boolean bottom = isBottomResizeZone(event);

        if (left && top) state = S.NW_RESIZE;
        else if (left && bottom) state = S.SW_RESIZE;
        else if (right && top) state = S.NE_RESIZE;
        else if (right && bottom) state = S.SE_RESIZE;
        else if (right) state = S.E_RESIZE;
        else if (left) state = S.W_RESIZE;
        else if (top) state = S.N_RESIZE;
        else if (bottom) state = S.S_RESIZE;
        else if (isInDragZone(event)) state = S.DRAG;

        return state;
    }

    private static Cursor getCursorForState(S state) {
        switch (state) {
            case NW_RESIZE:
                return Cursor.NW_RESIZE;
            case SW_RESIZE:
                return Cursor.SW_RESIZE;
            case NE_RESIZE:
                return Cursor.NE_RESIZE;
            case SE_RESIZE:
                return Cursor.SE_RESIZE;
            case E_RESIZE:
                return Cursor.E_RESIZE;
            case W_RESIZE:
                return Cursor.W_RESIZE;
            case N_RESIZE:
                return Cursor.N_RESIZE;
            case S_RESIZE:
                return Cursor.S_RESIZE;
            default:
                return Cursor.DEFAULT;

        }
    }


    protected void mouseDragged(MouseEvent event) {

        if (listener != null) {
            double mouseX = parentX(event.getX());
            double mouseY = parentY(event.getY());
            if (state == S.DRAG) {
                listener.onDrag(figure, mouseX - clickX, mouseY - clickY, nodeH, nodeW);
            } else if (state != S.DEFAULT) {
                //resizing
                double newX = nodeX;
                double newY = nodeY;
                double newH = nodeH;
                double newW = nodeW;

                // Right Resize
                if (state == S.E_RESIZE || state == S.NE_RESIZE || state == S.SE_RESIZE) {
                    newW = mouseX - nodeX;
                }
                // Left Resize
                if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE) {
                    newX = mouseX;
                    newW = nodeW + nodeX - newX;
                }

                // Bottom Resize
                if (state == S.S_RESIZE || state == S.SE_RESIZE || state == S.SW_RESIZE) {
                    newH = mouseY - nodeY;
                }
                // Top Resize
                if (state == S.N_RESIZE || state == S.NW_RESIZE || state == S.NE_RESIZE) {
                    newY = mouseY;
                    newH = nodeH + nodeY - newY;
                }

                //min valid rect Size Check
                if (newW < MIN_W) {
                    if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE)
                        newX = newX - MIN_W + newW;
                    newW = MIN_W;
                }

                if (newH < MIN_H) {
                    if (state == S.N_RESIZE || state == S.NW_RESIZE || state == S.NE_RESIZE)
                        newY = newY + newH - MIN_H;
                    newH = MIN_H;
                }

                listener.onResize(figure, newX, newY, newH, newW);
            }
        }
    }

    protected void mousePressed(MouseEvent event) {
            if (isInResizeZone(event)) {
                setNewInitialEventCoordinates(event);
                state = currentMouseState(event);
            } else if (isInDragZone(event)) {
                setNewInitialEventCoordinates(event);
                state = S.DRAG;
            } else {
                state = S.DEFAULT;
            }

    }

    private void setNewInitialEventCoordinates(MouseEvent event) {
        nodeX = nodeX();
        nodeY = nodeY();
        nodeH = nodeH();
        nodeW = nodeW();
        clickX = event.getX();
        clickY = event.getY();
    }

    private boolean isInResizeZone(MouseEvent event) {
        return isLeftResizeZone(event) || isRightResizeZone(event)
                || isBottomResizeZone(event) || isTopResizeZone(event);
    }

    private boolean isInDragZone(MouseEvent event) {
        double xPos = parentX(event.getX());
        double yPos = parentY(event.getY());
        double nodeX = nodeX() + MARGIN;
        double nodeY = nodeY() + MARGIN;
        double nodeX0 = nodeX() + nodeW() - MARGIN;
        double nodeY0 = nodeY() + nodeH() - MARGIN;

        return (xPos > nodeX && xPos < nodeX0) && (yPos > nodeY && yPos < nodeY0);
    }

    private boolean isLeftResizeZone(MouseEvent event) {
        return intersect(0, event.getX());
    }

    private boolean isRightResizeZone(MouseEvent event) {
        return intersect(nodeW(), event.getX());
    }

    private boolean isTopResizeZone(MouseEvent event) {
        return intersect(0, event.getY());
    }

    private boolean isBottomResizeZone(MouseEvent event) {
        return intersect(nodeH(), event.getY());
    }

    private boolean intersect(double side, double point) {
        return side + MARGIN > point && side - MARGIN < point;
    }

    private double parentX(double localX) {
        return nodeX() + localX;
    }

    private double parentY(double localY) {
        return nodeY() + localY;
    }

    private double nodeX() {
        return node.getBoundsInParent().getMinX();
    }

    private double nodeY() {
        return node.getBoundsInParent().getMinY();
    }

    private double nodeW() {
        return node.getBoundsInParent().getWidth();
    }

    private double nodeH() {
        return node.getBoundsInParent().getHeight();
    }
}