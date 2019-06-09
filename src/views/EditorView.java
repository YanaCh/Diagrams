package views;

import com.jfoenix.controls.*;
import controllers.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.ColorButton;
import models.figures.Figures;
import org.controlsfx.dialog.FontSelectorDialog;




public class EditorView extends Application implements Observer {

    private ColorController colorController;
    private ModesController modesController;
    private ControllerImpl controller;
    private CurrentState currentState;
    private SheetManager sheetManager;
    private   Pane canvas;
    public static Scene scene;
    private JFXColorPicker multiColorButton;
    private Sheet tab;

    JFXButton plusButton;
    private FontSelectorDialog fontSelectorDialog  = new FontSelectorDialog(null);;


    @Override
    public void start(Stage primaryStage) throws Exception {

        multiColorButton = new JFXColorPicker();
        controller = ControllerImpl.getInstance(this);
        currentState = CurrentState.getInstance();
        sheetManager = SheetManager.getInstance();
        modesController = new ModesController( this);


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

        JFXButton fontButton = new JFXButton();

        JFXButton btnActor = new JFXButton("Actor");
        JFXButton btnUseCase = new JFXButton("Use Case");
        JFXButton btnSystem = new JFXButton("System");
        JFXButton btnLine = new JFXButton("ConnectLine");
        JFXButton btnDrag = new JFXButton("Drag");
        JFXButton btnRepaint = new JFXButton("Repaint");
        JFXButton btnArrow = new JFXButton("Arrow");
        JFXButton btnText = new JFXButton("Text");

        JFXButton makeXMl = new JFXButton("XML");

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


        ToolBar toolColorBar = new ToolBar();
        toolColorBar.setOrientation(Orientation.HORIZONTAL);
        Color mainColor = Color.rgb(204,255,255);

        ColorButton redButton = new ColorButton(Color.RED);
        ColorButton orangeButton = new ColorButton(Color.ORANGE);
        ColorButton yellowButton = new ColorButton(Color.YELLOW);
        ColorButton blueButton = new ColorButton(Color.rgb(204,230,255));
        ColorButton greenButton = new ColorButton(Color.rgb(204,255,204));
        ColorButton cyanButton = new ColorButton(Color.rgb(204,255,255));
        ColorButton magentaButton = new ColorButton(Color.rgb(255,204,255));
        ColorButton whiteButton = new ColorButton(Color.WHITE);
        ColorButton blackButton = new ColorButton(Color.BLACK);


         multiColorButton.setValue(mainColor);
         multiColorButton.getStyleClass().add("button");
         multiColorButton.setStyle("-fx-color-label-visible: false ;");

        fontButton.setFont(Font.font("Lucida Console",  FontWeight.BOLD, FontPosture.ITALIC,  16));
        fontButton.setText("Lucida Console 14.0");


        redButton.setStyle("-fx-background-color: red");
        orangeButton.setStyle("-fx-background-color: orange");
        yellowButton.setStyle("-fx-background-color: yellow");
        blueButton.setStyle("-fx-background-color: #cce6ff");
        greenButton.setStyle("-fx-background-color: #ccffcc");
        cyanButton.setStyle("-fx-background-color: #ccffff");
        magentaButton.setStyle("-fx-background-color: #ffccff");
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


        toolColorBar.getItems().addAll(fontButton, multiColorButton,blackButton,whiteButton,blueButton,magentaButton,cyanButton,greenButton,
                yellowButton,orangeButton,redButton);
        Insets insetsColor = new Insets(10,15, 10, 20);
        toolColorBar.setPadding(insetsColor);

        vBox.getChildren().add(toolColorBar);

        plusButton = new JFXButton("➕");
        plusButton.setFont(Font.font("Segoi UI Black", FontWeight.BOLD,18));
        plusButton.setButtonType(JFXButton.ButtonType.RAISED);
        plusButton.getStylesheets().add(getClass().getResource("/css/PlusButton.css").toExternalForm());
        plusButton.getStyleClass().addAll("animated-option-button");


       /* ScrollPane scrollPane = new ScrollPane();
        scrollPane.prefWidthProperty().bind(vBox.widthProperty());
        scrollPane.prefHeightProperty().bind(vBox.heightProperty());
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);



        canvas = new Pane();
        canvas.setStyle("-fx-background-color: white");
        scrollPane.setContent(canvas);

        canvas.setPrefHeight(10000);
        canvas.prefWidthProperty().bind(scrollPane.widthProperty());
        //canvas.prefHeightProperty().bind(scrollPane.heightProperty());
        */



        tab =  new Sheet(vBox, "sheet 1", modesController, this);
        sheetManager.currentSheet = tab;

        Sheet tab1 = new Sheet(vBox, "sheet 2", modesController, this);
        Sheet tab3 = new Sheet(vBox, "sheet 3", modesController, this);

        sheetManager.tabPane.setSide(Side.TOP);
        sheetManager.tabPane.getTabs().addAll(tab, tab1, tab3);

        sheetManager.tabPane.getTabs().stream().forEach(node->{


                    ContextMenu contextMenu=new ContextMenu();
                    MenuItem closeItem=new MenuItem("X");

                    closeItem.setOnAction(actionEvent->{ sheetManager.tabPane.getTabs().remove(node);});
                    contextMenu.getItems().add(closeItem);
                    node.setContextMenu(contextMenu);
                });


        vBox.getChildren().add(sheetManager.tabPane);
        splitPane.getItems().add(vBox);
        root.setCenter(splitPane);

        primaryStage.show();


        //////////////////////////////////////////////////////////////////////////////////


      /*  if (scrollPane.getSkin() == null) {
            // Skin is not yet attached, wait until skin is attached to access the scroll bars
            ChangeListener<Skin<?>> skinChangeListener = new ChangeListener<Skin<?>>() {
                @Override
                public void changed(ObservableValue<? extends Skin<?>> observable, Skin<?> oldValue, Skin<?> newValue) {
                    scrollPane.skinProperty().removeListener(this);
                    accessScrollBar(scrollPane);
                }
            };
            scrollPane.skinProperty().addListener(skinChangeListener);
        } else {
            // Skin is already attached, just access the scroll bars
            accessScrollBar(scrollPane);
        }

*/
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


            }
        });




        fontButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                fontSelectorDialog.setTitle("Select Font");
                fontSelectorDialog.show();



            }
        });


        fontSelectorDialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                if (fontSelectorDialog.getResult() != null) {
                    Font f = fontSelectorDialog.getResult();
                    fontButton.setText( f.getName()
                            + " " + f.getSize());
                    fontButton.setFont(Font.font(f.getFamily(), 16.0));
                    if(currentState.selectedTextArea != null)
                        currentState.selectedTextArea.setFont(f);


                    System.out.println(f.toString());
                }
            }
        });

       /* canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, modesController);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, modesController);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, modesController);

        update();

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            update();
        });

        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
           update();
        });

*/
    }



    private void accessScrollBar(ScrollPane scrollPane) {
        for (Node node : scrollPane.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar) {
                ScrollBar scrollBar = (ScrollBar) node;

                if (scrollBar.getOrientation() == Orientation.HORIZONTAL) {

                }
                if (scrollBar.getOrientation() == Orientation.VERTICAL) {

                    scrollBar.valueProperty().addListener((observable,  oldValue, newValue) -> {
                        double value = (Math.abs(newValue.doubleValue() - oldValue.doubleValue()))* 10000;
                        System.out.println(value);
                        //plusButton.setTranslateY(plusButton.getLayoutY() - value);
                        plusButton.setTranslateY((scrollPane.getVvalue() * 10000) );
                        //scrollPane.setVvalue(0.5);

                         });
                }

            }
        }
    }




    public void update() {
        sheetManager.currentSheet.update();
       // drawMatrix();
      //  drawFigs();
      //  drawTempFigs();

    }

    @Override
    public Pane getCanvas() {
        return canvas;
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

       // for (Figures figure: currentState.treeSet)
        //    figure.addShape(canvas);

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
