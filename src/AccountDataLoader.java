import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class AccountDataLoader {

    private static final String[] accountCSVheader = {"Display Name", "Username", "Password"};
    private static final String[] favouritesCSVheader = {"Username", "Listing ID"};

    private ArrayList<Account> accounts;
    private HashMap<String, ArrayList<AirbnbListing>> favourites;

    public AccountDataLoader(){
        accounts = new ArrayList<>();
        favourites = new HashMap<>();
    }

    public ArrayList<Account> loadAccounts() {
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

    public HashMap<String, ArrayList<AirbnbListing>> loadFavourites(ArrayList<AirbnbListing> listings) {
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

    public void newAccount(Account account) throws IOException{
        String[] newline = new String[3];
        newline[0] = account.getDName();
        newline[1] = account.getUserName();
        newline[2] = account.getPassword();

        try(CSVWriter accountWriter = new CSVWriter(
                new FileWriter("src/app-accounts.csv"),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        ) {
            accountWriter.writeNext(accountCSVheader);
            for (Account prevAccount : accounts) {
                String[] line = new String[3];
                line[0] = prevAccount.getDName();
                line[1] = prevAccount.getUserName();
                line[2] = prevAccount.getPassword();
                accountWriter.writeNext(line);
            }
            accountWriter.writeNext(newline);
        }
    }

    public void newFavourite(String username, String listingID) throws IOException {
        String[] newline = new String[2];
        newline[0] = username;
        newline[1] = listingID;

        try (CSVWriter favouriteWriter = new CSVWriter(
                new FileWriter("src/favourites.csv"),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        ) {
            favouriteWriter.writeNext(favouritesCSVheader);
            Set favouriteKeySet = favourites.keySet();
            for (Object key : favouriteKeySet) {
                ArrayList<AirbnbListing> currentArray = favourites.get((String) key);
                for (AirbnbListing listing : currentArray) {
                    String[] line = new String[2];
                    line[0] = username;
                    line[1] = listing.getId();
                    favouriteWriter.writeNext(line);
                }
            }
            favouriteWriter.writeNext(newline);
        }
    }

    public void removeFavourite(String username, String listingID) throws IOException {
        try (CSVWriter favouriteWriter = new CSVWriter(
                new FileWriter("src/favourites.csv"),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        ) {
            favouriteWriter.writeNext(favouritesCSVheader);
            Set favouriteKeySet = favourites.keySet();
            for (Object key : favouriteKeySet) {
                ArrayList<AirbnbListing> currentArray = favourites.get((String) key);
                for (AirbnbListing listing : currentArray) {
                    String[] line = new String[2];
                    line[0] = username;
                    line[1] = listing.getId();
                    if (!line[1].equals(listingID)) {
                        favouriteWriter.writeNext(line);
                    }
                }
            }
        }
    }
}
