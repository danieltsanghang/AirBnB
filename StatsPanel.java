/**
 * Write a description of class StatsPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javafx.scene.layout.*;

public class StatsPanel extends Panel
{
    /**
     * Constructor for objects of class MapPanel
     */
    public StatsPanel()
    {
        super();
    }

    @Override
    public Pane getPanel(int minPrice, int maxPrice) {
        HashMap<String, Integer> boroughStatistics = statisticsForBoroughs();
        Set<String> statsList = boroughStatistics.keySet();
        
        
    }
}
