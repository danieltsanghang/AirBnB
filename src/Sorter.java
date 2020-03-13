import java.util.HashMap;
import java.util.Comparator;

public class Sorter
{
    private HashMap<String, Comparator<AirbnbListing>> sortingMethods;

    public Sorter()
    {
        // Create new HashMap
        sortingMethods = new HashMap<>();
        // Put the sorting methods and comparator has pairs in the HashMap
        sortingMethods.put("Price - Ascending", new byAscendingPrice());
        sortingMethods.put("Price - Descending", new byDescendingPrice());
        sortingMethods.put("Review - Ascending", new byAscendingReviews());
        sortingMethods.put("Review - Descending", new byDescendingReviews());
        sortingMethods.put("Host Name - Ascending", new byAscendingHostName());
        sortingMethods.put("Host Name - Descending", new byDescendingHostName());
    }

    /**
     * @param selection The sorting method, key to the HashMap
     * @return The Comparator for that sorting method
     */
    public Comparator getSoringMethod(String selection) { return sortingMethods.get(selection); }

    /**
     * Comparator that sorts by ascending number of views
     */
    private class byAscendingReviews implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getNumberOfReviews() - o2.getNumberOfReviews();
        }
    }

    /**
     * Comparator that sorts by descending number of views
     */
    private class byDescendingReviews implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o2.getNumberOfReviews() - o1.getNumberOfReviews();
        }
    }

    /**
     * Comparator that sorts by ascending price
     */
    private class byAscendingPrice implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getPrice() - o2.getPrice();
        }
    }

    /**
     * Comparator that sorts by descending price
     */
    private class byDescendingPrice implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o2.getPrice() - o1.getPrice();
        }
    }

    /**
     * Comparator that sorts by host name in alphabetical order
     */
    private class byAscendingHostName implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getHost_name().compareTo(o2.getHost_name());
        }
    }

    /**
     * Comparator that sorts by host name in reverse alphabetical order
     */
    private class byDescendingHostName implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o2.getHost_name().compareTo(o1.getHost_name());
        }
    }
}

