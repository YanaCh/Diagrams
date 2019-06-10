package views;

import controllers.CurrentState;
import controllers.ModesController;
import controllers.SheetManager;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import models.figures.Figure;

import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeSet;

public class Sheet extends Tab implements Serializable {

    private transient CurrentState currentState;
    private transient ModesController modesController;
    private transient SheetManager sheetManager;
    public String name;
    public transient ScrollPane scrollPane;
    public transient Pane canvas;


    public TreeSet<Figure> treeSet = new TreeSet<Figure>(new FigureComparator());

    public Sheet(VBox vBox, String name, ModesController modesController){
        this.sheetManager = SheetManager.getInstance();
        this.modesController = modesController;
        currentState = CurrentState.getInstance();
        this.name = name;
         setText(name);
        scrollPane = new ScrollPane();
        scrollPane.prefWidthProperty().bind(vBox.widthProperty());
        scrollPane.prefHeightProperty().bind(vBox.heightProperty());
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        canvas = new Pane();
        canvas.setStyle("-fx-background-color: white");
        canvas.setPrefHeight(10000);
        canvas.prefWidthProperty().bind(scrollPane.widthProperty());

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, modesController);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, modesController);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, modesController);

        update();

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            update();
        });

        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            update();
        });


        scrollPane.setContent(canvas);
        setContent(scrollPane);
        sheetManager.sheetArrayList.add(this);
  }

    public void update() {
        drawMatrix();
        drawFigs();
        drawTempFigs();

    }
    private void drawMatrix(){

        double width = canvas.getPrefWidth();
        double height = canvas.getPrefHeight();

        canvas.getChildren().clear();


        for (int i = 0; i < width; i += 10) {
            Line matrixLine = new Line();
            matrixLine.setStroke(Color.BLACK);
            matrixLine.setStrokeWidth(0.1);
            matrixLine.setStartX(i);
            matrixLine.setStartY(0.0f);
            matrixLine.setEndX(i);
            matrixLine.setEndY(height);
            canvas.getChildren().add(matrixLine);

        }

        for (int i = 0; i < height; i += 10) {
            Line matrixLine = new Line();
            matrixLine.setStroke(Color.BLACK);
            matrixLine.setStrokeWidth(0.1);
            matrixLine.setStartX(0.0f);
            matrixLine.setStartY(i);
            matrixLine.setEndX(width);
            matrixLine.setEndY(i);
            canvas.getChildren().add(matrixLine);
        }

    }


    private void drawFigs(){

        for (Figure figure: sheetManager.currentTreeSet)
            figure.addShape(canvas);

    }

    private void drawTempFigs() {

        if(currentState.tempFig!=null)
            currentState.tempFig.addShape(canvas);

        if(currentState.strokeShape!=null)
            canvas.getChildren().add(currentState.strokeShape);
    }


}
