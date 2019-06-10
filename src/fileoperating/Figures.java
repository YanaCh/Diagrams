package fileoperating;

import models.figures.Figure;

import javax.xml.bind.annotation.*;
import java.util.TreeSet;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "figures")
public class Figures {



    @XmlElementWrapper(name = "figureList")
    @XmlElement(name = "figure")
    private TreeSet<Figure> figures = new TreeSet<Figure>();

    public Figures(TreeSet<Figure> figures){
        this.figures = figures;
    }

    public TreeSet<Figure> getFigures() {
        return figures;
    }
}
