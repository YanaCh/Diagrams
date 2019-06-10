package controllers;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.layout.Pane;
import models.figures.Figure;
import views.Sheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

public class SheetManager implements Serializable {

    private transient static SheetManager sheetManager;

    public transient JFXTabPane tabPane = new JFXTabPane();

    public transient ArrayList<Sheet> sheetArrayList = new ArrayList<>();
    public transient Sheet currentSheet;
    public TreeSet<Figure> currentTreeSet = new TreeSet<Figure>();
    public transient Pane currentCanvas;

    private SheetManager(){
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{

            for(  Sheet sheet : sheetArrayList){
                if(nv.equals(sheet)) {
                    System.out.println(sheet.name + " is selected");
                    currentSheet = sheet;
                    currentTreeSet = sheet.treeSet;
                    currentCanvas = sheet.canvas;
                }
            }

        });
    }

    public static SheetManager getInstance(){
        if(sheetManager == null)
            sheetManager = new SheetManager();
        return sheetManager;}




}
