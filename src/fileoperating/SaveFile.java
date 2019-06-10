package fileoperating;

import controllers.ControllerImpl;
import controllers.SheetManager;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import models.connectors.ConnectorAdapter;
import models.connectors.Connectors;
import models.connectors.MyLine;
import models.connectors.arrow.Arrow;
import models.figures.*;
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

    public SaveFile(String fileName, Observer observer) {


        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("diagram.dat")))
        {
            TreeSet<Figure> serializeTreeSet = sheetManager.currentTreeSet;

            oos.writeObject(serializeTreeSet);
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }


        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("diagram.dat")))
        {
            TreeSet<Figure> p=(TreeSet<Figure>)ois.readObject();
            TreeSet<Figure> resTreeSet = new TreeSet<Figure>(new FigureComparator());

            for (Iterator itr = p.descendingIterator(); itr.hasNext(); ) {
                Figure fig = (Figure) itr.next();
                Figure textFig;
                Connectors connector;
                ConnectorAdapter connectorAdapter;
                //MyLine line;
                Arrow arrow;


                if(fig instanceof TextRect) {
                    textFig = new TextRect(fig.getFigX(), fig.getFigY(), fig.getFigX1(), fig.getFigY1(), observer);
                    resTreeSet.add(textFig);
                    controller.setShape(textFig,new Text(""));
                }

                if(fig instanceof TextEllipse) {
                    textFig = new TextEllipse(((TextEllipse)fig).startingX, ((TextEllipse)fig).startingY
                            , ((TextEllipse)fig).startingX1, ((TextEllipse)fig).startingY1, observer);
                    resTreeSet.add(textFig);
                    controller.setShape(textFig,new Text(""));
                }

                if(fig instanceof ImageRect) {
                    textFig = new ImageRect(fig.getFigX(), fig.getFigY(), fig.getFigX1(), fig.getFigY1(), observer);
                    resTreeSet.add(textFig);
                    controller.setShape(textFig,new Text(""));
                }

                if(fig instanceof CustomTextArea) {
                    textFig = new CustomTextArea(fig.getFigX(), fig.getFigY(), fig.getFigX1(), fig.getFigY1(), observer, fig.getSource());
                    resTreeSet.add(textFig);
                    controller.setShape(textFig,new Text(""));
                }

                if(fig instanceof ConnectorAdapter){
                   MyLine line = new MyLine(fig.getFigX(), fig.getFigY(), fig.getFigX1(), fig.getFigY1(),
                            ((ConnectorAdapter)fig).getFrom(), ((ConnectorAdapter)fig).getTo());
                    connectorAdapter = new ConnectorAdapter(line);
                    resTreeSet.add(connectorAdapter);
                    controller.setShape(connectorAdapter, new Text(""));
                    observer.update();
                }





            }
            System.out.printf("X1: %s \t X2: %s \n", p.first().getFigX1(), p.first().getFigY1());
            Sheet sheet = (Sheet) sheetManager.tabPane.getTabs().get(2);
            sheetManager.tabPane.getSelectionModel().select(sheet);
            sheetManager.currentSheet = sheet;
            sheetManager.currentTreeSet = resTreeSet;
            sheetManager.currentCanvas = sheet.canvas;
            observer.update();
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }

        /*
        try {

            File file = new File("C:\\dia.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ImageRect.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //jaxbMarshaller.marshal(new Figures(sheetManager.currentTreeSet), file);
            //jaxbMarshaller.marshal(new Figures(sheetManager.currentTreeSet), System.out);
            jaxbMarshaller.marshal(new ImageRect(0,0,0,0), System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }*/

    }

}
