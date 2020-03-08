/**
 * Write a description of class StatsPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class StatsPanel extends Panel
{
    private ArrayList<String> statsType;
    private ArrayList<String> stats;

    public StatsPanel()
    {
        super();
        statsType = new ArrayList<>();
        stats = new ArrayList<>();
    }

    private void loadStatType() {
        statsType.add("Average Reviews per Property");
        statsType.add("Total Number of Available Properties");
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

        for (int col = 0; col < 2; col++) {
            for (int row = 0; row < 2; row++) {
                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10));
                vbox.setSpacing(69);

                Text statName = new Text(statsType.get(col * 2 + row));
                statName.setFont(Font.font("Arial", FontWeight.BOLD, 20));

                Text stat = new Text(stats.get(col * 2 + row));
                stat.setFont(Font.font("Arial", FontWeight.BOLD, 20));

                vbox.getChildren().addAll(statName, stat);

                gridPane.add(vbox, col, row);
            }
        }
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        mainPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        return mainPane;
    }

    private String getAverageReviews()
    {
        int number = 0;
        for (AirbnbListing listing : listings) {
            number += listing.getNumberOfReviews();
        }
        return (number/listings.size()) + "";
    }

    private String getTotalAvailableProperties()
    {
        int number = 0;
        for (AirbnbListing listing : listings) {
            if (listing.getAvailability365() == 365) {
                number++;
            }
        }
        return number + "";
    }

    private String getNumberOfEntireHomes()
    {
        int number = 0;
        for (AirbnbListing listing : listings) {
            if (listing.getRoom_type().equalsIgnoreCase("Private room")) {
                number++;
            }
        }
        return number + "";
    }

    private String getMostExpensiveBorough()
    {
        HashMap<Double, String> averages = new HashMap<>();
        for (Borough borough : boroughs) {
            ArrayList<AirbnbListing> listingsInBorough = borough.getListing();
            long sum = 0;
            for (AirbnbListing listing : listingsInBorough) {
                sum += listing.getMinimumNights() * listing.getPrice();
            }
            double average = (double) sum / listingsInBorough.size();
            averages.put(average, borough.getName());
        }
        return averages.get(Collections.max(averages.keySet()));
    }
}
