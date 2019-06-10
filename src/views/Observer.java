package views;

import controllers.ModesController;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public interface Observer extends Serializable {

    void update();

    Pane getCanvas();

    ColorPicker getMultiColorButton();
}
