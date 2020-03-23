import java.io.IOException;
import java.util.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.lang.Math;


public class StatsPanel extends Panel {
    //Create an array list that stores statistics types
    private ArrayList<String> statsType= new ArrayList<>();
    //Create an array list that stores statistics
    private ArrayList<String> stats= new ArrayList<>();

    //Create two iterators that store airbnb listings
    private Iterator<AirbnbListing> airbnbIT;
    private Iterator<AirbnbListing> ownerIT;

    public StatsPanel(ArrayList<AirbnbListing> loadedListings) throws IOException {
        super(loadedListings);
    }

    /**
     * Add the strings of the description of the statistics to Stats type array list
     */
    private void loadStatType() {
        statsType.add("Average Reviews per Property");
        statsType.add("Total Available Properties");
        statsType.add("Number of Entire Homes");
        statsType.add("Most Expensive Borough");
        statsType.add("Most Expensive Listing");
        statsType.add("Owner who owns the most properties");
        statsType.add("Borough with the most properties");
        statsType.add("Closest property to the city centre");
    }

    /**
     * Add respective statistics data into statistics
     */
    private void loadStats() {
        stats.add(getAverageReviews());
        stats.add(getTotalAvailableProperties());
        stats.add(getNumberOfEntireHomes());
        stats.add(getMostExpensiveBorough());
        stats.add(getMostExpensiveListing());
        stats.add(getMostPropertyOwner());
        stats.add(getMostPropertyBorough());
        stats.add(getClosestToCheapest());
    }

