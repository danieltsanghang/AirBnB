import java.util.ArrayList;
import java.util.Collections;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class BoroughWindow{

    private ArrayList<String> sortBy;
    private ArrayList<AirbnbListing> sortedListings;

    private Sorter sorter;

    private String boroughSelection;
    private double longestString;
    private ScrollPane scrollBar;
    private BorderPane popUpPane;
    private VBox hostBox = new VBox();
    private VBox priceBox = new VBox();
    private VBox minStayBox = new VBox();
    private VBox reviewsBox = new VBox();
    private VBox propertyLaunch = new VBox();


    public BoroughWindow(String name, int minPrice, int maxPrice, ArrayList<Borough> boroughs) {
        sorter = new Sorter();
        sortBy = new ArrayList<>();
        sortedListings = new ArrayList<>();
        longestString = 0;
        this.boroughSelection = name;

        for (Borough borough : boroughs) {
            if (borough.getName().equals(boroughSelection)) {
                sortedListings = borough.getFilteredListing(minPrice, maxPrice);
            }
        }

        sortBy.add("Price - Ascending");
        sortBy.add("Price - Descending");
        sortBy.add("Review - Ascending");
        sortBy.add("Review - Descending");
        sortBy.add("Host Name - Ascending");
        sortBy.add("Host Name - Descending");

        Label hostName_title = new Label();
        hostName_title.setText("Host Name");
        hostBox.getChildren().add(hostName_title);

        Label price_title = new Label();
        price_title.setText("Price per night ($)");
        priceBox.getChildren().add(price_title);

        Label minStay_title = new Label();
        minStay_title.setText("Minimum nights");
        minStayBox.getChildren().add(minStay_title);

        Label reviews_title = new Label();
        reviews_title.setText("Number of reviews");
        reviewsBox.getChildren().add(reviews_title);

        Label launchLabel = new Label();
        launchLabel.setText("Click to learn more");
        propertyLaunch.getChildren().add(launchLabel);

        hostBox.setId("propertyBox");
        priceBox.setId("propertyBox");
        minStayBox.setId("propertyBox");
        propertyLaunch.setId("propertyBox");

        popUpPane = new BorderPane();
        scrollBar = new ScrollPane();
        buildWindow();
    }

    private void buildWindow(){

        Label sortLabel = new Label("Sort by:");
        ComboBox<String> sortBox = new ComboBox();
        sortBox.getItems().addAll(sortBy);
        sortBox.valueProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue ov, String t, String t1) {
                if (t1 != null) {
                    for (String sortBy : sortBy){
                        if (t1.equals(sortBy)) {
                            Collections.sort(sortedListings, sorter.getSoringMethod(sortBy));
                            loadBoxes(sortedListings, scrollBar);
                        }
                    }
                }
            }
        });

        BorderPane topBar = new BorderPane();
        topBar.setLeft(sortLabel);
        topBar.setRight(sortBox);
        popUpPane.setTop(topBar);
        loadBoxes(sortedListings, scrollBar);
    }

    private void loadBoxes(ArrayList<AirbnbListing> sortedListings, ScrollPane scrollBar) {

        findWidest(sortedListings);
        for (int i = 0; i < sortedListings.size(); i++) {
            AirbnbListing listing = sortedListings.get(i);
            final int pos = i;
            Button b = toBoxes(listing);
            b.setOnMouseClicked(e -> {
                ApplicationWindow.triggerPropertyWindow(listing, sortedListings, pos);
            });
        }
        HBox content = new HBox();
        content.setSpacing(15);
        content.getChildren().addAll(hostBox,priceBox,minStayBox,reviewsBox, propertyLaunch);
        // popUpPane.setMinWidth(content.getMinWidth());
        scrollBar.setContent(content);
        popUpPane.setCenter(scrollBar);
    }

    private Button toBoxes(AirbnbListing listing) {
        Label hostName= new Label();
        hostName.setText(listing.getHost_name());
        Label price= new Label();
        price.setText(listing.getPrice() + "");
        Label reviews= new Label();
        reviews.setText(listing.getNumberOfReviews() + "");
        Label minStay= new Label();
        minStay.setText(listing.getMinimumNights() + "");
        Button launch = new Button();
        hostName.setMinHeight(26.9);
        reviews.setMinHeight(26.9);
        price.setMinHeight(26.9);
        minStay.setMinHeight(26.9);

        hostBox.getChildren().add(hostName);
        priceBox.getChildren().add(price);
        reviewsBox.getChildren().add(reviews);
        minStayBox.getChildren().add(minStay);
        propertyLaunch.getChildren().add(launch);
        return launch;
    }

    public Pane getPane() {
        popUpPane.setMinSize(500,600);
        return popUpPane;
    }

    private void findWidest (ArrayList<AirbnbListing> sortedListings){
        for (AirbnbListing property : sortedListings)   {
            int lineOne = property.getHost_name().length() ;
            System.out.println(lineOne);
            if (lineOne > longestString)    {
                longestString = lineOne;
            }
        }
        System.out.println(longestString);
    }
}