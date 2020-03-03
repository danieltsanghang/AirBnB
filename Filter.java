
/**
 * Write a description of class Filter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.HashMap;

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
        ArrayList<AirbnbListing> inRange = getInRange(min, max);
        Iterator<AirbnbListing> it = inRange.iterator();
        while (it.hasNext()) {
            AirbnbListing current = it.next();
            String currentBorough = current.getNeighbourhood();
            if (currentBorough.equals(borough)) {
                listing.add(current);
            }
        }
        return listing;
    }

    /**
     * Gets the highest number of listings in one area
     * @return the highest number
     */
    public int getMax() {
        HashMap <String, Integer> boroughs = new HashMap<>();
        while(listingsIT.hasNext()) {
            AirbnbListing current = listingsIT.next();
            String currentBorough = current.getNeighbourhood();
            if (!boroughs.containsKey(currentBorough)) {
                boroughs.put(currentBorough, 1);
            }
            else {
                int inc = boroughs.get(currentBorough) + 1;
                boroughs.replace(currentBorough, inc);
            }
        }

        return Collections.max(boroughs.values());
    }
}
