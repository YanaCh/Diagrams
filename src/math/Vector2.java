package math;

import javafx.geometry.Point2D;
import java.io.Serializable;

public class Vector2 implements Serializable {
    private double x, y;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
