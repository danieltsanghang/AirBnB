import java.util.ArrayList;

public class Borough
{
    // Name of Borough
    private String name;
    // Abbreviated name of Borough
    private String abbrevName;
    // X coordinate for maps
    private int xPos;
    // Y coordinate for maps
    private int yPos;
    // Radius of circle for maps
    private int radius;
    // ArrayList of listings that belong in that Borough
    private ArrayList<AirbnbListing> listings;

    public Borough(String name, String abbrevName, int xPos, int yPos, int diameter)
    {
        // Set Borough name
        this.name = name;
        // Set abbreviated name
        this.abbrevName = abbrevName;
        // Set x coordinate
        this.xPos = xPos;
        // Set y coordinate
        this.yPos = yPos;
        // Set radius (diameter / 2)
        this.radius = diameter / 2;
        // Create new ArrayList to hold listings
        this.listings = new ArrayList<>();
    }

    /**
     * @return Borough name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Abbreviated name of Borough
     */
    public String getAbbrevName() {
        return abbrevName;
    }

    /**
     * @param scale scale factor
     * @return X coordinate to put in MapPanel
     */
    public double getX(double scale) {
        return xPos * scale;
    }

    /**
     * @param scale scale factor
     * @return Y coordinate to put in MapPanel
     */
    public double getY(double scale) {
        return yPos * scale;
    }

    /**
     * @param scale scale factor
     * @return Radius to put in MapPanel
     */
    public double getRadius(double scale) {
        return radius * scale;
    }

    /**
     * @return The ArrayList of listings that belongs this Borough
     */
    public ArrayList<AirbnbListing> getListing() { return listings; }

    /**
     * @param minPrice minimum price for filtering
     * @param maxPrice maximum price for filtering
     * @return number of listings in the borough that fits the price range
     */
    public int getNumberOfListings(int minPrice, int maxPrice)
    {
        int counter = 0;
        for (AirbnbListing listing : listings) {
            if (listing.getPrice() >= minPrice && listing.getPrice() <= maxPrice) {
                // If the listing falls between the price range
                counter++;
            }
        }
        return counter;
    }

    /**
     * Filters the ArrayList of this borough
     * @param minPrice minimum price for filtering
     * @param maxPrice maximum price for filtering
     * @return The filtered ArrayList
     */
    public ArrayList<AirbnbListing> getFilteredListing (int minPrice, int maxPrice)
    {
        ArrayList<AirbnbListing> filtered = new ArrayList<>();
        for (AirbnbListing listing : listings) {
            if (listing.getPrice() >= minPrice && listing.getPrice() <= maxPrice) {
                // If the listing falls between the price range
                filtered.add(listing);
            }
        }
        return filtered;
    }

    /**
     * Adds a new listing to the ArrayList
     * Called when loading csv data
     */
    public void addListings(AirbnbListing toAdd) {
        listings.add(toAdd);
    }
}
