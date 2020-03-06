import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

public class listingBorough extends Pane{
    private BorderPane container = new BorderPane();
    private ArrayList<Pane> matchedListingPanes = new ArrayList();
    private ArrayList<AirbnbListing> matchedListings = new ArrayList<>();
    private String name;
    private int min = ApplicationWindow.getMinPrice();;
    private int max = ApplicationWindow.getMaxPrice();;
    private ScrollPane main = new ScrollPane();

    public listingBorough(String name) {
        this.name = name;
        AirbnbDataLoader data = new AirbnbDataLoader();
        ArrayList<AirbnbListing> listings = data.load();
        for (AirbnbListing property : listings) {
            int price = property.getPrice();
            if (property.getNeighbourhood().equals(name)
                    && min <= price && price <= max
            ) {
                matchedListings.add(property);
                matchedListingPanes.add(this.toBoxes(property));
            }
        }

        Label sortLabel = new Label("Sort by:");
        ComboBox sortBox = new ComboBox();
        sortBox.getItems().addAll("Price","Review", "Host Name");


        sortBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t1 != null)  {
                    Sorter sorting = new Sorter();
                    if (t1.equals("Price")) {
                        matchedListings = sorting.sortByPrice(matchedListings);
                        matchedListingPanes.clear();
                        for (AirbnbListing list: matchedListings) {
                            matchedListingPanes.add(toBoxes(list));
                        }
                        createVbox(matchedListingPanes,main );

                    }

                    else if (t1.equals("Review")) {
                        matchedListings = sorting.sortByNumberOfReviews(matchedListings);
                        matchedListingPanes.clear();
                        for (AirbnbListing list: matchedListings) {
                            matchedListingPanes.add(toBoxes(list));
                        }
                        createVbox(matchedListingPanes,main );

                    }
                    else if (t1.equals("Host Name")) {
                        matchedListings = sorting.sortByHostName(matchedListings);
                        matchedListingPanes.clear();
                        for (AirbnbListing list: matchedListings) {
                            matchedListingPanes.add(toBoxes(list));
                        }
                        createVbox(matchedListingPanes,main );

                    }



                }

            }
        });

        BorderPane topBar = new BorderPane();
        GridPane sort = new GridPane();
        sort.add(sortLabel,0,0);
        sort.add(sortBox, 1,0);
        topBar.setRight(sort);
        container.setTop(topBar);


        createVbox(matchedListingPanes, main);
    }
    private void createVbox(ArrayList<Pane> Panes, ScrollPane smain){
        VBox vbox = new VBox();
        for (Pane pane : Panes)   {
            vbox.getChildren().add(pane);
        }
        vbox.setStyle("-fx-border-color: black");
        vbox.setPadding(new Insets(5,5,5,5));
        vbox.setMinWidth(300);
        smain.setContent(vbox);
        container.setCenter(smain);
        container.setPrefWidth(vbox.getPrefWidth());
    }

    private Pane toBoxes(AirbnbListing listing) {
        GridPane box = new GridPane();
        Label hostName= new Label();
        Label price= new Label();
        Label reviews= new Label();
        Label minStay= new Label();
        hostName.setText("Host:" + listing.getHost_name());
        price.setText("$ " + listing.getPrice());
        reviews.setText("Number of reviews: " + listing.getNumberOfReviews());
        minStay.setText("Minimum nights: " + listing.getMinimumNights());

        hostName.setAlignment(Pos.CENTER_LEFT);
        price.setAlignment(Pos.CENTER_RIGHT);
        box.add(hostName,0,0);
        box.add(price,1,0);
        box.add(reviews,0,1);
        box.add(minStay,1,1);

        box.setId(listing.getId());
        box.setStyle("-fx-border-color: black");
        box.setVgap(3);
        box.setHgap(20);
        return box;
    }
    public void button ()   {
        for (Pane pane : matchedListingPanes)   {
            pane.setOnMouseClicked(e ->
                    propertyMatch(pane.getId())
            );
        }
    }
    public void propertyMatch (String id)   {
        for (AirbnbListing listing : matchedListings) {
            if (id.equals(listing.getId())) {
                this.propertyToPane(listing);
                ApplicationWindow.popUpProperty(listing, matchedListings);
            }
        }
    }



    public void propertyToPane (AirbnbListing listing)  {
        BorderPane pane = new BorderPane();
        Label text = new Label();
        text.setText(listing.toString());
        pane.setCenter(text);
    }

    public Pane getPane()   {
        return container;
    }

    public String getName() {
        return name;
    }
}
