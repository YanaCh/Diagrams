package math;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GeomCalculations extends  Application {

    private static double[] makeLineEquation(Vector2 a1, Vector2 a2){
        double A = a1.getY() - a2.getY();
        double B = a2.getX() - a1.getX();
        double C = a1.getX() * a2.getY() - a2.getX() * a1.getY();

        System.out.println(A + "x + " + B + "y + " + C + " = 0");
        return new double[] {A, B, C};
    }

    private static boolean isSegmentCrossesEllipse(Vector2 a1, Vector2 a2, Ellipse ellipse){
        //(x1-x0)^2/a^2 + (y1-y0)^2/b^2 < 1
        // (x2-x0)^2/a^2 + (y2-y0)^2/b^2 > 1
        double x1 = Math.pow(a1.getX() - ellipse.getCenterX(),2) / Math.pow(ellipse.getRadiusX(),2);
        double y1 = Math.pow(a1.getY() - ellipse.getCenterY(),2) / Math.pow(ellipse.getRadiusY(),2);

        double x2 = Math.pow(a2.getX() - ellipse.getCenterX(),2) / Math.pow(ellipse.getRadiusX(),2);
        double y2 = Math.pow(a2.getY() - ellipse.getCenterY(),2) / Math.pow(ellipse.getRadiusY(),2);

        //(x1-x0)^2/a^2 + (y1-y0)^2/b^2 > 1
        // (x2-x0)^2/a^2 + (y2-y0)^2/b^2 < 1

        if(x1 + y1 <1 && x2 + y2 >1 || x1 + y1 >1 && x2 + y2 <1)
            return  true;

        return false;
    }


    private static Vector2 pointBelongsSegment(Vector2 interP1, Vector2 interP2, Vector2 segP1,
                                               Vector2 segP2){

        Vector v1 = new Vector(new double[]{interP1.getX(),interP1.getY()},
                  new double[]{segP1.getX(),segP1.getY()});
        Vector v2 = new Vector(new double[]{interP1.getX(),interP1.getY()},
                new double[]{segP2.getX(),segP2.getY()});
        if(v1.innerProd(v2)<= 0)
            return  interP1;
        else return  interP2;


    }

    public static Vector2 interactionPointsWithEllipse(Vector2 a1, Vector2 a2, Ellipse ellipse){

        double [] coefs = makeLineEquation(a1, a2);
        double A = coefs[0];
        double B = coefs [1];
        double C = coefs[2];

        double A1;
        double B1;
        double C1;

        double D;
        double y1;
        double y2;
        double x1;
        double x2;

        double a = ellipse.getRadiusX();
        double b = ellipse.getRadiusY();
        double x0 = ellipse.getCenterX();
        double y0 = ellipse.getCenterY();
        System.out.println("A = " + A + " B = " + B + " C = " + C);


        if (isSegmentCrossesEllipse(a1,a2, ellipse)) {
            if (A == 0) {

                A1 = b*b;
                B1 = - 2*x0*b*b;
                C1 = b*b*x0*x0 + a*a*C*C / (B*B) + 2*y0*a*a*C/B + a*a*y0*y0 - a*a*b*b;

                D = Math.sqrt(Math.pow(B1, 2) - 4 * A1 * C1);
                x1 = (- B1 + D)/ (2 * A1);
                x2 = (- B1 - D)/ (2 * A1);
                y1 = - C/B;
                y2 = y1;

                System.out.println("IF x1 y1 = " + x1 + " " + y1 + " x2 y2 = " + x2 + " " + y2);
            }
            else {

                A1 = Math.pow((b * B) / A, 2) + Math.pow(a, 2);

                B1 = (Math.pow(b, 2) * 2 * B * C) / Math.pow(A, 2) + (Math.pow(b, 2) * 2 * B * x0) / A -
                        Math.pow(a, 2) * 2 * y0;

                C1 = Math.pow(a * y0, 2) - Math.pow(a * b, 2) + Math.pow(b * x0, 2) +
                        (2 * x0 * C * Math.pow(b, 2)) / A + Math.pow((b * C) / A, 2);


                D = Math.sqrt(Math.pow(B1, 2) - 4 * A1 * C1);
                y1 = (-B1 + D) / (2 * A1);
                y2 = (-B1 - D) / (2 * A1);
                x1 = (-C - B * y1) / A;
                x2 = (-C - B * y2) / A;
                System.out.println("x1 y1 = " + x1 + " " + y1 + " x2 y2 = " + x2 + " " + y2);

            }
            Vector2 interactionP = pointBelongsSegment(new Vector2(x1,y1),
                    new Vector2(x2,y2),a1,a2);
            System.out.println("interaction point is x: " + interactionP.getX()
                    + " y: " + interactionP.getY());
            return interactionP;
        }
        else {

            System.out.println("Any interections or 2 interection");
            return null;
        }

    }

    private static Vector2 interactionPointWithOX(Vector2 a1, Vector2 a2){
        double [] coefs = makeLineEquation(a1, a2);
        double A = coefs[0];
        double B = coefs [1];
        double C = coefs[2];
        double x, y;
        if(A != 0){
            y = 0;
            x = -C/A;
            return new Vector2(x,y);

        }
        else

            return null;
    }

    //need to be tested
    public static double angleLineOX(Vector2 a1, Vector2 a2){
        Vector2 interactP = interactionPointWithOX(a1,a2);;
        double hypotenuseLen ;
        try {
             hypotenuseLen = Math.hypot(a2.getX() - interactP.getX(), a2.getY() - interactP.getY());
        }
        catch (NullPointerException e){
            System.out.println("Lines are ||");
            return 0;
        }

        double cathetusLen = Math.hypot(0, a2.getY());

        double result = Math.toDegrees(Math.asin(cathetusLen/hypotenuseLen));
        System.out.println("degrees " + result);
        return result;

    }



    private static boolean sign (double num){
        //true: num is +
        if(num >= 0) return true;

        //false: num is -
       else
           return false;
    }



   public static Point2D interactionPointsWithRectangle(Point2D seg1StartCoords, Point2D seg1EndCoords, Rectangle rec){

   double x1 = rec.getX() + rec.getWidth();
   double y1 = rec.getY();
   double x2 = rec.getX() + rec.getWidth();
   double y2 = rec.getY() - rec.getHeight();
   double x3 = rec.getX();
   double y3 = rec.getY() - rec.getHeight();

    LineSegment segment1 = new LineSegment(new Point2D(rec.getX(),rec.getY()),new Point2D(x1,y1));
    LineSegment segment2 = new LineSegment(new Point2D(rec.getX(),rec.getY()), new Point2D(x3, y3));
    LineSegment segment3 = new LineSegment(new Point2D(x3,y3), new Point2D(x2,y2));
    LineSegment segment4 = new LineSegment(new Point2D(x2,y2), new Point2D(x1, y1));

       ArrayList<LineSegment> segmentArrayList = new ArrayList<LineSegment>();

       Vector vec1 = new Vector(new double[]{seg1StartCoords.getX(), seg1StartCoords.getY(),0},
               new double[]{seg1EndCoords.getX(), seg1EndCoords.getY(),0} );

       segmentArrayList.add(segment1);
       segmentArrayList.add(segment2);
       segmentArrayList.add(segment3);
       segmentArrayList.add(segment4);


       for (LineSegment segment: segmentArrayList){
          Point2D startCoords = segment.getStartPoint();
          Point2D endCoords = segment.getEndPoint();

           Vector vec2 = new Vector(new double[]{startCoords.getX(), startCoords.getY(),0},
                   new double[]{endCoords.getX(), endCoords.getY(),0} );

           Vector prod1 = vec1.crossProd(new Vector(new double[]{seg1StartCoords.getX(), seg1StartCoords.getY(),0},
                   new double[]{startCoords.getX(), startCoords.getY(),0}));

           Vector prod2 = vec1.crossProd(new Vector(new double[]{seg1StartCoords.getX(), seg1StartCoords.getY(),0},
                   new double[]{endCoords.getX(), endCoords.getY(),0}));

           if(sign(prod1.cartesian(2)) == sign(prod2.cartesian(2))
                   || prod1.cartesian(2) == 0 || prod2.cartesian(2) == 0) {
               System.out.println("are not crossing1");
               continue;
           }
           prod1 = vec2.crossProd(new Vector(new double[]{startCoords.getX(), startCoords.getY(),0},
                   new double[]{seg1StartCoords.getX(), seg1StartCoords.getY(),0}));
           prod2 = vec2.crossProd(new Vector(new double[]{startCoords.getX(), startCoords.getY(),0},
                   new double[]{seg1EndCoords.getX(), seg1EndCoords.getY(),0}));

           if(sign(prod1.cartesian(2)) == sign(prod2.cartesian(2))
                   || prod1.cartesian(2) == 0 || prod2.cartesian(2) == 0) {
               System.out.println("are not crossing2");
               continue;
           }

               double x = seg1StartCoords.getX() + vec1.cartesian(0) *
                       Math.abs(prod1.cartesian(2))/ Math.abs(prod2.cartesian(2)- prod1.cartesian(2));

               double y = seg1StartCoords.getY() + vec1.cartesian(1) *
                       Math.abs(prod1.cartesian(2))/ Math.abs(prod2.cartesian(2)- prod1.cartesian(2));

               System.out.println("are crossing!!!");
               System.out.println("x: " + x + " y: " + y );
               return  new Point2D(x,y);

       }

           return null;

    }



    @Override
    public void start(Stage stage) throws Exception {
        HBox hbox = new HBox();
        stage.setScene(new Scene(hbox));
        stage.sizeToScene();
        stage.show();

        Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(3);
        ellipse.setCenterY(0);
        ellipse.setRadiusX(3);
        ellipse.setRadiusY(2);

        Vector2  a1 = new Vector2(-2,1); //center of first ellipse
        Vector2  a2 = new Vector2(3,1); //center of second ellipse

        interactionPointsWithEllipse(a1, a2, ellipse);

        Rectangle rectangle = new Rectangle();
        rectangle.setX(2);
        rectangle.setY(5);
        rectangle.setHeight(3);
        rectangle.setWidth(4);

        interactionPointsWithRectangle(new Point2D(8,1), new Point2D(4,4),
              rectangle);

       // angleLineOX(new Point2D(5,2), new Point2D(2,4));

    }
}