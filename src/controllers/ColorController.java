package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import models.ColorButton;

public class ColorController implements EventHandler<ActionEvent> {

    private Color mainColor;
    private ColorPicker multiColorButton;


    public ColorController(ColorPicker multiColorButton) {
       this.multiColorButton = multiColorButton;
    }



    @Override
    public void handle(ActionEvent event) {

       ColorButton colorButton = (ColorButton) event.getSource();

       if (colorButton.getColor() == Color.RED) {
           mainColor = Color.RED;
           changeButtonColor(mainColor);
       }
        if (colorButton.getColor() == Color.ORANGE) {
            mainColor = Color.ORANGE;
            changeButtonColor(mainColor);
        }
        if (colorButton.getColor() == Color.YELLOW) {
            mainColor = Color.YELLOW;
            changeButtonColor(mainColor);
        }
        if (colorButton.getColor().equals(Color.rgb(204,230,255))) {
            mainColor = Color.rgb(204,230,255);
            changeButtonColor(mainColor);
        }
        if (colorButton.getColor().equals(Color.rgb(204,255,204)) ) {
            mainColor = Color.rgb(204,255,204);
            changeButtonColor(mainColor);
        }
        if (colorButton.getColor().equals(Color.rgb(204,255,255))) {
            mainColor = Color.rgb(204,255,255);
            changeButtonColor(mainColor);
        }
        if (colorButton.getColor().equals(Color.rgb(255,204,255))) {
            mainColor = Color.rgb(255,204,255);
            changeButtonColor(mainColor);
        }
        if (colorButton.getColor() == Color.WHITE) {
            mainColor = Color.WHITE;
            changeButtonColor(mainColor);
        }
        if (colorButton.getColor() == Color.BLACK) {
            mainColor = Color.BLACK;
            changeButtonColor(mainColor);
        }

    }

    private void changeButtonColor(Color mainColor){
       multiColorButton.setValue(mainColor);
       multiColorButton.setStyle("-fx-color-label-visible: false ;");

    }


}
