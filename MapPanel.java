/**
 * Write a description of class MapPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

 import java.awt.Color;

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
        return new Color(redValue, 255, blueValue);
    }

    @Override
    public Panel getPanel(int min, int max) {

        return null;
    }
}
