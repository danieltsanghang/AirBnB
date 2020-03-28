import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;


public class BoroughWindow{

    //Create an array list sortBy that stores the string of the sorting methods
    private ArrayList<String> sortBy= new ArrayList<>();
    //Create an array list sorted listings that store the sorted listings
    private ArrayList<AirbnbListing> sortedListings = new ArrayList<>();
    //Create a new array list that stores the array list of listings
    private ArrayList<ArrayList<AirbnbListing>> pages = new ArrayList<>();
    //Create a sorter to sort the required borough listing order
    private Sorter sorter= new Sorter();
    //Create an integer that stores the position of the chosen property in the array list
    private int position;

    //Create a string that stores the borough selected
    private String boroughSelection;
    //Create a scroll pane
    private ScrollPane scrollBar = new ScrollPane();
    //Create a borderPane
    private BorderPane popUpPane= new BorderPane();
    //Create a Hbox for the vboxes
    private HBox content = new HBox();
    //Create Vbox for the 5 attributes of the individual property
    private VBox hostBox = new VBox();
    private VBox priceBox = new VBox();
    private VBox minStayBox = new VBox();
    private VBox reviewsBox = new VBox();
    private VBox propertyLaunch = new VBox();

    public BoroughWindow(String name, int minPrice, int maxPrice, ArrayList<Borough> boroughs) {
        //Set the name of the selected borough
        this.boroughSelection = name;

        //Create a for loop to sort the listings of the borough selected
        for (Borough borough : boroughs) {
            if (borough.getName().equals(boroughSelection)) {
                sortedListings = borough.getFilteredListing(minPrice, maxPrice);
            }
        }

        //Add the categories of the sorting order to array list sortBy
        sortBy.add("Price - Ascending");
        sortBy.add("Price - Descending");
        sortBy.add("Review - Ascending");
        sortBy.add("Review - Descending");
        sortBy.add("Host Name - Ascending");
        sortBy.add("Host Name - Descending");

        //Create a label which is the title of the host box and add it to the host box
        Label hostName_title = new Label();
        hostName_title.setText("Host Name");
        hostBox.getChildren().add(hostName_title);

        //Create a label which is the title of the price box and add it to the price box
        Label price_title = new Label();
        price_title.setText("Price per night (£)");
        priceBox.getChildren().add(price_title);

        //Create a label which is the title of the minimum number of days to stay box and add it to its  box
        Label minStay_title = new Label();
        minStay_title.setText("Minimum nights");
        minStayBox.getChildren().add(minStay_title);

        //Create a label which is the title of the reviews box and add it to the reviews box
        Label reviews_title = new Label();
        reviews_title.setText("Number of reviews");
        reviewsBox.getChildren().add(reviews_title);

        //Create a label which is the title of the property launch box and add it to the property launch box
        Label launchLabel = new Label();
        launchLabel.setText("Click to learn more");
        propertyLaunch.getChildren().add(launchLabel);

        //Set the id of the objects for later csv editing
        hostBox.setId("boroughBox");    priceBox.setId("boroughBox");
        minStayBox.setId("boroughBox"); reviewsBox.setId("boroughBox");
        propertyLaunch.setId("boroughBox");

        //Set the padding and alignment of the objects displayed
        content.setPadding(new Insets(0,0,1,20));
        content.setAlignment(Pos.CENTER);       hostBox.setAlignment(Pos.CENTER);
        reviewsBox.setAlignment(Pos.CENTER);    priceBox.setAlignment(Pos.CENTER);
        minStayBox.setAlignment(Pos.CENTER);    propertyLaunch.setAlignment(Pos.CENTER);

        buildWindow();
    }

