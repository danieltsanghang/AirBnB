
/**
 * Write a description of class Filter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Filter
{
    private AirbnbDataLoader dataLoader;
    private ArrayList<AirbnbListing> listings;
    private Iterator<AirbnbListing> listingsIT;

    /**
     * Constructor for objects of class Filter
     */
    public Filter()
    {
        dataLoader = new AirbnbDataLoader();
        listings = dataLoader.load();
        listingsIT = listings.iterator();
    }

    /**
     * Gets an ArrayList of listing that's within the price range
     * @param min minimum value for price
     * @param max maximum value for price
     * @return an Arraylist
     */
    public ArrayList<AirbnbListing> getInRange(int min, int max) {
        ArrayList<AirbnbListing> listing = new ArrayList<>();
        listingsIT = listings.iterator();
        while (listingsIT.hasNext()) {
            AirbnbListing current = listingsIT.next();
            int currentPrice = current.getPrice();
            if (currentPrice > min && currentPrice < max) {
                listing.add(current);
            }
        }
        return listing;
    }

    /**
     * Gets an ArrayList of listing from a borough that's within the price range
     * @param min minimum value for price
     * @param max maximum value for price
     * @param borough the specific borough
     * @return an ArrayList
     */
    public ArrayList<AirbnbListing> getInArea(int min, int max, String borough) {
        ArrayList<AirbnbListing> listing = new ArrayList<>();
        Iterator<AirbnbListing> it = getInRange(min, max).iterator();
        while (it.hasNext()) {
            AirbnbListing current = it.next();
            String currentBorough = current.getNeighbourhood();
            if (currentBorough.equals(borough)) {
                listing.add(current);
            }
        }
        return listing;
    }
}
