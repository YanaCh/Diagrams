package fileoperating;

import controllers.ControllerImpl;
import controllers.ModesController;
import controllers.SheetManager;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.connectors.ConnectorAdapter;
import models.connectors.Connectors;
import models.connectors.MyLine;
import models.connectors.arrow.Arrow;
import models.figures.*;
import views.EditorView;
import views.FigureComparator;
import views.Observer;
import views.Sheet;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class SaveFile {

    SheetManager sheetManager = SheetManager.getInstance();
    ControllerImpl controller = ControllerImpl.getInstance();

    public SaveFile(File file) {


        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
        {
            TreeSet<Figure> serializeTreeSet = sheetManager.currentTreeSet;

            oos.writeObject(serializeTreeSet);
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }



    }

    public SaveFile(File file, Observer observer, ModesController modesController, VBox container){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
        {
            TreeSet<Figure> p=(TreeSet<Figure>)ois.readObject();
            TreeSet<Figure> resTreeSet = new TreeSet<Figure>(new FigureComparator());

            for (Iterator itr = p.descendingIterator(); itr.hasNext(); ) {
                Figure fig = (Figure) itr.next();
                Figure textFig;

                if (fig instanceof TextRect) {
                    textFig = new TextRect(fig.getFigX(), fig.getFigY(), fig.getFigX1(), fig.getFigY1(), observer);
                    resTreeSet.add(textFig);

                    controller.setShape(textFig, new Text(""));
                    textFig.setText(fig.getFigText());
                    resTreeSet.add(textFig.getCustomTextArea());
                    ((TextRect) textFig).setColorRGB(((TextRect) fig).getColorRGB());
                    textFig.setLayer(fig.getLayer());
                }

                if (fig instanceof TextEllipse) {
                    textFig = new TextEllipse(((TextEllipse) fig).startingX, ((TextEllipse) fig).startingY
                            , ((TextEllipse) fig).startingX1, ((TextEllipse) fig).startingY1, observer);
                    resTreeSet.add(textFig);

                    controller.setShape(textFig, new Text(""));
                    textFig.setText(fig.getFigText());
                    resTreeSet.add( textFig.getCustomTextArea());
                    textFig.setColorRGB(fig.getColorRGB());
                    textFig.setLayer(fig.getLayer());
               }

                if (fig instanceof ImageRect) {
                    textFig = new ImageRect(fig.getFigX(), fig.getFigY(), fig.getFigX1(), fig.getFigY1(), observer);
                    resTreeSet.add(textFig);
                    resTreeSet.add(textFig.getCustomTextArea());
                    controller.setShape(textFig, new Text(""));
                    textFig.setText(fig.getFigText());
                    ((ImageRect) textFig).setColorRGB(((ImageRect) fig).getColorRGB());
                    textFig.setLayer(fig.getLayer());
                }

            }

            for (Iterator itr = p.descendingIterator(); itr.hasNext(); ) {
                Figure fig = (Figure) itr.next();

                if(fig instanceof ConnectorAdapter){

                    Connectors connector = ((ConnectorAdapter) fig).getConnector();

                    if(connector instanceof MyLine) {

                        Figure from = connector.getFrom();
                        Figure to = connector.getTo();

                        for(Figure figure : resTreeSet){

                            if(figure.getLayer() == from.getLayer())
                                from = figure;

                            if(figure.getLayer() == to.getLayer())
                                to = figure;

                        }

                        connector = new MyLine(fig.getFigX(), fig.getFigY(), fig.getFigX1(), fig.getFigY1(),
                                from, to);
                        ConnectorAdapter connectorAdapter = new ConnectorAdapter(connector);
                        resTreeSet.add(connectorAdapter);
                        controller.setShape(connectorAdapter, new Text(""));
                        connector.setColorRGB(fig.getColorRGB());
                        connector.setLayer(fig.getLayer());

                    }


                    if(connector instanceof Arrow) {

                        Figure from = connector.getFrom();
                        Figure to = connector.getTo();

                        for(Figure figure : resTreeSet){

                            if(figure.getLayer() == from.getLayer())
                                from = figure;

                            if(figure.getLayer() == to.getLayer())
                                to = figure;

                        }
                        if(to instanceof TextEllipse) {

                            connector = new Arrow(fig.getFigX(), fig.getFigY(), fig.getFigX1(), fig.getFigY1(), ((ConnectorAdapter) fig).getInterPoint(),
                                    from, (TextEllipse) to);
                            ConnectorAdapter connectorAdapter = new ConnectorAdapter(connector);
                            resTreeSet.add(connectorAdapter);
                            controller.setShape(connectorAdapter, new Text(""));
                            connectorAdapter.setTextContent(fig.getFigText());
                            connector.setColorRGB(fig.getColorRGB());
                            connector.setLayer(fig.getLayer());
                        }

                    }

                }



            }




            System.out.printf("X1: %s \t X2: %s \n", p.first().getFigX1(), p.first().getFigY1());
            Sheet sheet = new Sheet(container, file.getName(),modesController);
            sheetManager.tabPane.getTabs().add(sheet);
            sheetManager.sheetArrayList.add(sheet);
            //Sheet sheet = (Sheet) sheetManager.tabPane.getTabs().get(2);
           // if(sheet.equals(sheetManager.currentSheet))
            //   sheet = (Sheet) sheetManager.tabPane.getTabs().get(1);
            sheet.treeSet = resTreeSet;
            //sheetManager.tabPane.getSelectionModel().select(sheet);

        }
        catch(Exception ex){

            System.out.println(ex);
        }
    }

}
