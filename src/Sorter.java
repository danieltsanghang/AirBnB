import java.util.HashMap;
import java.util.Comparator;

public class Sorter
{
    private HashMap<String, Comparator<AirbnbListing>> sortingMethods;

    /**
     * Constructor for objects of class Sorter
     */

    public Sorter()
    {
        sortingMethods = new HashMap<>();
        sortingMethods.put("Price - Ascending", new byAscendingPrice());
        sortingMethods.put("Price - Descending", new byDescendingPrice());
        sortingMethods.put("Review - Ascending", new byAscendingReviews());
        sortingMethods.put("Review - Descending", new byDescendingReviews());
        sortingMethods.put("Host Name - Ascending", new byAscendingHostName());
        sortingMethods.put("Host Name - Descending", new byDescendingHostName());
    }

    public Comparator getSoringMethod(String selection) {
        return sortingMethods.get(selection);
    }

    private class byAscendingReviews implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getNumberOfReviews() - o2.getNumberOfReviews();
        }
    }

    private class byDescendingReviews implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o2.getNumberOfReviews() - o1.getNumberOfReviews();
        }
    }

    private class byAscendingPrice implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getPrice() - o2.getPrice();
        }
    }

    private class byDescendingPrice implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o2.getPrice() - o1.getPrice();
        }
    }

    private class byAscendingHostName implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o1.getHost_name().compareTo(o2.getHost_name());
        }
    }

    private class byDescendingHostName implements Comparator<AirbnbListing> {
        @Override
        public int compare(AirbnbListing o1, AirbnbListing o2) {
            return o2.getHost_name().compareTo(o1.getHost_name());
        }
    }
}

