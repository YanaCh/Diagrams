package models;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class ColorButton extends Button {

    private Color color;

    public ColorButton(Color color){

        this.color = color;
    }

    public Color getColor() {
        return color;
    }



}
