package controllers;

import models.figures.CustomTextArea;
import models.figures.Figure;
import models.outline.StrokeShape;

import java.io.Serializable;

public class CurrentState implements Serializable {

    private static CurrentState currentState;

    public Figure tempFig;
    public int tempLayerVal = 0;
    public CustomTextArea selectedTextArea;
    //public ArrayList<Sheet> sheetArrayList = new ArrayList<>();
   /* public TreeSet<Figure> treeSet = new TreeSet<Figure>(new Comparator<Figure>() {
        @Override

        public int compare(Figure o1, Figure o2) {

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
    */
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
