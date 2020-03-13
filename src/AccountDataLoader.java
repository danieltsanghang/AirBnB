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

    // The first row (labels) in account.csv
    private static final String[] accountCSVheader = {"Display Name", "Username", "Password"};
    // The first row (labels) in favourite.csv
    private static final String[] favouritesCSVheader = {"Username", "Listing ID"};

    // An ArrayList that holds every account created, including previous accounts
    private ArrayList<Account> accounts;
    // A HashMap username and its matching list of favourite listings in pairs
    private HashMap<String, ArrayList<AirbnbListing>> favourites;

    public AccountDataLoader()
    {
        accounts = new ArrayList<>();
        favourites = new HashMap<>();
    }

    /**
     * Reads account.csv and loads every account in the csv into an ArrayList
     * @return An account filled ArrayList
     */
    public ArrayList<Account> loadAccounts()
    {
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

                // Creation of new account based on the loaded csv data
                Account account = new Account(display_name, username, password);
                // Adds newly created account to accounts list
                accounts.add(account);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        return accounts;
    }

    /**
     * Reads favourites.csv and loads every favourited entry into a HashMap
     * It matches the username to the actual account and fills the account's favourite list as well
     * @param listings The Full list of Airbnb listings
     * @return A filled HashMap
     */
    public HashMap<String, ArrayList<AirbnbListing>> loadFavourites(ArrayList<AirbnbListing> listings)
    {
        try{
            URL url = getClass().getResource("favourites.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String username = line[0];
                String listingID = line[1];

                Iterator<AirbnbListing> listingIT = listings.iterator();
                boolean match = false;
                while (listingIT.hasNext() && !match) {
                    AirbnbListing listing = listingIT.next();
                    if (listingID.equals(listing.getId())) {
                        if (favourites.get(username) != null) {
                            // If the username already exists and has a non empty favourite list
                            ArrayList<AirbnbListing> personalListings = favourites.get(username);
                            // Add matched listing into ArrayList
                            personalListings.add(listing);
                            // Set the newly updated ArrayList as the favourite list of the account
                            getAccount(username).loadFavouriteList(personalListings);
                            // Update the HashMap by replacing the old pair
                            favourites.replace(username, personalListings);
                        }
                        else {
                            // If the username does not exist, create new ArrayList
                            ArrayList<AirbnbListing> personalListings = new ArrayList<>();
                            // Add matched listing into newly created ArrayList
                            personalListings.add(listing);
                            // Set the newly created ArrayList as the favourite list of the account
                            getAccount(username).loadFavouriteList(personalListings);
                            // Update the HashMap by adding this newly created pair
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

    /**
     * Writes the new account into account.csv
     * @param account The newly created account
     * @throws IOException Unable to write into csv
     */
    public void newAccount(Account account) throws IOException
    {
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
            // Write the labels into account.csv
            accountWriter.writeNext(accountCSVheader);
            // Write all the existing accounts back into account.csv
            for (Account prevAccount : accounts) {
                String[] line = new String[3];
                line[0] = prevAccount.getDName();
                line[1] = prevAccount.getUserName();
                line[2] = prevAccount.getPassword();
                accountWriter.writeNext(line);
            }
            // Write the newly created account into account.csv
            accountWriter.writeNext(newline);
        }
    }

    /**
     * Writes the new favourited listing into favourite.csv
     * @param username The current account's username
     * @param listingID The ID of the listing to be added
     * @throws IOException Unable to write into csv
     */
    public void newFavourite(String username, String listingID) throws IOException
    {
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
            // Write the labels into favourite.csv
            favouriteWriter.writeNext(favouritesCSVheader);
            // Write the existing favourite pairs back into favourite.csv
            Set favouriteKeySet = favourites.keySet(); // username
            for (Object key : favouriteKeySet) {
                String name = (String) key;
                System.out.println("writing favourites of " + name);
                ArrayList<AirbnbListing> currentArray = favourites.get(name);
                for (AirbnbListing listing : currentArray) {
                    String[] line = new String[2];
                    line[0] = (String) name;
                    line[1] = listing.getId();
                    favouriteWriter.writeNext(line);
                }
            }
            // Write the new favourite pair into favourite.csv
            favouriteWriter.writeNext(newline);
        }
    }

    /**
     * Removes the favourite listing from favourite.csv
     * @param username The current account's username
     * @param listingID The ID of the listing to be removed
     * @throws IOException Unable to write into csv
     */
    public void removeFavourite(String username, String listingID) throws IOException
    {
        try (CSVWriter favouriteWriter = new CSVWriter(
                new FileWriter("src/favourites.csv"),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        ) {
            // Write the labels into favourite.csv
            favouriteWriter.writeNext(favouritesCSVheader);
            // Write the existing favourite pairs back into favourite.csv
            Set favouriteKeySet = favourites.keySet();
            for (Object key : favouriteKeySet) {
                ArrayList<AirbnbListing> currentArray = favourites.get((String) key);
                for (AirbnbListing listing : currentArray) {
                    String[] line = new String[2];
                    line[0] = username;
                    line[1] = listing.getId();
                    // Write into csv if only the listingID does not match the selected one
                    if (!line[1].equals(listingID)) {
                        favouriteWriter.writeNext(line);
                    }
                }
            }
        }
    }

    /**
     * @param username The String to match
     * @return The account with matching the matching username
     */
    private Account getAccount(String username)
    {
        for (Account account : accounts) {
            if (account.getUserName().equals(username)) {
                return account;
            }
        }
        return null;
    }
}
