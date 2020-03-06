
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

    public String getAbbrevName() {
        return abbrevName;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public int getRadius() {
        return radius;
    }
    
}
