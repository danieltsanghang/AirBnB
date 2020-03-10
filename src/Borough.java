import java.util.ArrayList;

public class Borough
{
    private String name;
    private String abbrevName;
    private int xPos;
    private int yPos;
    private int radius;

    private ArrayList<AirbnbListing> listings;
    /**
     * Constructor for objects of class Borough
     */
    public Borough(String name, String abbrevName, int xPos, int yPos, int diameter)
    {
        this.name = name;
        this.abbrevName = abbrevName;
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = diameter / 2;
        this.listings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAbbrevName() {
        return abbrevName;
    }

    public double getX(double scale) {
        return xPos * scale;
    }

    public double getY(double scale) {
        return yPos * scale;
    }

    public double getRadius(double scale) {
        return radius * scale;
    }

    public int getNumberOfListings(int minPrice, int maxPrice) {
        int counter = 0;
        for (AirbnbListing listing : listings) {
            if (listing.getPrice() >= minPrice && listing.getPrice() <= maxPrice) {
                counter++;
            }
        }
        return counter;
    }

    public ArrayList<AirbnbListing> getListing() {
        return listings;
    }

    public ArrayList<AirbnbListing> getFilteredListing (int minPrice, int maxPrice) {
        ArrayList<AirbnbListing> filtered = new ArrayList<>();
        for (AirbnbListing listing : listings) {
            if (listing.getPrice() >= minPrice && listing.getPrice() <= maxPrice) {
                filtered.add(listing);
            }
        }
        return filtered;
    }

    public void addListings(AirbnbListing toAdd) {
        listings.add(toAdd);
    }
}
