import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.layout.*;

public abstract class Panel extends Pane
{
    private AirbnbDataLoader dataLoader;
    private BoroughDataLoader boroughLoader;
    protected AccountDataLoader accountLoader;

    protected ArrayList<AirbnbListing> listings;
    protected ArrayList<Borough> boroughs;
    protected ArrayList<Account> accounts;

    public Panel () throws IOException {
        dataLoader = new AirbnbDataLoader();
        boroughLoader = new BoroughDataLoader();
        accountLoader = new AccountDataLoader();


        listings = dataLoader.load();
        boroughs = boroughLoader.load();
        accounts = accountLoader.loadAccounts();

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
