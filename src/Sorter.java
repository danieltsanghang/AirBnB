
/**
 * Write a description of class Sorter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sorter
{
    /**
     * Constructor for objects of class Sorter
     */
    public Sorter()
    {

    }

    public ArrayList<AirbnbListing> sortByNumberOfReviews(ArrayList<AirbnbListing> toSort) {
        Collections.sort(toSort, new byNumberOfReviews());
        return toSort;
    }

    public ArrayList<AirbnbListing> sortByPrice(ArrayList<AirbnbListing> toSort) {
        Collections.sort(toSort, new byPrice());
        return toSort;
    }

    public ArrayList<AirbnbListing> sortByHostName(ArrayList<AirbnbListing> toSort) {
        Collections.sort(toSort, new byHostName());
        return toSort;
    }

    private class byNumberOfReviews implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getNumberOfReviews() - o2.getNumberOfReviews();
        }
    }

    private class byPrice implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getPrice() - o2.getPrice();
        }
    }

    private class byHostName implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getHost_name().compareTo(o2.getHost_name());
        }
    }
}

