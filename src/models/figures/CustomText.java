package models.figures;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CustomText extends TextRect {

    private TextArea textArea;

    public CustomText(double x, double y, double x1, double y1) {
        super(x, y, x1, y1);

        textArea = new TextArea();
    }

    @Override
    public void setFigColor(Color c) {
        color = Color.TRANSPARENT;
    }


    @Override
    public void setText(Text text) {
        text.setText("TextTextTextTextTextTextTextммTextTextTextTextTextTextTextText");
        super.setText(text);
    }

    @Override
    public boolean isMouseInside(double mouseX, double mouseY) {
        if(super.isMouseInside(mouseX,mouseY))
        {

            textArea.setLayoutX(300);
            textArea.setLayoutY(400);
            textArea.setPrefHeight(500);
            textArea.setPrefWidth(500);
            textArea.setWrapText(true);
            textArea.setText("YANAAA");
            String css = this.getClass().getResource("/css/mycss.css").toExternalForm();
            textArea.getStylesheets().add(css);
           // ScrollBar scrollBar = (ScrollBar) textArea.lookup(".scroll-bar:vertical");
           // scrollBar.setDisable(true);
            this.setFigH(0);
            this.setFigW(0);
            this.setFigParams();

            return true;
        }


        else
            return  false;
    }
}
