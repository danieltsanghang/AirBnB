
/**
 * Write a description of class MapPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Iterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class MapPanel extends Panel
{
    /**
     * Constructor for objects of class MapPanel
     */
    public MapPanel()
    {
        super();
    }

    private Color getFillColor(int number, int max) {
        int redValue = number / max * 255;
        int blueValue = number / max * 200;
        return Color.rgb(redValue, 255, blueValue);
    }

    public void printTest() {
        HashMap<String, Integer> boroughs = boroughToPropertyNo(0, 10000000);
        Set<String> boroughsList = boroughs.keySet();
        Iterator<String> boroughsIT = boroughsList.iterator();

        while (boroughsIT.hasNext()) {
            System.out.println(boroughsIT.next());
        }
    }

    @Override
    public Pane getPanel(int minPrice, int maxPrice) {
        HashMap<String, Integer> boroughs = boroughToPropertyNo(minPrice, maxPrice);
        Set<String> boroughsList = boroughs.keySet();
        Iterator<String> boroughsIT = boroughsList.iterator();
        
        GridPane gridpane = new GridPane();

        int max = Collections.max(boroughs.values());

        loadBoroughs();

        while (boroughsIT.hasNext()) {
            String boroughName = boroughsIT.next();
            Borough current = matchBoroughs(boroughName);
            Circle circle = new Circle(current.getRadius());
            circle.relocate(current.getX(), current.getY());
            circle.setFill(getFillColor(boroughs.get(boroughName), max));
        }
        return gridpane;
    }
}
