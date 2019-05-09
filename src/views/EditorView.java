package views;

import controllers.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import models.ColorButton;
import models.figures.Figures;
import xmlproc.WriteXML;

public class EditorView extends Application implements Observer {

    ColorController colorController;
    ModesController modesController;
    ControllerImpl controller;
    CurrentState currentState;
    public static Pane canvas;
    public static Scene scene;
    private ColorPicker multiColorButton;


    @Override
    public void start(Stage primaryStage) throws Exception {

        multiColorButton = new ColorPicker();
        controller = ControllerImpl.getInstance(this);
        modesController = new ModesController(controller, this);

        primaryStage.setTitle("Use Case Creator ");
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white");
        scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem close = new MenuItem("Close");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save as...");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(close, save, saveAs, new SeparatorMenuItem(), exit);
        Menu helpMenu = new Menu("Help");
        MenuItem about = new MenuItem("About");
        helpMenu.getItems().add(about);
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        root.setTop(menuBar);

        SplitPane splitPane = new SplitPane();
        splitPane.prefWidthProperty().bind(root.widthProperty());
        splitPane.prefHeightProperty().bind(root.heightProperty());
        splitPane.setOrientation(Orientation.HORIZONTAL);


        ToolBar toolButtonBar = new ToolBar();

        Button btnActor = new Button("Actor");
        Button btnUseCase = new Button("Use Case");
        Button btnSystem = new Button("System");
        Button btnLine = new Button("ConnectLine");
        Button btnDrag = new Button("Drag");
        Button btnRepaint = new Button("Repaint");
        Button btnArrow = new Button("Arrow");
        Button btnText = new Button("Text");

        Button makeXMl = new Button("XML");

        Insets insets = new Insets(10);
        toolButtonBar.setPadding(insets);
        toolButtonBar.setOrientation(Orientation.VERTICAL);
        toolButtonBar.getItems().addAll(btnActor, btnSystem, btnUseCase, btnDrag, btnLine, btnRepaint, btnArrow, btnText, makeXMl);

        //HBox hBox = new HBox();
        //toolButtonBar.prefWidthProperty().bind(hBox.widthProperty());
        //toolButtonBar.prefHeightProperty().bind(hBox.heightProperty());
        //hBox.getChildren().add(toolButtonBar);

        splitPane.getItems().add(toolButtonBar);

        VBox vBox = new VBox();

        currentState = CurrentState.getInstance();

        ToolBar toolColorBar = new ToolBar();
        toolColorBar.setOrientation(Orientation.HORIZONTAL);
        Color mainColor = Color.AQUA;

        ColorButton redButton = new ColorButton(Color.RED);
        ColorButton orangeButton = new ColorButton(Color.ORANGE);
        ColorButton yellowButton = new ColorButton(Color.YELLOW);
        ColorButton blueButton = new ColorButton(Color.BLUE);
        ColorButton greenButton = new ColorButton(Color.GREEN);
        ColorButton cyanButton = new ColorButton(Color.CYAN);
        ColorButton magentaButton = new ColorButton(Color.MAGENTA);
        ColorButton whiteButton = new ColorButton(Color.WHITE);
        ColorButton blackButton = new ColorButton(Color.BLACK);

        multiColorButton.setValue(mainColor);
        multiColorButton.getStyleClass().add("button");
        multiColorButton.setStyle("-fx-color-label-visible: false ;");

        redButton.setStyle("-fx-background-color: red");
        orangeButton.setStyle("-fx-background-color: orange");
        yellowButton.setStyle("-fx-background-color: yellow");
        blueButton.setStyle("-fx-background-color: blue");
        greenButton.setStyle("-fx-background-color: green");
        cyanButton.setStyle("-fx-background-color: cyan");
        magentaButton.setStyle("-fx-background-color: magenta");
        whiteButton.setStyle("-fx-background-color: white");
        blackButton.setStyle("-fx-background-color: black");

        multiColorButton.setPrefHeight(30);
        redButton.setPrefWidth(25);
        redButton.setPrefHeight(25);
        orangeButton.setPrefWidth(25);
        orangeButton.setPrefHeight(25);
        yellowButton.setPrefWidth(25);
        yellowButton.setPrefHeight(25);
        blueButton.setPrefWidth(25);
        blueButton.setPrefHeight(25);
        greenButton.setPrefWidth(25);
        greenButton.setPrefHeight(25);
        cyanButton.setPrefWidth(25);
        cyanButton.setPrefHeight(25);
        magentaButton.setPrefWidth(25);
        magentaButton.setPrefHeight(25);
        whiteButton.setPrefWidth(25);
        whiteButton.setPrefHeight(25);
        blackButton.setPrefWidth(25);
        blackButton.setPrefHeight(25);

        toolColorBar.getItems().addAll(multiColorButton,blackButton,whiteButton,blueButton,magentaButton,cyanButton,greenButton,
                yellowButton,orangeButton,redButton);
        Insets insetsColor = new Insets(10,15, 10, 20);
        toolColorBar.setPadding(insetsColor);

        vBox.getChildren().add(toolColorBar);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.prefWidthProperty().bind(vBox.widthProperty());
        scrollPane.prefHeightProperty().bind(vBox.heightProperty());
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        canvas = new Pane();
        canvas.setStyle("-fx-background-color: white");
        scrollPane.setContent(canvas);
        canvas.prefWidthProperty().bind(scrollPane.widthProperty());
        canvas.prefHeightProperty().bind(scrollPane.heightProperty());

        vBox.getChildren().add(scrollPane);

        splitPane.getItems().add(vBox);
        root.setCenter(splitPane);

        primaryStage.show();

        //////////////////////////////////////////////////////////////////////////////////

        colorController = new ColorController(multiColorButton);

        redButton.setOnAction(colorController);
        orangeButton.setOnAction(colorController);
        yellowButton.setOnAction(colorController);
        blueButton.setOnAction(colorController);
        greenButton.setOnAction(colorController);
        cyanButton.setOnAction(colorController);
        cyanButton.setOnAction(colorController);
        magentaButton.setOnAction(colorController);
        whiteButton.setOnAction(colorController);
        blackButton.setOnAction(colorController);



        btnUseCase.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                currentState.mode = 0;
                modesController.setActive(true);
            }
        });
        btnActor.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                currentState.mode = 1;
                modesController.setActive(true);
            }
        });
        btnSystem.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                currentState.mode = 2;
                modesController.setActive(true);
            }
        });
        btnDrag.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                currentState.mode = 3;
                modesController.setActive(true);
            }
        });
        btnLine.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                currentState.mode = 4;
                modesController.setActive(true);
            }
        });
        btnRepaint.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                currentState.mode = 5;
                modesController.setActive(true);
            }
        });
        btnArrow.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                currentState.mode = 6;
                modesController.setActive(true);
            }
        });
        btnText.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                currentState.mode = 7;
                modesController.setActive(true);
            }
        });
        makeXMl.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                WriteXML writeXML = new WriteXML();
                writeXML.fill();
            }
        });

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

        for (Figures figure: currentState.treeSet)
            figure.addShape(canvas);

    }

    private void drawTempFigs() {

            if(currentState.tempFig!=null)
            currentState.tempFig.addShape(canvas);

            if(currentState.strokeShape!=null)
                canvas.getChildren().add(currentState.strokeShape);
    }

    public ColorPicker getMultiColorButton() {
        return multiColorButton;
    }

    public static void main(String[] args) {
        Application.launch(EditorView.class,args);
    }
}