    private void buildWindow(){

        //Create labels for sorting, next page and previous page in the panel
        Label sortLabel = new Label("Sort by");
        Label pageNumber = new Label();
        Button previous = new Button("Previous");
        Button next = new Button("Next");

        //Create the combobox with sorting options from the arraylist sortBy
        ComboBox<String> sortBox = new ComboBox();
        sortBox.getItems().addAll(sortBy);

        //Subsequent actions to be made when a sorting method is chosen on sort box by the user
        sortBox.valueProperty().addListener((ov, oldTerm, newTerm) -> {
            if (newTerm != null) {
                for (String sortBy : sortBy){
                    if (newTerm.equals(sortBy)) {
                        //Sort the listings correspondingly to the assigned method
                        sortedListings.sort(sorter.getSoringMethod(sortBy));
                        //Refresh the Vbox after the selection
                        refreshVBox(hostBox,priceBox,minStayBox,reviewsBox,propertyLaunch);
                        //Convert the sorted listings into pages
                        toPages(sortedListings);
                        //Convert the first pages into smaller boxes
                        loadBoxes(pages.get(0));
                        position = 0;
                        //Set the text of the page mark
                        pageNumber.setText("Page 1 of " + pages.size());
                    }
                }
            }
        });

        //Display the next page with the according listings displayed when next button is clicked
        next.setOnMouseClicked(e -> {
            if(position < pages.size() - 1) {
                if (pages.get(position + 1) != null) {
                    //Increment the position by 1
                    position++;
                    //Refresh the Vbox after the selection
                    refreshVBox(hostBox, priceBox, minStayBox, reviewsBox, propertyLaunch);
                    //Convert the page with the new position into smaller boxes
                    loadBoxes(pages.get(position));
                    //Increment the page mark by one
                    pageNumber.setText("Page " + (position + 1) + " of " + pages.size());
                }
            }
        });

        //Display the previous page with the according listings displayed when previous button is clicked
        previous.setOnMouseClicked(e -> {
            if(position != 0) {
                if (pages.get(position - 1) != null) {
                    //Decrement the position by 1
                    position--;
                    //Refresh the Vbox after the selection
                    refreshVBox(hostBox, priceBox, minStayBox, reviewsBox, propertyLaunch);
                    //Convert the page with the new position into smaller boxes
                    loadBoxes(pages.get(position));
                    //Decrement the page mark by one
                    pageNumber.setText("Page " + (position + 1) + " of " + pages.size());

                }
            }
        });

        //Convert the sorted listings into pages
        toPages(sortedListings);
        //Convert the first page into smaller boxes
        loadBoxes(pages.get(0));

        content.setSpacing(15);
        content.getChildren().addAll(hostBox,priceBox,minStayBox,reviewsBox, propertyLaunch);
        scrollBar.setContent(content);
        scrollBar.setPadding(new Insets(3));

        //Create a Hbox bot bar
        //Add buttons previous, next and label page number on the bot bar
        HBox botBar = new HBox();
        botBar.setId("navBarBorough");
        pageNumber.setText("Page 1 of " + pages.size());
        botBar.getChildren().addAll(previous, pageNumber, next);

        //Create a Hbox top bar
        //Add the sort label and the combobox onto the top bar
        HBox topBar = new HBox();
        topBar.getChildren().addAll(sortLabel,sortBox);

        //Set the pop up pane with top, scroll and bot bars
        popUpPane.setTop(topBar);
        popUpPane.setCenter(scrollBar);
        popUpPane.setBottom(botBar);
    }

    /**
     * @param sortedListings for creating the pages for displaying them
     */
    private  void toPages (ArrayList<AirbnbListing> sortedListings)   {
        pages.clear();
        ArrayList<AirbnbListing> currentList = new ArrayList<>(sortedListings);
        int pageNumber = (int) Math.ceil(currentList.size() / 25.0);
        for (;pageNumber > 0;pageNumber--) {
            ArrayList<AirbnbListing> page = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                if(!currentList.isEmpty()) {
                    page.add(currentList.get(0));
                    currentList.remove(0);
                }
            }
            pages.add(page);
        }
    }

    /**
     * @param sortedListings for additional boxes added when clicked
     */
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

    /**
     * @param listing as a borough listing
     * @return Button of a borough listing
     */
    private Button toBoxes(AirbnbListing listing) {

        Button launch = new Button("View");
        launch.setPadding(new Insets(0, 40, 0, 40));

        Pane hostCell = new Pane(new Label(listing.getHost_name()));
        Pane priceCell = new Pane(new Label(listing.getPrice() + ""));
        Pane reviewCell = new Pane(new Label(listing.getNumberOfReviews() + ""));
        Pane minStayCell = new Pane(new Label(listing.getMinimumNights() + ""));
        Pane launchCell = new Pane(launch);

        hostCell.getStyleClass().add("cell");   priceCell.getStyleClass().add("cell");
        reviewCell.getStyleClass().add("cell"); minStayCell.getStyleClass().add("cell");
        launchCell.getStyleClass().add("cell");

        hostBox.getChildren().add(hostCell);      priceBox.getChildren().add(priceCell);
        reviewsBox.getChildren().add(reviewCell); minStayBox.getChildren().add(minStayCell);
        propertyLaunch.getChildren().add(launchCell);

        return launch;
    }

    /**
     * @return the the pop up pane which is set with a minimum width and maximum height
     */
    public Pane getPane() {
        popUpPane.setMinWidth(700);
        popUpPane.setMaxHeight(550);
        return popUpPane;
    }

    //refresh the vbox
    private void refreshVBox(VBox c1, VBox c2, VBox c3, VBox c4, VBox c5) {
        List<VBox> list = Arrays.asList(c1,c2,c3,c4,c5);
        for(VBox v : list) {
            if (v.getChildren().size() > 1) {
                v.getChildren().remove(1, v.getChildren().size());
            }
        }
    }
}