package views;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Sheet {

  public   ArrayList<Tab> tabArrayList = new ArrayList<>();

    public Pane canvas;

  public Tab createTab(VBox vBox){
      Tab tab = new Tab("Sheet");
      ScrollPane scrollPane = new ScrollPane();
      scrollPane.prefWidthProperty().bind(vBox.widthProperty());
      scrollPane.prefHeightProperty().bind(vBox.heightProperty());
      scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
      scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

      canvas = new Pane();
      canvas.setStyle("-fx-background-color: white");
      canvas.setPrefHeight(10000);
      canvas.prefWidthProperty().bind(scrollPane.widthProperty());
      scrollPane.setContent(canvas);
      tab.setContent(scrollPane);
      tabArrayList.add(tab);

        return tab;
  }


}
