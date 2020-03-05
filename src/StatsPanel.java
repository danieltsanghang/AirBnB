/**
 * Write a description of class StatsPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class StatsPanel extends Panel
{
    /**
     * Constructor for objects of class MapPanel
     */
    public AirbnbDataLoader Data;
    public Filter filter;
    public int boroughCost = 0;

    int NUMBER_OF_REVIEWS = 0;


    public StatsPanel()
    {
        super();
    }

    @Override
    public Pane getPanel(int minPrice, int maxPrice) {

        return null;
    }

    public ArrayList loadListings()
    {
        return Data.load();
    }

    public int getTotalReview()
    {
        ArrayList<AirbnbListing> listings = loadListings();
        Iterator<AirbnbListing> airbnbIT = listings.iterator();
        int i = 0;
        while(airbnbIT.hasNext()){
            AirbnbListing a1 = listings.get(i);
            NUMBER_OF_REVIEWS += a1.getNumberOfReviews();
            i++;
        }
        return NUMBER_OF_REVIEWS;
    }

    public int getAverageReviews()
    {
        return getTotalReview()/getTotalNumber();
    }

    public int getTotalNumber()
    {
        return loadListings().size();
    }

    public int getNumberOfEntireHomes()
    {
        ArrayList<AirbnbListing> listings = loadListings();
        Iterator<AirbnbListing> airbnbIT = listings.iterator();
        int NUMBER_OF_ENTIRE_HOME_APARTMENTS = 0;
        int i = 0;
        while(airbnbIT.hasNext()){
            AirbnbListing a1 = listings.get(i);
            String toTest = a1.getRoom_type();
            if(toTest.equals("Entire home/apt")){
                NUMBER_OF_ENTIRE_HOME_APARTMENTS++;
            }
            i++;
        }
        return NUMBER_OF_ENTIRE_HOME_APARTMENTS;
    }

    public int boroughCost()
    {
        int min = 0;
        int max = 1000000;
        HashMap<String, Integer> boroughs = boroughToPropertyNo(min, max);
        Set<String> boroughsList = boroughs.keySet();
        Iterator<String> boroughsIT = boroughsList.iterator();
        int i = 0;
        while(boroughsIT.hasNext()){
            String boroughName = boroughsIT.next();
            ArrayList<AirbnbListing> listings = filter.getInArea(min, max, boroughName);
            Iterator<AirbnbListing> airbnbIT = listings.iterator();
            int toCompare = 0;
            while(airbnbIT.hasNext()){
                boroughCost = toCompare;
                AirbnbListing airbnb = listings.get(i);
                i++;
                toCompare += airbnb.getPrice();
            }
            if(toCompare/i > boroughCost){
                boroughCost = toCompare/i;
            }
        }

    }

}
