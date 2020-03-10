import java.util.ArrayList;
import javafx.scene.layout.*;

public abstract class Panel extends Pane
{
    private AirbnbDataLoader dataLoader;
    private BoroughDataLoader boroughLoader;

    protected ArrayList<AirbnbListing> listings;
    protected ArrayList<Borough> boroughs;

    public Panel () {
        dataLoader = new AirbnbDataLoader();
        boroughLoader = new BoroughDataLoader();
        listings = dataLoader.load();
        boroughs = boroughLoader.load();
        loadListingsIntoBorough();
    }

    public Pane getPanel(int minPrice, int maxPrice) {
        return null;
    }

    public void loadListingsIntoBorough() {
        for (AirbnbListing listing : listings) {
            for (Borough borough : boroughs) {
                if (listing.getNeighbourhood().equals(borough.getName())) {
                    borough.addListings(listing);
                }
            }
        }
    }
}
