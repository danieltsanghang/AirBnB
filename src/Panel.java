import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.layout.*;

public abstract class Panel extends Pane {
    // CSV loader of favourite data
    protected FavouriteDataLoader favouritesLoader= new FavouriteDataLoader();

    // Full list of Airbnb listings in the CSV file
    protected ArrayList<AirbnbListing> listings;
    // Full list of boroughs in London
    protected ArrayList<Borough> boroughs;
    // List of favourites
    protected ArrayList<String> favouriteID;

    public Panel(ArrayList<AirbnbListing> loadedListings) throws IOException {
        // Create new CSV loaders
        BoroughDataLoader boroughLoader = new BoroughDataLoader();

        // Load data into the lists created above.
        listings = loadedListings;
        boroughs = boroughLoader.load();
        favouriteID = favouritesLoader.loadFavourites();

        loadListingsIntoBorough();
    }

    /**
     * Abstract method overrided in every class that extends Panel
     *
     * @param minPrice Selected minimum price for filtering
     * @param maxPrice Selected maximum price for filtering
     * @return The Pane of each Panel
     */
    public Pane getPanel(int minPrice, int maxPrice) {
        return null;
    }

    /**
     * Each Borough object has an ArrayList that holds listings
     * that belong in that specific borough.
     * This method matches every listing to its own borough and
     * loads it in.
     */
    public void loadListingsIntoBorough() {
        if (listings != null) {
            for (AirbnbListing listing : listings) {
                for (Borough borough : boroughs) {
                    if (listing.getNeighbourhood().equals(borough.getName())) {
                        // If the "neighbourhood" or borough matches
                        borough.addListings(listing);
                    }
                }
            }
        }
    }
}
