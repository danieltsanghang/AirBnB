import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserPanel extends Panel
{
    //Whether the selected property is favourite
    private boolean favSelected;
    //Load Sorter
    private Sorter sorter;

    public UserPanel() throws IOException
    {
        super();
        favSelected = true;
        sorter = new Sorter();
    }

    /**
     * Parameters not in use
     * @param minPrice Selected minimum price for filtering
     * @param maxPrice Selected maximum price for filtering
     * @return The selected pane
     */
    public Pane getPanel(int minPrice, int maxPrice)
    {
        ScrollPane displayPane = new ScrollPane();
        displayPane.setContent(updateFavDisplay(""));

        TextField search = new TextField();
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 != null) {
                    displayPane.setContent(updateFavDisplay(t1));
                }
            }
        });

        Button refresh = new Button("Refresh");
        refresh.setOnAction(e -> {
            displayPane.setContent(updateFavDisplay(""));
        });
        Button favouriteSelect = new Button("Favourites");
        favouriteSelect.setOnAction(e -> {
            favSelected = true;
            displayPane.setContent(updateFavDisplay(""));
        });
        Button allSelect = new Button("Everything");
        allSelect.setOnAction(e -> {
            favSelected = false;
            displayPane.setContent(updateFavDisplay(""));
        });

        HBox topBar = new HBox();
        topBar.getChildren().addAll(search, refresh);

        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(favouriteSelect, allSelect);

        VBox returnPane = new VBox();
        returnPane.getChildren().addAll(topBar, buttonHBox, displayPane);
        return returnPane;
    }

    private VBox updateFavDisplay(String startingFilter)
    {
        VBox content = new VBox();
        ArrayList<AirbnbListing> sortedListings;
        int position = 0;

        if (favSelected) {
            sortedListings = favouritesLoader.getFavourites(listings);
        }
        else {
            sortedListings = listings;
        }

        Collections.sort(sortedListings, new byName());

        for (AirbnbListing listing : sortedListings) {
            if (listing.getName().startsWith(startingFilter)) {
                position++;
                final int finalPos = position;
                final ArrayList<AirbnbListing> finalSortedListings = sortedListings;

                Button viewButton = new Button();
                viewButton.setOnAction(e -> { ;
                    ApplicationWindow.triggerPropertyWindow(listing, finalSortedListings, finalPos);
                });

                GridPane grid = new GridPane();
                grid.add(new Label("Name: "), 0, 0);
                grid.add(new Label("ID"), 0, 1);
                grid.add(new Label("Click to View: "), 0, 2);
                grid.add(new Label(listing.getName()),1, 0);
                grid.add(new Label(listing.getId()),1, 1);
                grid.add(viewButton, 1, 2);

                content.getChildren().add(grid);

                if (position > 100) {
                    return content;
                }
            }
        }
        return content;
    }

    private class byName implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}

