import java.util.ArrayList;
import java.util.Collections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class BoroughWindow{

    private ArrayList<String> sortBy;
    private ArrayList<AirbnbListing> sortedListings;

    private Sorter sorter;

    private String boroughSelection;

    private ScrollPane scrollBar;
    private BorderPane popUpPane;

    public BoroughWindow(String name, int minPrice, int maxPrice, ArrayList<Borough> boroughs) {
        sorter = new Sorter();
        sortBy = new ArrayList<>();
        sortedListings = new ArrayList<>();

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

        popUpPane = new BorderPane();
        scrollBar = new ScrollPane();

        buildWindow();
    }

    private void buildWindow(){
        Label sortLabel = new Label("Sort by:");

        System.out.println(sortedListings.size());

        ComboBox sortBox = new ComboBox();
        sortBox.getItems().addAll(sortBy);
        sortBox.valueProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue ov, String t, String t1) {
                if (t1 != null) {
                    for (String sortby : sortBy){
                        if (t1.equals(sortby)) {
                            Collections.sort(sortedListings, sorter.getSoringMethod(sortby));
                            loadBoxes(sortedListings, scrollBar);
                        }
                    }
                }
            }
        });

        GridPane topBar = new GridPane();
        topBar.add(sortLabel,0,0);
        topBar.add(sortBox, 1,0);

        popUpPane.setTop(topBar);
        popUpPane.setMaxHeight(800);

        loadBoxes(sortedListings, scrollBar);
    }

    private void loadBoxes(ArrayList<AirbnbListing> sortedListings, ScrollPane scrollBar) {
        VBox content = new VBox();
        for (int i = 0; i < sortedListings.size(); i++) {
            AirbnbListing listing = sortedListings.get(i);
            final int pos = i;
            Pane box = toBoxes(listing);
            box.setOnMouseClicked(e -> {
                ApplicationWindow.triggerPropertyWindow(listing, sortedListings, pos);
            });
            content.getChildren().add(box);
        }
        content.setStyle("-fx-border-color: black");
        content.setPadding(new Insets(5,5,5,5));
        content.setMinWidth(300);

        scrollBar.setContent(content);
        popUpPane.setCenter(scrollBar);
        popUpPane.setPrefWidth(content.getPrefWidth());
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

    public Pane getPane() {
        return popUpPane;
    }
}