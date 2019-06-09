package controllers;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import models.figures.Figures;
import views.Sheet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class SheetManager {

    private static SheetManager sheetManager;

    public JFXTabPane tabPane = new JFXTabPane();

    public ArrayList<Sheet> sheetArrayList = new ArrayList<>();
    public Sheet currentSheet;
    public TreeSet<Figures> currentTreeSet = new TreeSet<Figures>();
    public Pane currentCanvas;

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
