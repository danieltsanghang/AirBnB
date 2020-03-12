import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class BoroughWindow{

    private ArrayList<String> sortBy;
    private ArrayList<AirbnbListing> sortedListings;
    private ArrayList<ArrayList<AirbnbListing>> pages = new ArrayList<>();
    private int position;
    private Sorter sorter;

    private String boroughSelection;
    private double longestString;
    private ScrollPane scrollBar;
    private BorderPane popUpPane;
    private HBox content = new HBox();
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

        hostBox.setId("boroughBox");
        priceBox.setId("boroughBox");
        minStayBox.setId("boroughBox");
        reviewsBox.setId("boroughBox");
        propertyLaunch.setId("boroughBox");

        hostBox.setAlignment(Pos.CENTER_RIGHT);
        reviewsBox.setAlignment(Pos.CENTER);
        priceBox.setAlignment(Pos.CENTER);
        minStayBox.setAlignment(Pos.CENTER);
        propertyLaunch.setAlignment(Pos.CENTER);

        popUpPane = new BorderPane();
        scrollBar = new ScrollPane();
        buildWindow();
    }

    private void buildWindow(){
        Label sortLabel = new Label("Sort by");
        Button previous = new Button("Previous");
        Button next = new Button("Next");
        ComboBox<String> sortBox = new ComboBox();
        sortBox.getItems().addAll(sortBy);
        sortBox.valueProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue ov, String t, String t1) {
                if (t1 != null) {
                    for (String sortBy : sortBy){
                        if (t1.equals(sortBy)) {
                            Collections.sort(sortedListings, sorter.getSoringMethod(sortBy));
                            refreshVBox(hostBox,priceBox,minStayBox,reviewsBox,propertyLaunch);
                            toPages(sortedListings);
                            loadBoxes(pages.get(0));
                            position = 0;
                        }
                    }
                }
            }
        });

        next.setOnMouseClicked(e -> {
            if(position < pages.size() - 1) {
                if (pages.get(position + 1) != null) {
                    position++;
                    refreshVBox(hostBox, priceBox, minStayBox, reviewsBox, propertyLaunch);
                    loadBoxes(pages.get(position));
                }
            }
        });

        previous.setOnMouseClicked(e -> {
            if(position != 0) {
                if (pages.get(position - 1) != null) {
                    position--;
                    refreshVBox(hostBox, priceBox, minStayBox, reviewsBox, propertyLaunch);
                    loadBoxes(pages.get(position));
                }
            }
        });

        content.setSpacing(15);
        content.getChildren().addAll(hostBox,priceBox,minStayBox,reviewsBox, propertyLaunch);
        scrollBar.setContent(content);

        HBox botBar = new HBox();
        botBar.getChildren().addAll(previous, next);
        HBox topBar = new HBox();
        topBar.getChildren().addAll(sortLabel,sortBox);

        popUpPane.setTop(topBar);
        popUpPane.setCenter(scrollBar);
        popUpPane.setBottom(botBar);
        toPages(sortedListings);
        loadBoxes(pages.get(0));
    }

    private  void toPages (ArrayList<AirbnbListing> sortedListings)   {
        pages.clear();
        ArrayList<AirbnbListing> currentList = new ArrayList<>();
        currentList.addAll(sortedListings);
        int pageNumber = (int) Math.ceil(currentList.size() / 25.0);
        for (;pageNumber > 0;pageNumber--) {
            ArrayList<AirbnbListing> page = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                if(!currentList.isEmpty() && currentList.size() >= 1) {
                    page.add(currentList.get(0));
                    currentList.remove(0);
                }
            }
            pages.add(page);
        }
    }

    private void loadBoxes(ArrayList<AirbnbListing> sortedListings) {
        for (int i = 0; i < sortedListings.size(); i++) {
            AirbnbListing listing = sortedListings.get(i);
            final int pos = i;
            Button b = toBoxes(listing);
            b.setOnMouseClicked(e -> {
                ApplicationWindow.triggerPropertyWindow(listing, sortedListings, pos);
            });
        }
    }

    private Button toBoxes(AirbnbListing listing) {

        Button launch = new Button();

        Pane hostCell = new Pane(new Label(listing.getHost_name()));
        Pane priceCell = new Pane(new Label(listing.getPrice() + ""));
        Pane reviewCell = new Pane(new Label(listing.getNumberOfReviews() + ""));
        Pane minStayCell = new Pane(new Label(listing.getMinimumNights() + ""));
        Pane launchCell = new Pane(launch);

        hostCell.getStyleClass().add("cell");
        priceCell.getStyleClass().add("cell");
        reviewCell.getStyleClass().add("cell");
        minStayCell.getStyleClass().add("cell");
        launchCell.getStyleClass().add("cell");

        hostBox.getChildren().add(hostCell);
        priceBox.getChildren().add(priceCell);
        reviewsBox.getChildren().add(reviewCell);
        minStayBox.getChildren().add(minStayCell);
        propertyLaunch.getChildren().add(launchCell);

        return launch;
    }

    public Pane getPane() {
        popUpPane.setMinWidth(560);
        popUpPane.setMaxHeight(500);
        return popUpPane;
    }
    private void refreshVBox(VBox c1, VBox c2, VBox c3, VBox c4, VBox c5)    {
        List<VBox> list = Arrays.asList(c1,c2,c3,c4,c5);
        for(VBox v : list) {
            if (v.getChildren().size() > 1) {
                v.getChildren().remove(1, v.getChildren().size());
            }
        }
    }
}