    @Override
    public Pane getPanel(int minPrice, int maxPrice) {
        //Create a border pane
        BorderPane mainPane = new BorderPane();

        //Create a grid pane
        GridPane gridPane = new GridPane();

        loadStatType();
        loadStats();

        //Create a grid pane for the statistics panel
        for (int col = 0; col < 2; col++) {
            for (int row = 0; row < 2; row++) {

                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10));
                vbox.setSpacing(35);

                //Create array list that stores the
                ArrayList<String> flipStats = new ArrayList<>();
                flipStats.add(statsType.get(col * 2 + row));
                flipStats.add(statsType.get(col * 2 + row + 4));
                flipStats.add(stats.get(col * 2 + row));
                flipStats.add(stats.get(col * 2 + row + 4));

                //Create a label for the first statistics in glip statistics
                Label statName = new Label(flipStats.get(0));
                statName.setFont(Font.font("Arial", FontWeight.BOLD, 20));

                //Create a label for
                Label stat = new Label(flipStats.get(2));
                stat.setFont(Font.font("Arial", FontWeight.BOLD, 20));

                //Create a new pane for
                Pane spacer = new Pane();

                //Create new button for flipping the previous statistics label
                Button backButton = new Button("<");
                backButton.setPrefSize(30,120);
                //Create new button for flipping the next statistics label
                Button forwardButton = new Button(">");
                forwardButton.setPrefSize(30,120);

                navButton(flipStats, statName, stat, backButton);
                navButton(flipStats, statName, stat, forwardButton);

                vbox.getChildren().addAll(statName, stat,spacer);
                HBox hbox = new HBox(10);
                hbox.setId("statBox");
                hbox.getChildren().addAll(backButton, vbox, forwardButton);
                vbox.setPrefSize(320,150);
                gridPane.add(hbox, col, row);
            }
        }
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        mainPane.getStylesheets().add("darkMode.css");
        return mainPane;
    }

    /**
     * @param flipStats for
     * @param statName
     * @param stat
     * @param b
     */
    private void navButton(ArrayList<String> flipStats, Label statName, Label stat, Button b) {
        b.setOnMouseClicked(e -> {
            String currentTitle;
            String currentData;
            if (statName.getText().equals(flipStats.get(0))) {
                currentTitle = flipStats.get(1);
                currentData = flipStats.get(3);
            }
            else {
                currentTitle = flipStats.get(0);
                currentData = flipStats.get(2);
            }
            statName.setText(currentTitle);
            stat.setText(currentData);
        });
    }

    /**
     * @return the string of the average reviews
     */
    private String getAverageReviews() {
        int number = 0;
        for (AirbnbListing listing : listings) {
            number += listing.getNumberOfReviews();
        }
        return (number / listings.size()) + "";
    }

    /**
     * @return the string of the total available number of properties
     */
    private String getTotalAvailableProperties() {
        int number = 0;
        for (AirbnbListing listing : listings) {
            if (listing.getAvailability365() == 365) {
                number++;
            }
        }
        return number + "";
    }

    /**
     * @return the string of thenumber of homes in the borough
     */
    private String getNumberOfEntireHomes() {
        int number = 0;
        for (AirbnbListing listing : listings) {
            if (!listing.getRoom_type().equalsIgnoreCase("Private room")) {
                number++;
            }
        }
        return number + "";
    }

    /**
     * @return the string of the most expensive borough
     */
    private String getMostExpensiveBorough() {
        HashMap<String, Double> averages = new HashMap<>();
        for (Borough borough : boroughs) {
            double sum = 0;
            for (AirbnbListing listing : borough.getListing()) {
                sum += listing.getPrice() * listing.getMinimumNights();
            }
            averages.put(borough.getName(), sum / borough.getListing().size());
        }
        String mostExpensive = "Luigi's castle";
        double price = 0;
        for (String key : averages.keySet()) {
            if (price < averages.get(key)) {
                price = averages.get(key);
                mostExpensive = key;
            }
        }
        return mostExpensive;
    }

    /**
     * @return the string of the most expensive listing
     */
    private String getMostExpensiveListing() {
        airbnbIT = listings.iterator();
        long toCompare = 0;
        AirbnbListing mostExpensiveListing = listings.get(0);
        while (airbnbIT.hasNext()) {
            AirbnbListing toTest = airbnbIT.next();
            long temp = toTest.getPrice();
            if (temp > toCompare) {
                toCompare = temp;
                mostExpensiveListing = toTest;
            } else if (temp == toCompare) {
                mostExpensiveListing = toTest;
            }
        }
        String toReturn = ("Listing Title: " + mostExpensiveListing.getName() + "\n");
        toReturn += ("Listing ID: " + mostExpensiveListing.getId() + "\n");
        toReturn += ("Host ID: " + mostExpensiveListing.getHost_id() + "\n");
        toReturn += ("Price: " + mostExpensiveListing.getPrice() + "/night" + "\n");
        toReturn += ("Minimum nights: " + mostExpensiveListing.getMinimumNights() + "\n");
        return toReturn;
    }

    /**
     * @return the string of the owner who own most properties
     */
    private String getMostPropertyOwner() {
        ownerIT = listings.iterator();
        long numberOfProperties = 0;
        String ownerName = "Homer Simpson";
        String ownerID = "123456789";
        AirbnbListing currentListing;
        while (ownerIT.hasNext()) {
            currentListing = ownerIT.next();
            long toCompare = currentListing.getCalculatedHostListingsCount();
            if (toCompare > numberOfProperties) {
                numberOfProperties = toCompare;
                ownerName = currentListing.getHost_name();
                ownerID = currentListing.getHost_id();
            } else if (toCompare == numberOfProperties) {
                if (!ownerName.equals(currentListing.getHost_name())) {
                    ownerName += currentListing.getHost_name();
                }
            }
        }
        return "Host Name: " + ownerName + "\n" + "Host ID: " + ownerID + "\n" + numberOfProperties;
    }

    /**
     * return the string of the borough name that has most properties
     */
    private String getMostPropertyBorough() {
        ArrayList<String> temp = new ArrayList<>();
        String sBoroughToReturn = "yeet";
        long lBoroughToReturn = 0;
        int i = 0;
        for (Borough borough : boroughs) {
            long boroughToCompare = borough.getNumberOfListings(0, 100000);
            if (boroughToCompare > lBoroughToReturn) {
                lBoroughToReturn = boroughToCompare;
                sBoroughToReturn = borough.getName();
            } else if (boroughToCompare == lBoroughToReturn) {
                sBoroughToReturn += ", " + borough.getName();
                i++;
            }
        }

        String toReturn;
        if(i == 0) {
            toReturn = ("Borough Name: " + sBoroughToReturn);
        } else{
            toReturn = ("Borough Names: " + sBoroughToReturn);
        }
        return toReturn;
    }

    /**
     * @return string
     */
    private String getClosestToCheapest() {
        String returnString = "rainbow road";
        double diff = 0;
        for (AirbnbListing listing : listings) {
            double currentDistance = getDistance(listing.getLongitude(), listing.getLatitude());
            double currentRatio = (listing.getPrice() * listing.getMinimumNights()) / currentDistance;
            double currentDiff = Math.abs(currentRatio - 1);
            if (diff == 0 || diff > currentDiff) {
                diff = currentDiff;
                returnString = listing.getName() + "\nby: " + listing.getHost_name();
            }
        }
        return returnString;
    }

    /**
     * @param lon for the property's longitude
     * @param lat for the property's latitude
     * @return the distance from Centre
     */
    private double getDistance(double lon, double lat) {
        double latitudeOfCentre = 51.50853;
        double longitudeOfCentre = -0.12574;
        double radiusOfEarth = 6371;

        double dLong = Math.toRadians(lon - longitudeOfCentre);
        double dLat = Math.toRadians(lat - latitudeOfCentre);

        // Haversine Formula of calculating distance with longitudes and latitudes
        double step1 = Math.sin(dLat / 2) * Math.sin(dLat / 2);
        double step2 = Math.cos(Math.toRadians(latitudeOfCentre)) * Math.cos(Math.toRadians(lat));
        double step3 = Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double step4 = step1 + step2 * step3;

        return radiusOfEarth * 2 * Math.atan2(Math.sqrt(step4), Math.sqrt(1 - step4)) * 1000;
    }
}