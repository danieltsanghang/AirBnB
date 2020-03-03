
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
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class MapPanel extends Panel
{
    private HashMap<String, Borough> list;

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

    public void loadBoroughs() {
        list = new HashMap<>();
        list.put("", new Borough ("", "", 10, 10, 10));
    }

    public Borough matchBoroughs(String name) {
        if (list.get(name) != null) {
            return list.get(name);
        }
        return null;
    }

    @Override
    public Pane getPanel(int minPrice, int maxPrice) {
        HashMap<String, Integer> boroughs = boroughToPropertyNo(minPrice, maxPrice);
        Set<String> boroughsList = boroughs.keySet();
        Iterator<String> boroughsIT = boroughsList.iterator();
        
        GridPane gridpane = new GridPane();

        int max = Collections.max(boroughs.values());
        int row = 0;
        int col = 0;

        while (boroughsIT.hasNext()) {
            String boroughName = boroughsIT.next();
            Borough current = matchBoroughs(boroughName);
            Circle circle = new Circle(current.getRadius());
            circle.relocate(current.getX(), current.getY());
            circle.setFill(getFillColor(boroughs.get(boroughName), Collections.max(boroughs.values())));
        }
        return gridpane;
    }
}
