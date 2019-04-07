package math;

import javafx.geometry.Point2D;

/**
 * Created by yana on 01.04.2018.
 */
public class LineSegment {
    private Point2D startPoint ,endPoint;

    public LineSegment(){}

    public LineSegment(Point2D startPoint, Point2D endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;

    }

    public void setStartPoint(Point2D startPoint){
        this.startPoint = startPoint;

    }

    public void setEndPoint(Point2D endPoint){
        this.endPoint = endPoint;
    }

    public Point2D getStartPoint(){
        return startPoint;
    }

    public Point2D getEndPoint(){
        return endPoint;
    }



}
