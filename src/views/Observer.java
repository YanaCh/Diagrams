package views;

import controllers.ModesController;
import javafx.scene.control.ColorPicker;

public interface Observer {

    void update();

    ColorPicker getMultiColorButton();
}
