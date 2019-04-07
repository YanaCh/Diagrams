package models.outline;

import javafx.scene.shape.Line;
import models.figures.Figures;

public class StrokeConnector extends StrokeShape {

    private Line line;

    public StrokeConnector(Figures figure) {
        super(figure);
        line = new Line();
        super.shape = line;
    }

    public void setParams(){
        super.setParams();
        line.setStartX(x);
        line.setStartY(y);
        line.setEndX(x1);
        line.setEndY(y1);
    }

    @Override
    public void joinParamsInComposition() {
        super.joinParamsInComposition();
        notifyChanges();
    }

    @Override
    public void changeParams(double x, double y, double h, double w) {

    }
}
