package controllers;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import models.figures.Figures;
import models.outline.StrokeShape;

import java.util.Comparator;
import java.util.TreeSet;

public class CurrentState {

    private static CurrentState currentState;

    public Figures tempFig;
    public int tempLayerVal = 0;
    public TreeSet<Figures> treeSet = new TreeSet<Figures>(new Comparator<Figures>() {
        @Override
        public int compare(Figures o1, Figures o2) {

            if(o1.getPriority()>o2.getPriority())
                return 1;
            else if(o1.getPriority()<o2.getPriority())
                return -1;
            else {
                if(o1.getLayer() < o2.getLayer())
                    return 1;
                else
                    return -1;
            }
        }
    });
    public int mode = 0;

    public StrokeShape strokeShape;
   // public ColorPicker  multiColorButton;


    private CurrentState(){
       // multiColorButton = new ColorPicker();
       // multiColorButton.setValue(Color.GOLD);
    }

    public static CurrentState getInstance(){

        if(currentState == null) {
            currentState = new CurrentState();
        }
        return currentState;

    }


}
