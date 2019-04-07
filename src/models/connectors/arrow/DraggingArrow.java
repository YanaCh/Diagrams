package models.connectors.arrow;

import javafx.geometry.Point2D;

public class DraggingArrow implements ArrowCalculations {

    private Arrow arrow;


    public DraggingArrow(Arrow arrow){
        this.arrow = arrow;
    }


    @Override
    public void calculate() {

        arrow.setLineParams();

        double sx =  arrow.getLine().getStartX();
        double sy = arrow.getLine().getStartY();
        double ex = arrow.getLine().getEndX();
        double ey = arrow.getLine().getEndY();

        double arrowLength = 20;
        double arrowWidth = 7;


        double factor = arrowLength / Math.hypot(sx-ex, sy-ey);
        double factorO = arrowWidth / Math.hypot(sx-ex, sy-ey);

        // part in direction of main shape
        double dx = (sx - ex) * factor;
        double dy = (sy - ey) * factor;

        // part ortogonal to main shape
        double ox = (sx - ex) * factorO;
        double oy = (sy - ey) * factorO;


        arrow.setArrow1Params(ex + dx - oy,ey + dy + ox,ex, ey);
        arrow.setArrow2Params(ex + dx + oy,ey + dy - ox,ex,ey);

    }

}
