import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;

public class UserPanel extends Panel
{
    //Create a boolean to check Whether the selected property is favourite
    private boolean favSelected = true;

    public UserPanel(ArrayList<AirbnbListing> loadedListings) throws IOException, URISyntaxException {
        super(loadedListings);
    }

    /**
     * Parameters not in use
     * @param minPrice Selected minimum price for filtering
     * @param maxPrice Selected maximum price for filtering
     * @return The selected pane
     */
    public Pane getPanel(int minPrice, int maxPrice)
    {
        //Create a scroll pane
        ScrollPane displayPane = new ScrollPane();
        //Set the content of the display pane
        displayPane.setContent(updateFavDisplay(""));

        //Create a new text field for searching
        TextField search = new TextField();

        //Search
        search.textProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                displayPane.setContent(updateFavDisplay(t1));
            }
        });

        //Create button for refresh
        //Set the subsequent acts when the button is clicked.
        Button refresh = new Button("Refresh");
        refresh.setOnAction(e -> {
            displayPane.setContent(updateFavDisplay(""));
            System.out.println("refresh");
        });

        //Create button for favourite selection
        //Set the subsequent acts when the button is clicked.
        Button favouriteSelect = new Button("Favourites");
        favouriteSelect.setOnAction(e -> {
            favSelected = true;
            displayPane.setContent(updateFavDisplay(""));
        });

        //Create button for favourite selection
        //Set the subsequent acts when the button is clicked.
        Button allSelect = new Button("Everything");
        allSelect.setOnAction(e -> {
            favSelected = false;
            displayPane.setContent(updateFavDisplay(""));
        });

        //Create Hbox for top bar
        //Add search and refresh buttons into the top bar
        HBox topBar = new HBox();
        topBar.getChildren().addAll(search, refresh);

        //Create Hbox for button bar
        //Add favourtie selected and allselected button into the button box
        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(favouriteSelect, allSelect);

        //Create Vbox for return pane
        //Add the top bar, button box and display pane into the return pane
        VBox returnPane = new VBox();
        returnPane.getChildren().addAll(topBar, buttonHBox, displayPane);
        //return the return pane
        return returnPane;
    }
    /**
     * @param startingFilter
     * @return VBox
     */
    private VBox updateFavDisplay(String startingFilter)
    {
        //Create a Vbox for storing the content
        VBox content = new VBox();
        //Create an array list that stores the sorted listing
        ArrayList<AirbnbListing> sortedListings;
        //Create a position to check
        int position = 0;

        if (favSelected) {
            sortedListings = favouritesLoader.getFavourites(listings);
        }
        else {
            sortedListings = listings;
        }

        sortedListings.sort(new byName());

        for (AirbnbListing listing : sortedListings) {
            if (listing.getName().startsWith(startingFilter)) {
                //Increment the position by one
                position++;
                final int finalPos = position;

                //Create an array that stores all the  sorted airbnb listing
                final ArrayList<AirbnbListing> finalSortedListings = sortedListings;

                //Create a button viewButton to view tha marked favourites
                Button viewButton = new Button("View");

                //Set the subsequent acts when the view button is clicked
                viewButton.setOnAction(e -> {
                    ApplicationWindow.triggerPropertyWindow(listing, finalSortedListings, finalPos);
                });

                //Create a gridpane that stores the name, id and the view button of the property
                GridPane grid = new GridPane();
                grid.add(new Label("Name: "), 0, 0);
                grid.add(new Label("ID"), 0, 1);
                grid.add(new Label("Click to View: "), 0, 2);
                grid.add(new Label(listing.getName()),1, 0);
                grid.add(new Label(listing.getId()),1, 1);
                grid.add(viewButton, 1, 2);

                grid.getStyleClass().add("style.css");
                //Add the grid to the content pane
                content.getChildren().add(grid);

                if (position > 100) {
                    //return the content pane if position is larger than one hundred
                    return content;
                }
            }
        }
        //return the content pane
        return content;
    }

    private class byName implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}

