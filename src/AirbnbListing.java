

/**
 * Represents one listing of a property for rental on Airbnb.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 */

public class AirbnbListing
{
    /**
     * The id and name of the individual property
     */
    private String id;
    private String name;
    /**
     * The id and name of the host for this listing.
     * Each listing has only one host, but one host may
     * list many properties.
     */
    private String host_id;
    private String host_name;

    /**
     * The grouped location to where the listed property is situated.
     * For this data set, it is a london borough.
     */
    private String neighbourhood;

    /**
     * The location on a map where the property is situated.
     */
    private double latitude;
    private double longitude;

    /**
     * The type of property, either "Private room" or "Entire Home/apt".
     */
    private String room_type;

    /**
     * The price per night's stay
     */
    private int price;

    /**
     * The minimum number of nights the listed property must be booked for.
     */
    private int minimumNights;
    private int numberOfReviews;

    /**
     * The date of the last review, but as a String
     */
    private String lastReview;
    private double reviewsPerMonth;

    /**
     * The total number of listings the host holds across AirBnB
     */
    private int calculatedHostListingsCount;
    /**
     * The total number of days in the year that the property is available for
     */
    private int availability365;

    public AirbnbListing(String id, String name, String host_id,
                         String host_name, String neighbourhood, double latitude,
                         double longitude, String room_type, int price,
                         int minimumNights, int numberOfReviews, String lastReview,
                         double reviewsPerMonth, int calculatedHostListingsCount, int availability365) {
        this.id = id;
        this.name = name;
        this.host_id = host_id;
        this.host_name = host_name;
        this.neighbourhood = neighbourhood;
        this.latitude = latitude;
        this.longitude = longitude;
        this.room_type = room_type;
        this.price = price;
        this.minimumNights = minimumNights;
        this.numberOfReviews = numberOfReviews;
        this.lastReview = lastReview;
        this.reviewsPerMonth = reviewsPerMonth;
        this.calculatedHostListingsCount = calculatedHostListingsCount;
        this.availability365 = availability365;
    }
    /**
     * @return the id of the individual property
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name of the individual property
     */
    public String getName() {
        return name;
    }

    /**
     * @return the host id of the individual property
     */
    public String getHost_id() {
        return host_id;
    }

    /**
     * @return the host name of the individual property
     */
    public String getHost_name() {
        return host_name;
    }

    /**
     * @return the neighbourhood of the individual property
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * @return the latitude of the individual property
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude of the individual property
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return the room type of the individual property
     */
    public String getRoom_type() {
        return room_type;
    }

    /**
     * @return the price of the individual property
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return the minimum nights of the individual property
     */
    public int getMinimumNights() {
        return minimumNights;
    }

    /**
     * @return the number of reviews of the individual property
     */
    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    /**
     * @return the last review of the individual property
     */
    public String getLastReview() {
        return lastReview;
    }

    /**
     * @return the reviews per month of the individual property
     */
    public double getReviewsPerMonth() {
        return reviewsPerMonth;
    }

    /**
     * @return the calculated number of host listings of the individual property
     */
    public int getCalculatedHostListingsCount() {
        return calculatedHostListingsCount;
    }

    /**
     * @return the total number of days in the year that is available of the property
     */
    public int getAvailability365() {
        return availability365;
    }

    @Override
    public String toString()
    {
        //return a string of statement that includes all the details of the property
        return "AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + host_id + '\'' +
                ", host_name='" + host_name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + room_type + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", lastReview='" + lastReview + '\'' +
                ", reviewsPerMonth=" + reviewsPerMonth +
                ", calculatedHostListingsCount=" + calculatedHostListingsCount +
                ", availability365=" + availability365 +
                '}';
    }
}
