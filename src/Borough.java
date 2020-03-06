
/**
 * Write a description of class Borough here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.HashMap;

public class Borough
{
    private String name;
    private String abbrevName;
    private int xPos;
    private int yPos;
    private int radius;
    HashMap <String, Borough> list;
    /**
     * Constructor for objects of class Borough
     */
    public Borough(String name, String abbrevName, int xPos, int yPos, int diameter)
    {
        this.name = name;
        this.abbrevName = abbrevName;
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = diameter / 2;
    }

    public String getName() {
        return name;
    }

    public String getAbbrevName() {
        return abbrevName;
    }

    public double getX(double scale) {
        return xPos * scale;
    }

    public double getY(double scale) {
        return yPos * scale;
    }

    public double getRadius(double scale) {
        return radius * scale;
    }

}
