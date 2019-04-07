package models.outline;

import javafx.scene.Group;
import models.connectors.Connectors;
import models.figures.Figures;
import views.Observer;

public class OutlineShape {

    private  OutlineInterface outlineInterface;
    private Figures figure;

    public void setOutLineShape(Figures figure){
        if(!(figure == null)){
            this.figure = figure;

        }
      outlineInterface = new FigOutline(figure);
      doOutLineShape();
    }

    public  void setOutLineShape(Connectors connector){
        //outlineInterface = new ConOutline(connector);
        doOutLineShape();
    }

    private  void doOutLineShape(){
        outlineInterface.initialize();
        outlineInterface.setParams();
        outlineInterface.joinParamsInComposition();
    }

    private void defineFigType(){
     //   if(figure.getType()==2)
         //   outlineInterface = new FigOutline(figure);
       // else
           // outlineInterface = new ConOutline(figure);

    }

    public Group getOutlineShape(){
         try{
             return outlineInterface.getComposition();
         }
         catch (NullPointerException e){
             System.out.println("Set OutlineShape " + e);
             return null;
         }

    }

    public void changeParams(double x, double y,double h, double w){
        try {
            outlineInterface.changeParams(x, y, h, w);
        }
        catch (NullPointerException e){
            System.out.println("Set OutlineShape " + e);
        }

     }

     public void registerObserver(Observer observer){outlineInterface.registerObserver(observer);
     }


}
