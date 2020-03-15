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
import javafx.scene.text.Text;

import java.lang.Math;


public class StatsPanel extends Panel {
    private ArrayList<String> statsType;
    private ArrayList<String> stats;
    private Iterator<AirbnbListing> airbnbIT;
    private Iterator<AirbnbListing> ownerIT;
    private ArrayList<AirbnbListing> mostExpensiveListings;
    private ArrayList<AirbnbListing> closestListings;

    public StatsPanel() throws IOException {
        super();
        statsType = new ArrayList<>();
        stats = new ArrayList<>();
    }

    private void loadStatType() {
        statsType.add("Average Reviews per Property");
        statsType.add("Total Available Properties");
        statsType.add("Number of Entire Homes");
        statsType.add("Most Expensive Borough");
        statsType.add("addition 1");
        statsType.add("addition 2");
        statsType.add("addition 3");
        statsType.add("addition 4");
    }

    private void loadStats() {
        stats.add(getAverageReviews());
        stats.add(getTotalAvailableProperties());
        stats.add(getNumberOfEntireHomes());
        stats.add(getMostExpensiveBorough());
        stats.add("value 1");
        stats.add("value 2");
        stats.add("value 3");
        stats.add("value 4");
    }

    @Override
    public Pane getPanel(int minPrice, int maxPrice) {
        BorderPane mainPane = new BorderPane();

        GridPane gridPane = new GridPane();

        loadStatType();
        loadStats();
        getMostPropertyOwner();
        getMostPropertyBorough();
        getMostExpensiveListing();

        for (int col = 0; col < 2; col++) {
            for (int row = 0; row < 2; row++) {
                VBox vbox = new VBox();

                vbox.setPadding(new Insets(10));
                vbox.setSpacing(35);

                ArrayList<String> flipStats = new ArrayList<>();
                flipStats.add(statsType.get(col * 2 + row));
                flipStats.add(statsType.get(col * 2 + row + 4));
                flipStats.add(stats.get(col * 2 + row));
                flipStats.add(stats.get(col * 2 + row + 4));

                Label statName = new Label(flipStats.get(0));
                statName.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                Label stat = new Label(flipStats.get(2));
                stat.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                Pane spacer = new Pane();

                Button backButton = new Button("<");
                backButton.setPrefSize(20,120);
                Button forwardButton = new Button(">");
                forwardButton.setPrefSize(20,120);
                navButton(flipStats, statName, stat, backButton);
                navButton(flipStats, statName, stat, forwardButton);

                vbox.getChildren().addAll(statName, stat,spacer);
                HBox hbox = new HBox(10);
                hbox.setId("statBox");
                hbox.getChildren().addAll(backButton, vbox, forwardButton);
                vbox.setPrefSize(320,150);
//                hbox.setPrefWidth(400);
                gridPane.add(hbox, col, row);
            }
        }
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        mainPane.getStylesheets().add("darkMode.css");
        return mainPane;
    }

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

    private String getAverageReviews() {
        int number = 0;
        for (AirbnbListing listing : listings) {
            number += listing.getNumberOfReviews();
        }
        return (number / listings.size()) + "";
    }

    private String getTotalAvailableProperties() {
        int number = 0;
        for (AirbnbListing listing : listings) {
            if (listing.getAvailability365() == 365) {
                number++;
            }
        }
        return number + "";
    }

    private String getNumberOfEntireHomes() {
        int number = 0;
        for (AirbnbListing listing : listings) {
            if (!listing.getRoom_type().equalsIgnoreCase("Private room")) {
                number++;
            }
        }
        return number + "";
    }

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

    private ArrayList<AirbnbListing> getMostExpensiveListing() {
        airbnbIT = listings.iterator();
        long toCompare = 0;
        AirbnbListing mostExpensiveListing = listings.get(0);
        mostExpensiveListings = new ArrayList<>();
        while (airbnbIT.hasNext()) {
            AirbnbListing toTest = airbnbIT.next();
            long temp = toTest.getPrice();
            if (temp > toCompare) {
                toCompare = temp;
                mostExpensiveListing = toTest;
                mostExpensiveListings.add(mostExpensiveListing);
            } else if (temp == toCompare) {
                mostExpensiveListing = toTest;
                mostExpensiveListings.add(mostExpensiveListing);
            }
        }
        //System.out.println(mostExpensiveListing); //debugging line remove later
        return mostExpensiveListings;
    }

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

    private String getMostPropertyBorough() {
        ArrayList<String> temp = new ArrayList<>();
        String sBoroughToReturn = "yeet";
        long lBoroughToReturn = 0;
        for (Borough borough : boroughs) {
            long boroughToCompare = borough.getNumberOfListings(0, 100000);
            if (boroughToCompare > lBoroughToReturn) {
                lBoroughToReturn = boroughToCompare;
                sBoroughToReturn = borough.getName();
            } else if (boroughToCompare == lBoroughToReturn) {
                sBoroughToReturn += borough.getName();
            }
        }
        //System.out.println(sBoroughToReturn); //debugging line remove later
        return sBoroughToReturn;
    }

    private ArrayList getClosestProperties() {
        double latitudeOfCentre = 51.50853;
        double longitudeOfCentre = -0.12574;
        double latitude = 51.4613;
        double longitude = -0.3037;
        double delta = 0;
        closestListings = new ArrayList<>();
        for (AirbnbListing listing : listings) {
            latitude = listing.getLatitude();
            longitude = listing.getLongitude();
            double latDelta = latitudeOfCentre - latitude;
            double longDelta = longitudeOfCentre - longitude;
            double deltaToCompare = Math.abs(latDelta) + Math.abs(longDelta);
            if (deltaToCompare > delta || deltaToCompare == delta) {
                closestListings.add(listing);
            }
        }
        return closestListings;
    }
}
