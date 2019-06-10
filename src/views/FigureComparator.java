package views;

import models.figures.Figure;

import java.io.Serializable;
import java.util.Comparator;

public class FigureComparator implements Serializable, Comparator<Figure> {

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
}
