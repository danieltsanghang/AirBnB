import java.io.IOException;
import java.util.ArrayList;

public class Account
{
    // The displayed name of the account
    private String displayName;
    // The username (unique) of the account
    private String username;
    // The password of the account
    private String password;
    // ArrayList that holds the user's favourite listings
    private static ArrayList<AirbnbListing> favourites;

    // CSV loader for account data
    private AccountDataLoader dataLoader;

    public Account (String displayName, String username, String password)
    {
        // Sets display name
        this.displayName = displayName;
        // Sets username
        this.username = username;
        // Sets password
        this.password = password;
        // Creates new ArrayList that holds the user's favourite listings
        favourites = new ArrayList<>();

        // Creates new instance of Account Data Loader
        dataLoader = new AccountDataLoader();
    }

    /**
     * @return The display name of the account
     */
    public String getDName() { return displayName; }

    /**
     * @return The username of the account
     */
    public String getUserName() { return username; }

    /**
     * @return The password of the account
     */
    public String getPassword() { return password; }

    /**
     * @return The favourite list of the account
     */
    public static ArrayList<AirbnbListing> getFavourites() { return favourites; }

    /**
     * Set the favourite list of the account as the passed in list
     * Used when loading favourite.csv at account creations
     * @param favList A new list that holds the favourite listings of the user
     */
    public void loadFavouriteList(ArrayList<AirbnbListing> favList) { favourites = favList; }

    /**
     * Adds a new listing to the ArrayList
     * Also updates favourite.csv
     * @param fav Listing of choice
     * @throws IOException Unable to update favourite.csv
     */
    public void newFavourite(AirbnbListing fav) throws IOException
    {
        favourites.add(fav);
        dataLoader.newFavourite(getUserName(), fav.getId());
    }

    /**
     * Removes a listing from the ArrayList
     * Also updates favourite.csv
     * @param fav Listing of choice
     * @throws IOException Unable to update favourite.csv
     */
    public void removeFavourite(AirbnbListing fav) throws IOException
    {
        favourites.remove(fav);
        dataLoader.removeFavourite(getUserName(), fav.getId());
    }
}
