package views;

import controllers.ModesController;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;

public interface Observer {

    void update();

    Pane getCanvas();

    ColorPicker getMultiColorButton();
}
