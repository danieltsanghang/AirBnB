import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import com.opencsv.CSVReader;

import javax.swing.text.html.HTMLDocument;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AccountDataLoader {

    public ArrayList<Account> loadAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        try{
            URL url = getClass().getResource("app-accounts.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String display_name = line[0];
                String username = line[1];
                String password = line[2];

                Account account = new Account(display_name, username, password);
                accounts.add(account);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        return accounts;
    }

    private HashMap<String, ArrayList<AirbnbListing>> loadFavourites(ArrayList<AirbnbListing> listings) {
        HashMap<String, ArrayList<AirbnbListing>> favourites = new HashMap<>();
        try{
            URL url = getClass().getResource("favourites.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String username = line[0];
                String listingID = line[2];

                Iterator<AirbnbListing> listingIT = listings.iterator();
                boolean match = false;
                while (listingIT.hasNext() && !match) {
                    AirbnbListing listing = listingIT.next();
                    if (listingID.equals(listing.getId())) {
                        if (favourites.get(username) != null) {
                            ArrayList<AirbnbListing> personalListings = favourites.get(username);
                            personalListings.add(listing);
                            favourites.replace(username, personalListings);
                        }
                        else {
                            ArrayList<AirbnbListing> personalListings = new ArrayList<>();
                            personalListings.add(listing);
                            favourites.put(username, personalListings);
                        }
                        match = true;
                    }
                }
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        return favourites;
    }
}
