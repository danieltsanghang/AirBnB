import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.layout.*;

public abstract class Panel extends Pane
{
    // CSV loader of Airbnb data
    private AirbnbDataLoader dataLoader;
    // CSV loader of borough data
    private BoroughDataLoader boroughLoader;
    // CSV loader of account data
    protected AccountDataLoader accountLoader;

    // Full list of Airbnb listings in the CSV file
    protected ArrayList<AirbnbListing> listings;
    // Full list of boroughs in London
    protected ArrayList<Borough> boroughs;
    // Full list of created accounts in the system
    protected ArrayList<Account> accounts;
    // A HashMap storing usernames of accounts paired with their favourite properties
    protected HashMap<String, ArrayList<AirbnbListing>> favourites;

    public Panel () throws IOException {
        // Create new CSV loaders
        dataLoader = new AirbnbDataLoader();
        boroughLoader = new BoroughDataLoader();
        accountLoader = new AccountDataLoader();

        // Load data into the lists created above.
        listings = dataLoader.load();
        boroughs = boroughLoader.load();
        accounts = accountLoader.loadAccounts();
        favourites = accountLoader.loadFavourites(listings);

        loadListingsIntoBorough();
    }

    /**
     * Abstract method overrided in every class that extends Panel
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
    public void loadListingsIntoBorough()
    {
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
