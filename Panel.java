/**
 * Abstract class Panel - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
import java.util.Iterator;
import java.util.HashMap;
import javafx.scene.layout.*;

public abstract class Panel
{
    private Filter filter;

    public Pane getPanel(int minPrice, int maxPrice){
        return null;
    }

    /**
     * Gets the highest number of listings in one area
     * @return the highest number
     */
    protected HashMap<String, Integer> boroughToPropertyNo (int minPrice, int maxPrice) {
        HashMap <String, Integer> boroughs = new HashMap<>();
        Iterator<AirbnbListing> it = filter.getInRange(minPrice, maxPrice).iterator();
        while(it.hasNext()) {
            AirbnbListing current = it.next();
            String currentBorough = current.getNeighbourhood();
            if (!boroughs.containsKey(currentBorough)) {
                boroughs.put(currentBorough, 1);
            }
            else {
                int inc = boroughs.get(currentBorough) + 1;
                boroughs.replace(currentBorough, inc);
            }
        }
        return boroughs;
    }
}
