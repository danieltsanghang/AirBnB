
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
            String borough = boroughsIT.next();
            Circle circle = new Circle(10, getFillColor(boroughs.get(borough), max));
            gridpane.add(new Button(borough, circle), row, col);
            if (++col > 6) {
                row++;
                col = 0;
            }
        }
        return gridpane;
    }
}
