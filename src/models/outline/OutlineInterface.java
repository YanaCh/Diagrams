package models.outline;

import javafx.scene.Group;
import views.Observer;

interface OutlineInterface {

    void initialize();

    void setParams();

    void joinParamsInComposition();

    Group getComposition();

    void changeParams(double x, double y,double h, double w);

    void registerObserver(Observer observer);



}
