package xmlproc;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import controllers.CurrentState;
import models.figures.Figures;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXML {

  private CurrentState currentState = CurrentState.getInstance();

    public void fill()  {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("diagram");
            doc.appendChild(rootElement);

            for(Figures figure:currentState.treeSet){
                Element fig = doc.createElement("figure");
                rootElement.appendChild(fig);

                Element type = doc.createElement("type");
                //type.appendChild(doc.create);
                fig.appendChild(type);


            }
        }

        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }


    }
}
