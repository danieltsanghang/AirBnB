import java.io.IOException;
import java.util.ArrayList;

public class Account {
    private String displayName;
    private String username;
    private String password;
    private ArrayList<AirbnbListing> favourites;
    private AccountDataLoader dataLoader;

    public Account (String displayName, String username, String password) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.favourites = new ArrayList<>();
        dataLoader = new AccountDataLoader();
    }

    public String getDName() {
        return displayName;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<AirbnbListing> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<AirbnbListing> fav) {
        favourites = fav;
    }

    public void newFavourite(AirbnbListing fav) throws IOException {
        favourites.add(fav);
        dataLoader.newFavourite(getUserName(), fav.getId());
    }

    public void removeFavourite(AirbnbListing fav) throws IOException {
        favourites.add(fav);
        dataLoader.removeFavourite(getUserName(), fav.getId());
    }
}
