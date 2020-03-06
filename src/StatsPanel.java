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
    private AirbnbDataLoader data;
    private Filter filter;
    private AirbnbListing AirbnbListing;
    public int boroughCost = 0;
    private int NUMBER_OF_ENTIRE_HOME_APARTMENTS = 0;

    public int NUMBER_OF_REVIEWS = 0;
    private ArrayList<AirbnbListing> listings;
    private Iterator<AirbnbListing> airbnbIT;
    private Iterator<AirbnbListing> homeListings;
    private Iterator<AirbnbListing> plsWork;


    public StatsPanel()
    {
        super();
    }

    @Override
    public Pane getPanel(int minPrice, int maxPrice) {
        data = new AirbnbDataLoader();
        filter = new Filter();
        listings = data.load();
        airbnbIT = listings.iterator();
        homeListings = listings.iterator();

        GridPane plsWork = new GridPane();


        VBox leftTopP = new VBox();
        leftTopP.setPadding(new Insets(10));
        leftTopP.setSpacing(69);

        VBox rightTopP = new VBox();
        rightTopP.setPadding(new Insets(10));
        rightTopP.setSpacing(69);

        VBox leftBottomP = new VBox();
        leftBottomP.setPadding(new Insets(10));
        leftBottomP.setSpacing(69);

        VBox rightBottomP = new VBox();
        rightBottomP.setPadding(new Insets(10));
        rightBottomP.setSpacing(69);

        // General Statistics title
        Text title = new Text("General Statistics");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 25));
        leftTopP.getChildren().add(title);

        // Average number of reviews/prop
        Text averageReview = new Text("Average Reviews per property:");
        averageReview.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rightTopP.getChildren().add(averageReview);
        Text avrNumber = new Text("" + getAverageReviews());
        avrNumber.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rightTopP.getChildren().add(avrNumber);

        // Total number of properties in London airbnb
        Text totalNumberOfProperties = new Text("Total number of properties on airbnb in London:");
        totalNumberOfProperties.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        leftBottomP.getChildren().add(totalNumberOfProperties);
        Text tnpNumber = new Text("" + getTotalNumber());
        tnpNumber.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        leftBottomP.getChildren().add(tnpNumber);

        // Number of entire homes and apartments
        Text totalNumberOfHomesAndAppts = new Text("Total number of homes and apartments:");
        totalNumberOfHomesAndAppts.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rightBottomP.getChildren().add(totalNumberOfHomesAndAppts);
        Text homeandappts = new Text("" + getNumberOfEntireHomes());
        homeandappts.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rightBottomP.getChildren().add(homeandappts);

        plsWork.add(leftTopP, 0, 0);
        plsWork.add(rightTopP, 2, 0);
        plsWork.add(leftBottomP, 0, 2);
        plsWork.add(rightBottomP, 2, 2);
        return plsWork;
    }

    public int getTotalReview()
    {
        NUMBER_OF_REVIEWS = 0;
        while(airbnbIT.hasNext()){
            AirbnbListing a1 = airbnbIT.next();
            NUMBER_OF_REVIEWS += a1.getNumberOfReviews();
        }
        return NUMBER_OF_REVIEWS;
    }

    public int getAverageReviews()
    {
        return getTotalReview()/getTotalNumber();
    }

    public int getTotalNumber()
    {
        return listings.size();
    }

    public int getNumberOfEntireHomes()
    {
        NUMBER_OF_ENTIRE_HOME_APARTMENTS = 0;

        while(homeListings.hasNext()){
            AirbnbListing a1 = homeListings.next();
            String toTest = a1.getRoom_type();
            if(!toTest.equalsIgnoreCase("Private room")){
                NUMBER_OF_ENTIRE_HOME_APARTMENTS++;
            }
        }
        return NUMBER_OF_ENTIRE_HOME_APARTMENTS;
    }

    /*
    Method returns the borough with the most expensive average cost (taking into account minimum nights)
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
            plsWork = listings.iterator();
            int toCompare = 0;
            while(plsWork.hasNext()){
                boroughCost = toCompare;
                AirbnbListing airbnb = plsWork.next();
                toCompare += airbnb.getPrice() * airbnb.getMinimumNights();
                i++;
            }
            if(toCompare/i > boroughCost){
                boroughCost = toCompare/i;
                toReturn = boroughName;
            }
        }
        return toReturn;
    }
}
