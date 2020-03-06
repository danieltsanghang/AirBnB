/**
 * Write a description of class StatsPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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

        FlowPane leftTopP = new FlowPane();
        leftTopP.setHgap(0);
        leftTopP.setVgap(0);
        leftTopP.setPadding(new Insets(10, 10, 10, 10));

        // General Statistics title
        Text title = new Text("General Statistics");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 25));
        leftTopP.getChildren().add(title);

        // Average number of reviews/prop
        Text averageReview = new Text("Average Reviews per property: " + getAverageReviews());
        averageReview.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        leftTopP.getChildren().add(averageReview);

        // Total number of properties in London airbnb
        Text totalNumberOfProperties = new Text("Total number of properties on airbnb in London: " + getTotalNumber());
        totalNumberOfProperties.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        leftTopP.getChildren().add(totalNumberOfProperties);

        // Number of entire homes and apartments
        Text totalNumberOfHomesAndAppts = new Text("Total number of homes and apartments: " + getNumberOfEntireHomes());
        totalNumberOfHomesAndAppts.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        leftTopP.getChildren().add(totalNumberOfHomesAndAppts);



        return leftTopP;
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

    /*
    Method returns the borough with the most expensive average cost (taking into account
     */
    public String boroughCost()
    {
        int min = 0;
        int max = 1000000;
        HashMap<String, Integer> boroughs = boroughToPropertyNo(min, max);
        Set<String> boroughsList = boroughs.keySet();
        Iterator<String> boroughsIT = boroughsList.iterator();
        String toReturn = "";
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
                toCompare += airbnb.getPrice() * airbnb.getMinimumNights();
            }
            if(toCompare/i > boroughCost){
                boroughCost = toCompare/i;
                toReturn = boroughName;
            }
        }
        return toReturn;
    }
}
